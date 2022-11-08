package no.ntnu.idata2304.group8.webserver;

import no.ntnu.idata2304.group8.MQTT.MQTTListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MainServer {

  public static void main(String[] args) {
    SpringApplication.run(MainServer.class, args);
    MQTTListener listener = new MQTTListener("admin");
    Thread thread = new Thread(listener);
    thread.start();
  }
}
