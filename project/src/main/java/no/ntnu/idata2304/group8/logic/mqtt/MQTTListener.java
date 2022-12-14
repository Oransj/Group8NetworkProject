package no.ntnu.idata2304.group8.logic.mqtt;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import no.ntnu.idata2304.group8.logic.webserver.WeatherSorting;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;

/**
 * Listens to the MQTT broker and sends the message to another class
 * as JSON object.
 * Runs on a separate thread.
 *
 * @author Oransj
 * @version 1.0
 */

public class MQTTListener implements Runnable {
    private final String broker;
    private final String password;
    private static int clientNumber;
    private final String clientId;
    private final String topic;

    private int qos = 0;

    private PrivateKey privateKey;
    private PublicKey publicKey;

    private HashMap<String, String> sensors;

    private MQTTPublisher publisher;
    private boolean useEncryption = false;

    /**
     * The constructor for the MQTTListener class.
     * Sets up the client id and topic.
     *
     * @param useEncryption Whether to use encryption
     */
    public MQTTListener(boolean useEncryption) {
        sensors = new HashMap<>();
        this.broker = "tcp://129.241.152.12:1883";
        this.password = "public";
        this.clientId = "client" + clientNumber;
        clientNumber++;
        topic = "ntnu/ankeret/c220/gruppe8/#"; //TODO: Check if topic needs to include sensor id or if we can subscribe to a level over
    }

    /**
     * The run method for the MQTTListener class.
     * Sets up the connection to the MQTT broker and subscribes to the topic.
     * When a message is received, it is sent to the //TODO: class that handles the message.
     */
    public void run() {
        try {
            System.out.println("Starting MQTT listener");
            publisher = new MQTTPublisher();
            File privateKeyFile = new File(System.getProperty("user.dir") + File.separator + "PRK.ppk");
            File publicKeyFile = new File(System.getProperty("user.dir") + File.separator + "PUK.pem");
            System.out.println(privateKeyFile.getAbsolutePath());
            System.out.println(publicKeyFile.getAbsolutePath());
            if (
                    privateKeyFile.exists() && publicKeyFile.exists()
                    &&
                    privateKeyFile.isFile() && publicKeyFile.isFile()
            ) {
                System.out.println("Found keys");
                byte[] publicKeyBytes = Files.readAllBytes(publicKeyFile.toPath());
                X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
                KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                publicKey = keyFactory.generatePublic(publicKeySpec);
                byte[] privateKeyBytes = Files.readAllBytes(privateKeyFile.toPath());
                PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
                privateKey = keyFactory.generatePrivate(privateKeySpec);
            } else {
                System.out.println("Generating keys");
                KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
                keyGen.initialize(4096);
                KeyPair pair = keyGen.generateKeyPair();
                privateKey = pair.getPrivate();
                publicKey = pair.getPublic();
                System.out.println("Public key: " + publicKey.getAlgorithm() + " : " + publicKey.getFormat());
                System.out.println("Private key encoded:" + privateKey.getAlgorithm() + " : " + privateKey.getFormat());
                System.out.println("Keys generated");

                System.out.println("Saving keys");

                FileOutputStream pubFOS = new FileOutputStream(publicKeyFile);
                pubFOS.write(publicKey.getEncoded());
                pubFOS.close();
                FileOutputStream prkFOS = new FileOutputStream(privateKeyFile);
                prkFOS.write(privateKey.getEncoded());
                prkFOS.close();

                /*FileWriter writerPRK = new FileWriter(privateKeyFile);
                FileWriter writerPUK = new FileWriter(publicKeyFile);
                writerPRK.write(Base64.getEncoder().encodeToString(privateKey.getEncoded()));
                writerPUK.write(Base64.getEncoder().encodeToString(publicKey.getEncoded()));
                writerPRK.close();
                writerPUK.close();*/
                System.out.println("Keys saved");
            }

            // Displaying the thread that is running
            System.out.println("Thread " + Thread.currentThread().getId() + " is running");
            connect();
        } catch (MqttException e) {
            // Throwing an exception
            System.out.printf("Exception caught: %s%n", e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    private void connect() throws MqttException {
        MqttClient client = new MqttClient(this.broker, this.topic);
        MqttConnectOptions options = new MqttConnectOptions();
        options.setUserName(broker);
        options.setPassword(password.toCharArray());
        options.setCleanSession(true);
        options.setAutomaticReconnect(true);
        options.setConnectionTimeout(10);
        options.setKeepAliveInterval(20);

        client.setCallback(new MqttCallback() {

            public void connectionLost(Throwable cause) {
                System.err.println("connectionLost: " + cause.getMessage() + " : " + cause.getClass());
            }

            public void messageArrived(String topic, MqttMessage message) throws ParseException, java.text.ParseException {
                System.out.println("topic: " + topic);
                System.out.println("Qos: " + message.getQos());
                String msg = new String(message.getPayload());
                System.out.println("message content: " + msg);
                boolean containsKey = false;
                String[] stringArray = new String[0];
                if (useEncryption) {
                    stringArray = msg.split("::", 2);
                    System.out.println("Sensor: " + stringArray[0]);
                    msg = stringArray[1];
                    containsKey = sensors.containsKey(stringArray[0]);
                }
                if ((!containsKey || sensors.get(stringArray[0]).equals(stringArray[1])) && useEncryption) {
                    if (!containsKey) {
                        sensors.put(stringArray[0], msg);
                    }
                    byte[] bytes = publicKey.getEncoded();
                    String stringKey = Base64.getEncoder().encodeToString(bytes);

                    int i = 0;
                    StringBuilder keyBuilder = new StringBuilder();
                    keyBuilder.append("-----BEGIN RSA PUBLIC KEY-----");
                    for (char charAt : stringKey.toCharArray()) {
                        if (i % 64 == 0) {
                            keyBuilder.append("\n");
                        }
                        keyBuilder.append(charAt);
                        i++;
                    }
                    keyBuilder.append("\n-----END RSA PUBLIC KEY-----\n");

                    System.out.println("Public key test: " + "\n" + keyBuilder.toString());
                    publisher.publish(keyBuilder.toString());
                } else {
                    try {
                        if (useEncryption) {
                            Cipher decrypt = Cipher.getInstance("RSA/ECB/OAEPWITHSHA-256ANDMGF1PADDING");
                            decrypt.init(Cipher.DECRYPT_MODE, privateKey);
                            msg = new String(decrypt.doFinal(msg.getBytes()), StandardCharsets.UTF_8);
                        }

                        JSONObject json = new JSONObject(msg);
                        WeatherSorting weatherSorting = new WeatherSorting();
                        weatherSorting.saveData(json);
                    } catch (NoSuchAlgorithmException e) {
                        System.out.println("No such algorithm, something went HORRIBLY wrong");
                        throw new RuntimeException(e);
                    } catch (InvalidKeyException e) {
                        System.err.println("Invalid key");
                        throw new RuntimeException(e);
                    } catch (IllegalBlockSizeException e) {
                        System.out.println("Illegal block size");
                        throw new RuntimeException(e);
                    } catch (BadPaddingException e) {
                        System.out.println("Bad padding");
                        e.printStackTrace();
                    } catch (Exception e) {
                        System.out.println(e.getClass().getName());
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
