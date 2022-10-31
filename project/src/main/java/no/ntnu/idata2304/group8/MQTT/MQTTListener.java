package no.ntnu.idata2304.group8.MQTT;

import org.eclipse.paho.client.mqttv3.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class MQTTListener implements Runnable {

    private static final String broker = "tcp://localhost:1883";
    private final String username;
    private final String password;
    private static int clientNumber;
    private final String clientId;
    private final String topic;

    private int qos = 0;
    public MQTTListener(String username, String sensorID) {
        this.username = username;
        this.password = "public";
        this.clientId = "client" + clientNumber;
        clientNumber++;
        topic = "test/" + sensorID;
    }

    public void run() {
        try {
            // Displaying the thread that is running
            System.out.println(
                    "Thread " + Thread.currentThread().getId()
                            + " is running");
        }
        catch (Exception e) {
            // Throwing an exception
            System.out.println("Exception is caught");
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
                //TODO:Add json object to database

            }

            public void deliveryComplete(IMqttDeliveryToken token) {
                System.err.println("deliveryComplete---------" + token.isComplete());
            }

        });

        client.connect(options);
        client.subscribe(this.topic, this.qos);
    }
}

class Multithread {
    public static void main(String[] args)
    {
        int n = 8; // Number of threads
        for (int i = 0; i < n; i++) {
            Thread object
                    = new Thread(new MQTTListener("admin", "sensor1"));
            object.start();
        }
    }
}
