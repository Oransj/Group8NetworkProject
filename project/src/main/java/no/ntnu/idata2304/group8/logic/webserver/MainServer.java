package no.ntnu.idata2304.group8.logic.webserver;

import java.util.concurrent.ThreadLocalRandom;
import javax.json.Json;
import javax.json.JsonObject;
import no.ntnu.idata2304.group8.data.databasehandler.SqlHandler;
import no.ntnu.idata2304.group8.logic.mqtt.MQTTListener;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The main class in the application.
 */
@SpringBootApplication
public class MainServer {

  /**
   * It's responsible for creating the necessary object for the application to run.

   * @param args arguments.
   */
  public static void main(String[] args) {
    SpringApplication.run(MainServer.class, args);
    MQTTListener listener = new MQTTListener(false);
    Thread thread = new Thread(listener);
    thread.start();
  }

  /**
   * Fills the database with dummy data.
   *

   * @throws ParseException In case it fails to parse the string.
   */
  private static void fillDummyData() throws ParseException {
    SqlHandler sqlHandler = new SqlHandler();
    // The day at 00:00 in ms - 900000 ms
    // the DB will be filled with data every 15min until 00:00 excluded
    Long num = 1669934700000L;
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
                  .add("Precipitation", Json.createObjectBuilder()
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
