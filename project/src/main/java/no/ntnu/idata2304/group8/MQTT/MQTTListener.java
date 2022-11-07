package no.ntnu.idata2304.group8.MQTT;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.crypto.*;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.*;

import java.util.Base64;
import java.util.HashMap;

/**
 * Listens to the MQTT broker and sends the message to another class
 * as JSON object.
 * Runs on a separate thread.
 *
 * @version 1.0
 *
 * @author Oransj
 */

public class MQTTListener implements Runnable {

    private static final String broker = "tcp://129.241.152.12:1883";
    private final String username;
    private final String password;
    private static int clientNumber;
    private final String clientId;
    private final String topic;

    private int qos = 0;

    private PrivateKey privateKey;
    private PublicKey publicKey;

    /**
     * The constructor for the MQTTListener class.
     * Sets up the client id and topic.
     *
     * @param username Username for the MQTT broker
     */
    
    public MQTTListener(String username) {
        this.username = username;
        this.password = "public";
        this.clientId = "client" + clientNumber;
        clientNumber++;
        topic = "ntnu/ankeret/c220/gruppe8/weathersenor/#";
    }

    /**
     * The run method for the MQTTListener class.
     * Sets up the connection to the MQTT broker and subscribes to the topic.
     * When a message is received, it is sent to the //TODO: class that handles the message.
     */
    public void run() {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(4096);
            KeyPair pair = keyGen.generateKeyPair();
            privateKey = pair.getPrivate();
            publicKey = pair.getPublic();
            // Displaying the thread that is running
            System.out.println("Thread " + Thread.currentThread().getId() + " is running");
            connect();
        }
        catch (MqttException e) {
            // Throwing an exception
            System.out.printf("Exception caught: %s%n", e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private void connect() throws MqttException {
        MqttClient client = new MqttClient(username, topic);
        MqttConnectOptions options = new MqttConnectOptions();
        options.setUserName(username);
        options.setPassword(password.toCharArray());
        options.setCleanSession(true);
        options.setAutomaticReconnect(true);
        options.setConnectionTimeout(10);
        options.setKeepAliveInterval(20);
        HashMap<String, String> sensors = new HashMap<>();

        client.setCallback(new MqttCallback() {

            public void connectionLost(Throwable cause) {
                System.err.println("connectionLost: " + cause.getMessage());
            }

            public void messageArrived(String topic, MqttMessage message) throws ParseException, NoSuchPaddingException {
                System.out.println("topic: " + topic);
                System.out.println("Qos: " + message.getQos());
                String msg = new String(message.getPayload());
                System.out.println("message content: " + msg);
                String[] stringArray = msg.split("::", 2);
                msg = stringArray[1];
                if(!sensors.containsKey(stringArray[0])) {
                    sensors.put(stringArray[0], msg);
                    byte[] bytes = publicKey.getEncoded();
                    String stringKey = Base64.getEncoder().encodeToString(bytes);
                    MQTTPublisher publisher = new MQTTPublisher();
                    publisher.publish(stringKey);
                }
                else {
                    try {
                        Cipher decrypt = Cipher.getInstance("RSA");
                        decrypt.init(Cipher.DECRYPT_MODE, privateKey);
                        msg = new String(decrypt.doFinal(msg.getBytes()), StandardCharsets.UTF_8);
                        JSONParser parser = new JSONParser();
                        JSONObject json = (JSONObject) parser.parse(msg);
                        //TODO:Add json object to database
                    } catch (NoSuchAlgorithmException e){
                        System.out.println("No such algorithm, something went HORRIBLY wrong");
                        throw new RuntimeException(e);
                    } catch (InvalidKeyException e) {
                        System.err.println("Invalid key");
                        throw new RuntimeException(e);
                    } catch (IllegalBlockSizeException | BadPaddingException e) {
                        //TODO: Figure out how to correctly handle these exceptions
                        throw new RuntimeException(e);
                    }
                }

            }

            public void deliveryComplete(IMqttDeliveryToken token) {
                System.err.println("deliveryComplete---------" + token.isComplete());
            }

        });

        client.connect(options);
        client.subscribe(this.topic, this.qos);
    }
}
