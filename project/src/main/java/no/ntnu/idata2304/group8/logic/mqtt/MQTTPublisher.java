package no.ntnu.idata2304.group8.logic.mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * Publishes messages to the MQTT broker.
 *
 * @author Oransj
 * @version 1.0
 */
public class MQTTPublisher {

    private static final String broker = "tcp://129.241.152.12:1883";
    private final String username;
    private final String password;
    private final String publisherID;
    private final String topic;
    private final int qos = 0;

    /**
     * The constructor for the MQTTPublisher class.
     * It sets up the username, password, publisher id and topic.
     */
    public MQTTPublisher() {
        this.username = "username";
        this.password = "public";
        this.publisherID = "GenericPublisher";
        topic = "ntnu/ankeret/c220/multisensor/gruppe8/visualiseringsnode/";
    }

    /**
     * Publishes a message to the MQTT broker.
     *
     * @param message The message to be published.
     */
    public void publish(String message) {
        try {
            System.out.println("Publishing key");
            MqttClient client = new MqttClient(broker, publisherID);
            MqttConnectOptions options = new MqttConnectOptions();
            options.setUserName(username);
            options.setPassword(password.toCharArray());
            client.connect(options);
            MqttMessage mqttMessage = new MqttMessage(message.getBytes());
            mqttMessage.setQos(qos);
            client.publish(topic, mqttMessage);
            client.disconnect();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
