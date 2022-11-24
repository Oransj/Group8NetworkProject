package no.ntnu.idata2304.group8.logic.webserver;

import no.ntnu.idata2304.group8.logic.MQTT.MQTTListener;
import no.ntnu.idata2304.group8.data.databasehandler.SQLHandler;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.json.Json;
import javax.json.JsonObject;
import java.util.concurrent.ThreadLocalRandom;

@SpringBootApplication
public class MainServer {

  public static void main(String[] args) {
    SpringApplication.run(MainServer.class, args);
    MQTTListener listener = new MQTTListener("admin");
    Thread thread = new Thread(listener);
    thread.start();
  }

  private static void fillDummyData() throws ParseException {
    SQLHandler sqlHandler = new SQLHandler();
    // The day at 00:00 in ms - 900000 ms
    // the DB will be filled with data every 15min until 00:00 excluded
    Long num = 1668379500000L;
    for (int i = 0; i < 384; i++) {
      num += 900000;
      ThreadLocalRandom random = ThreadLocalRandom.current();
      JsonObject json = Json.createObjectBuilder()
              .add("Reading1",
                      Json.createObjectBuilder()
                              .add("Time", Json.createObjectBuilder()
                                      .add("ms", num))
                              .add("Temperature", Json.createObjectBuilder()
                                      .add("celsius", random.nextInt(15, 30)))
                              .add("Precipitaion", Json.createObjectBuilder()
                                      .add("mm", random.nextDouble(0, 3)))
                              .add("Air_pressure", Json.createObjectBuilder()
                                      .add("hPa", random.nextInt(500, 3000)))
                              .add("Light", Json.createObjectBuilder()
                                      .add("lux", random.nextInt(0, 1000)))
                              .add("Wind", Json.createObjectBuilder()
                                      .add("W_speed", random.nextInt(0, 10))
                                      .add("W_direction", random.nextInt(0, 360))))
              .build();
      JSONParser parser = new JSONParser();
      JSONObject jsonn = (JSONObject) parser.parse(json.toString());
      sqlHandler.addData(jsonn, "weather");
    }
  }
}
