package no.ntnu.idata2304.group8.MQTT;

import no.ntnu.idata2304.group8.databasehandler.SQLHandler;
import no.ntnu.idata2304.group8.webserver.WeatherSorting;
import org.eclipse.paho.client.mqttv3.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.ConnectException;

public class MQTTListener implements Runnable {

    private static final String broker = "tcp://129.241.152.12:1883";
    private final String username;
    private final String password;
    private static int clientNumber;
    private final String clientId;
    private final String topic;

    private int qos = 0;

    /**
     * Constructor for the MQTTListener class
     *
     * @param username The username for the MQTT broker
     */
    public MQTTListener(String username) {
        this.username = username;
        this.password = "public";
        this.clientId = "client" + clientNumber;
        clientNumber++;
        topic = "ntnu/ankeret/c220/multisensor/gruppe8/" + "0601holmes"; //TODO: Check if topic needs to include sensor id or if we can subscribe to a level over
    }

    public void run() {
        try {
            // Displaying the thread that is running
            System.out.println(
                    "Thread " + Thread.currentThread().getId()
                            + " is running");
            connect();
        }
        catch (MqttException e) {
            // Throwing an exception
            System.out.printf("Exception caught: %s%n", e);
        }
    }

    private void connect() throws MqttException {
        MqttClient client = new MqttClient(broker, username);
        MqttConnectOptions options = new MqttConnectOptions();
        options.setUserName(username);
        options.setPassword(password.toCharArray());
        options.setCleanSession(true);
        options.setAutomaticReconnect(true);
        options.setConnectionTimeout(10);
        options.setKeepAliveInterval(20);

        client.setCallback(new MqttCallback() {

            public void connectionLost(Throwable cause) {
                System.err.println("connectionLost: " + cause.getMessage());
            }

            public void messageArrived(String topic, MqttMessage message) throws ParseException {
                System.out.println("topic: " + topic);
                System.out.println("Qos: " + message.getQos());
                String msg = new String(message.getPayload());
                System.out.println("message content: " + msg);
                JSONParser parser = new JSONParser();
                JSONObject json = (JSONObject) parser.parse(msg);
                SQLHandler sqlHandler = new SQLHandler();
                sqlHandler.addData(json, "weather");
            }

            public void deliveryComplete(IMqttDeliveryToken token) {
                System.err.println("deliveryComplete---------" + token.isComplete());
            }

        });

        client.connect(options);
        client.subscribe(this.topic, this.qos);
    }
}
