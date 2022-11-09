package no.ntnu.idata2304.group8.webserver;


import com.google.gson.Gson;
import com.workday.insights.timeseries.arima.Arima;
import com.workday.insights.timeseries.arima.struct.ArimaParams;
import com.workday.insights.timeseries.arima.struct.ForecastResult;
import no.ntnu.idata2304.group8.MQTT.MQTTListener;
import no.ntnu.idata2304.group8.databasehandler.SQLHandler;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.*;
import com.workday.insights.timeseries.arima.Arima;
import com.workday.insights.timeseries.arima.struct.ForecastResult;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Map;

@RestController
public class ControllerServer {
  SQLHandler sqlHandler = new SQLHandler();

  @PostMapping(value = "/api/front")
  public String processJSON(@RequestBody Map<String, Object> payload) {
    Long today = Long.parseLong((String) payload.get("todayMs"));
    Long day4Ms = Long.parseLong((String) payload.get("day4Ms"));
    ArrayList<String> list = sqlHandler.selectDate(today, day4Ms);
    Gson gson = new Gson();
    return gson.toJson(list);
  }

  @GetMapping("/api/front")
  public void getReports() throws IOException, ParseException {
//
//
//    SQLHandler sqlHandler = new SQLHandler();
//    ArrayList<String> list = sqlHandler.selectAll();
//
////    ArrayList<String> list = sqlHandler.selectDate(1666262249300L, 1666262249400L);
//    Gson gson = new Gson();
//    String jsonArray = gson.toJson(list);
//    return jsonArray;



// Prepare input timeseries data.
    double[] dataArray = new double[] {18, 18.4, 17, 12, 15, 18, 19, 20, 18, 20};

// Set ARIMA model parameters.
    int p = 3;
    int d = 0;
    int q = 3;
    int P = 1;
    int D = 1;
    int Q = 0;
    int m = 0;
    ArimaParams arimaParams = new ArimaParams(p, d, q, P, D, Q, m);

    int forecastSize = 3;

// Obtain forecast result. The structure contains forecasted values and performance metric etc.
    ForecastResult forecastResult = Arima.forecast_arima(dataArray, forecastSize, arimaParams);
    DecimalFormat decimalFormat = new DecimalFormat("#.##");
// Read forecast values
    double[] forecastData = forecastResult.getForecast(); // in this example, it will return { 2 }
    System.out.println(decimalFormat.format(forecastData[0]));
    System.out.println(forecastData[1]);
    System.out.println(forecastData[2]);
  }
}
