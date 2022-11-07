package no.ntnu.idata2304.group8.MQTT;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MQTTPublisher {

    private static final String broker = "tcp://129.241.152.12:1883";
    private final String username;
    private final String password;
    private final String publisherID;
    private final String topic;
    private final int qos = 0;

    public MQTTPublisher() {
        this.username = "username";
        this.password = "public";
        this.publisherID = "GenericPublisher";
        topic = "ntnu/ankeret/c220/multisensor/gruppe8/visualiseringsnode/";
    }

    public void publish(String message) {
        try {
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
