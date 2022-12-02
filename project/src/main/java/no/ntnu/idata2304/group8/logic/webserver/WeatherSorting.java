package no.ntnu.idata2304.group8.logic.webserver;

import com.workday.insights.timeseries.arima.Arima;
import com.workday.insights.timeseries.arima.struct.ArimaParams;
import com.workday.insights.timeseries.arima.struct.ForecastResult;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.json.Json;
import javax.json.JsonObject;
import no.ntnu.idata2304.group8.data.databasehandler.SQLHandler;
import no.ntnu.idata2304.group8.logic.weather.WeatherSummary;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class WeatherSorting {
  SQLHandler sqlHandler = new SQLHandler();
  WeatherSummary weatherSummary = new WeatherSummary();

  public ArrayList<Double[]> getHomePage(String[] days) {
    List<Double[]> day1Data =
        sqlHandler.selectWeatherDataBetween(Long.parseLong(days[0]), Long.parseLong(days[1]),
            "weather");
    Double[] day1AvgData = weatherSummary.getDaySummary(day1Data);

    List<Double[]> day2Data =
        sqlHandler.selectWeatherDataBetween(Long.parseLong(days[1]), Long.parseLong(days[2]),
            "weather");
    Double[] day2AvgData = weatherSummary.getDaySummary(day2Data);

    List<Double[]> day3Data =
        sqlHandler.selectWeatherDataBetween(Long.parseLong(days[2]), Long.parseLong(days[3]),
            "weather");
    Double[] day3AvgData = weatherSummary.getDaySummary(day3Data);

    List<Double[]> day4Data =
        sqlHandler.selectWeatherDataBetween(Long.parseLong(days[3]), Long.parseLong(days[4]),
            "weather");
    Double[] day4AvgData = weatherSummary.getDaySummary(day4Data);

    ArrayList<Double[]> avgDaysData = new ArrayList<>();
    avgDaysData.add(day1AvgData);
    avgDaysData.add(day2AvgData);
    avgDaysData.add(day3AvgData);
    avgDaysData.add(day4AvgData);

    return avgDaysData;
  }

  public String[] getWeatherType(String[] days) {
    List<Double[]> day1Data =
        sqlHandler.selectWeatherDataBetween(Long.parseLong(days[0]), Long.parseLong(days[1]),
            "weather");
    Double[] day1AvgData = weatherSummary.getAverageValuesInInterval(day1Data);
    String day1Type = weatherSummary.getWeatherSummary(day1AvgData);

    List<Double[]> day2Data =
        sqlHandler.selectWeatherDataBetween(Long.parseLong(days[1]), Long.parseLong(days[2]),
            "weather");
    Double[] day2AvgData = weatherSummary.getAverageValuesInInterval(day1Data);
    String day2Type = weatherSummary.getWeatherSummary(day2AvgData);

    List<Double[]> day3Data =
        sqlHandler.selectWeatherDataBetween(Long.parseLong(days[2]), Long.parseLong(days[3]),
            "weather");
    Double[] day3AvgData = weatherSummary.getAverageValuesInInterval(day1Data);
    String day3Type = weatherSummary.getWeatherSummary(day3AvgData);

    List<Double[]> day4Data =
        sqlHandler.selectWeatherDataBetween(Long.parseLong(days[3]), Long.parseLong(days[4]),
            "weather");
    Double[] day4AvgData = weatherSummary.getAverageValuesInInterval(day1Data);
    String day4Type = weatherSummary.getWeatherSummary(day4AvgData);

    return new String[] {day1Type, day2Type, day3Type, day4Type};
  }

  public ArrayList<Double[]> getDayRapport(String[] hours) {
    List<Double[]> hour0Data =
        sqlHandler.selectWeatherRapportBetween(Long.parseLong(hours[0]), Long.parseLong(hours[1]),
            "weather");
    Double[] hour0AvgData = weatherSummary.getAverageValuesIDayRapport(hour0Data);

    List<Double[]> hour1Data =
        sqlHandler.selectWeatherRapportBetween(Long.parseLong(hours[1]), Long.parseLong(hours[2]),
            "weather");
    Double[] hour1AvgData = weatherSummary.getAverageValuesIDayRapport(hour1Data);

    List<Double[]> hour2Data =
        sqlHandler.selectWeatherRapportBetween(Long.parseLong(hours[2]), Long.parseLong(hours[3]),
            "weather");
    Double[] hour2AvgData = weatherSummary.getAverageValuesIDayRapport(hour2Data);

    List<Double[]> hour3Data =
        sqlHandler.selectWeatherRapportBetween(Long.parseLong(hours[3]), Long.parseLong(hours[4]),
            "weather");
    Double[] hour3AvgData = weatherSummary.getAverageValuesIDayRapport(hour3Data);

    List<Double[]> hour4Data =
        sqlHandler.selectWeatherRapportBetween(Long.parseLong(hours[4]), Long.parseLong(hours[5]),
            "weather");
    Double[] hour4AvgData = weatherSummary.getAverageValuesIDayRapport(hour4Data);

    List<Double[]> hour5Data =
        sqlHandler.selectWeatherRapportBetween(Long.parseLong(hours[5]), Long.parseLong(hours[6]),
            "weather");
    Double[] hour5AvgData = weatherSummary.getAverageValuesIDayRapport(hour5Data);

    List<Double[]> hour6Data =
        sqlHandler.selectWeatherRapportBetween(Long.parseLong(hours[6]), Long.parseLong(hours[7]),
            "weather");
    Double[] hour6AvgData = weatherSummary.getAverageValuesIDayRapport(hour6Data);

    List<Double[]> hour7Data =
        sqlHandler.selectWeatherRapportBetween(Long.parseLong(hours[7]), Long.parseLong(hours[8]),
            "weather");
    Double[] hour7AvgData = weatherSummary.getAverageValuesIDayRapport(hour7Data);

    List<Double[]> hour8Data =
        sqlHandler.selectWeatherRapportBetween(Long.parseLong(hours[8]), Long.parseLong(hours[9]),
            "weather");
    Double[] hour8AvgData = weatherSummary.getAverageValuesIDayRapport(hour8Data);

    List<Double[]> hour9Data =
        sqlHandler.selectWeatherRapportBetween(Long.parseLong(hours[9]), Long.parseLong(hours[10]),
            "weather");
    Double[] hour9AvgData = weatherSummary.getAverageValuesIDayRapport(hour9Data);

    List<Double[]> hour10Data =
        sqlHandler.selectWeatherRapportBetween(Long.parseLong(hours[10]), Long.parseLong(hours[11]),
            "weather");
    Double[] hour10AvgData = weatherSummary.getAverageValuesIDayRapport(hour10Data);

    List<Double[]> hour11Data =
        sqlHandler.selectWeatherRapportBetween(Long.parseLong(hours[11]), Long.parseLong(hours[12]),
            "weather");
    Double[] hour11AvgData = weatherSummary.getAverageValuesIDayRapport(hour11Data);

    List<Double[]> hour12Data =
        sqlHandler.selectWeatherRapportBetween(Long.parseLong(hours[12]), Long.parseLong(hours[13]),
            "weather");
    Double[] hour12AvgData = weatherSummary.getAverageValuesIDayRapport(hour12Data);

    List<Double[]> hour13Data =
        sqlHandler.selectWeatherRapportBetween(Long.parseLong(hours[13]), Long.parseLong(hours[14]),
            "weather");
    Double[] hour13AvgData = weatherSummary.getAverageValuesIDayRapport(hour13Data);

    List<Double[]> hour14Data =
        sqlHandler.selectWeatherRapportBetween(Long.parseLong(hours[14]), Long.parseLong(hours[15]),
            "weather");
    Double[] hour14AvgData = weatherSummary.getAverageValuesIDayRapport(hour14Data);

    List<Double[]> hour15Data =
        sqlHandler.selectWeatherRapportBetween(Long.parseLong(hours[15]), Long.parseLong(hours[16]),
            "weather");
    Double[] hour15AvgData = weatherSummary.getAverageValuesIDayRapport(hour15Data);

    List<Double[]> hour16Data =
        sqlHandler.selectWeatherRapportBetween(Long.parseLong(hours[16]), Long.parseLong(hours[17]),
            "weather");
    Double[] hour16AvgData = weatherSummary.getAverageValuesIDayRapport(hour16Data);

    List<Double[]> hour17Data =
        sqlHandler.selectWeatherRapportBetween(Long.parseLong(hours[17]), Long.parseLong(hours[18]),
            "weather");
    Double[] hour17AvgData = weatherSummary.getAverageValuesIDayRapport(hour17Data);

    List<Double[]> hour18Data =
        sqlHandler.selectWeatherRapportBetween(Long.parseLong(hours[18]), Long.parseLong(hours[19]),
            "weather");
    Double[] hour18AvgData = weatherSummary.getAverageValuesIDayRapport(hour18Data);

    List<Double[]> hour19Data =
        sqlHandler.selectWeatherRapportBetween(Long.parseLong(hours[19]), Long.parseLong(hours[20]),
            "weather");
    Double[] hour19AvgData = weatherSummary.getAverageValuesIDayRapport(hour19Data);

    List<Double[]> hour20Data =
        sqlHandler.selectWeatherRapportBetween(Long.parseLong(hours[20]), Long.parseLong(hours[21]),
            "weather");
    Double[] hour20AvgData = weatherSummary.getAverageValuesIDayRapport(hour20Data);

    List<Double[]> hour21Data =
        sqlHandler.selectWeatherRapportBetween(Long.parseLong(hours[21]), Long.parseLong(hours[22]),
            "weather");
    Double[] hour21AvgData = weatherSummary.getAverageValuesIDayRapport(hour21Data);

    List<Double[]> hour22Data =
        sqlHandler.selectWeatherRapportBetween(Long.parseLong(hours[22]), Long.parseLong(hours[23]),
            "weather");
    Double[] hour22AvgData = weatherSummary.getAverageValuesIDayRapport(hour22Data);

    List<Double[]> hour23Data =
        sqlHandler.selectWeatherRapportBetween(Long.parseLong(hours[23]), Long.parseLong(hours[24]),
            "weather");
    Double[] hour23AvgData = weatherSummary.getAverageValuesIDayRapport(hour23Data);

    ArrayList<Double[]> avgHoursData = new ArrayList<>();
    avgHoursData.add(hour0AvgData);
    avgHoursData.add(hour1AvgData);
    avgHoursData.add(hour2AvgData);
    avgHoursData.add(hour3AvgData);
    avgHoursData.add(hour4AvgData);
    avgHoursData.add(hour5AvgData);
    avgHoursData.add(hour6AvgData);
    avgHoursData.add(hour7AvgData);
    avgHoursData.add(hour8AvgData);
    avgHoursData.add(hour9AvgData);
    avgHoursData.add(hour10AvgData);
    avgHoursData.add(hour11AvgData);
    avgHoursData.add(hour12AvgData);
    avgHoursData.add(hour13AvgData);
    avgHoursData.add(hour14AvgData);
    avgHoursData.add(hour15AvgData);
    avgHoursData.add(hour16AvgData);
    avgHoursData.add(hour17AvgData);
    avgHoursData.add(hour18AvgData);
    avgHoursData.add(hour19AvgData);
    avgHoursData.add(hour20AvgData);
    avgHoursData.add(hour21AvgData);
    avgHoursData.add(hour22AvgData);
    avgHoursData.add(hour23AvgData);


    return avgHoursData;
  }

  public String[] getWeatherTypeDayRapport(String[] hours) {
    List<Double[]> hour0Data =
        sqlHandler.selectWeatherDataBetween(Long.parseLong(hours[0]), Long.parseLong(hours[1]),
            "weather");
    Double[] hour0AvgData = weatherSummary.getAverageValuesInInterval(hour0Data);
    String hour0Type = weatherSummary.getWeatherSummary(hour0AvgData);

    List<Double[]> hour1Data =
        sqlHandler.selectWeatherDataBetween(Long.parseLong(hours[1]), Long.parseLong(hours[2]),
            "weather");
    Double[] hour1AvgData = weatherSummary.getAverageValuesInInterval(hour1Data);
    String hour1Type = weatherSummary.getWeatherSummary(hour1AvgData);

    List<Double[]> hour2Data =
        sqlHandler.selectWeatherDataBetween(Long.parseLong(hours[2]), Long.parseLong(hours[3]),
            "weather");
    Double[] hour2AvgData = weatherSummary.getAverageValuesInInterval(hour2Data);
    String hour2Type = weatherSummary.getWeatherSummary(hour2AvgData);

    List<Double[]> hour3Data =
        sqlHandler.selectWeatherDataBetween(Long.parseLong(hours[3]), Long.parseLong(hours[4]),
            "weather");
    Double[] hour3AvgData = weatherSummary.getAverageValuesInInterval(hour3Data);
    String hour3Type = weatherSummary.getWeatherSummary(hour3AvgData);

    List<Double[]> hour4Data =
        sqlHandler.selectWeatherDataBetween(Long.parseLong(hours[4]), Long.parseLong(hours[5]),
            "weather");
    Double[] hour4AvgData = weatherSummary.getAverageValuesInInterval(hour4Data);
    String hour4Type = weatherSummary.getWeatherSummary(hour4AvgData);

    List<Double[]> hour5Data =
        sqlHandler.selectWeatherDataBetween(Long.parseLong(hours[5]), Long.parseLong(hours[6]),
            "weather");
    Double[] hour5AvgData = weatherSummary.getAverageValuesInInterval(hour5Data);
    String hour5Type = weatherSummary.getWeatherSummary(hour5AvgData);

    List<Double[]> hour6Data =
        sqlHandler.selectWeatherDataBetween(Long.parseLong(hours[6]), Long.parseLong(hours[7]),
            "weather");
    Double[] hour6AvgData = weatherSummary.getAverageValuesInInterval(hour6Data);
    String hour6Type = weatherSummary.getWeatherSummary(hour6AvgData);

    List<Double[]> hour7Data =
        sqlHandler.selectWeatherDataBetween(Long.parseLong(hours[7]), Long.parseLong(hours[8]),
            "weather");
    Double[] hour7AvgData = weatherSummary.getAverageValuesInInterval(hour7Data);
    String hour7Type = weatherSummary.getWeatherSummary(hour7AvgData);


    List<Double[]> hour8Data =
        sqlHandler.selectWeatherDataBetween(Long.parseLong(hours[8]), Long.parseLong(hours[9]),
            "weather");
    Double[] hour8AvgData = weatherSummary.getAverageValuesInInterval(hour8Data);
    String hour8Type = weatherSummary.getWeatherSummary(hour8AvgData);

    List<Double[]> hour9Data =
        sqlHandler.selectWeatherDataBetween(Long.parseLong(hours[9]), Long.parseLong(hours[10]),
            "weather");
    Double[] hour9AvgData = weatherSummary.getAverageValuesInInterval(hour9Data);
    String hour9Type = weatherSummary.getWeatherSummary(hour9AvgData);

    List<Double[]> hour10Data =
        sqlHandler.selectWeatherDataBetween(Long.parseLong(hours[10]), Long.parseLong(hours[11]),
            "weather");
    Double[] hour10AvgData = weatherSummary.getAverageValuesInInterval(hour10Data);
    String hour10Type = weatherSummary.getWeatherSummary(hour10AvgData);

    List<Double[]> hour11Data =
        sqlHandler.selectWeatherDataBetween(Long.parseLong(hours[11]), Long.parseLong(hours[12]),
            "weather");
    Double[] hour11AvgData = weatherSummary.getAverageValuesInInterval(hour11Data);
    String hour11Type = weatherSummary.getWeatherSummary(hour11AvgData);

    List<Double[]> hour12Data =
        sqlHandler.selectWeatherDataBetween(Long.parseLong(hours[12]), Long.parseLong(hours[13]),
            "weather");
    Double[] hour12AvgData = weatherSummary.getAverageValuesInInterval(hour12Data);
    String hour12Type = weatherSummary.getWeatherSummary(hour12AvgData);

    List<Double[]> hour13Data =
        sqlHandler.selectWeatherDataBetween(Long.parseLong(hours[13]), Long.parseLong(hours[14]),
            "weather");
    Double[] hour13AvgData = weatherSummary.getAverageValuesInInterval(hour13Data);
    String hour13Type = weatherSummary.getWeatherSummary(hour13AvgData);

    List<Double[]> hour14Data =
        sqlHandler.selectWeatherDataBetween(Long.parseLong(hours[14]), Long.parseLong(hours[15]),
            "weather");
    Double[] hour14AvgData = weatherSummary.getAverageValuesInInterval(hour14Data);
    String hour14Type = weatherSummary.getWeatherSummary(hour14AvgData);

    List<Double[]> hour15Data =
        sqlHandler.selectWeatherDataBetween(Long.parseLong(hours[15]), Long.parseLong(hours[16]),
            "weather");
    Double[] hour15AvgData = weatherSummary.getAverageValuesInInterval(hour15Data);
    String hour15Type = weatherSummary.getWeatherSummary(hour15AvgData);

    List<Double[]> hour16Data =
        sqlHandler.selectWeatherDataBetween(Long.parseLong(hours[16]), Long.parseLong(hours[17]),
            "weather");
    Double[] hour16AvgData = weatherSummary.getAverageValuesInInterval(hour16Data);
    String hour16Type = weatherSummary.getWeatherSummary(hour16AvgData);

    List<Double[]> hour17Data =
        sqlHandler.selectWeatherDataBetween(Long.parseLong(hours[17]), Long.parseLong(hours[18]),
            "weather");
    Double[] hour17AvgData = weatherSummary.getAverageValuesInInterval(hour17Data);
    String hour17Type = weatherSummary.getWeatherSummary(hour17AvgData);

    List<Double[]> hour18Data =
        sqlHandler.selectWeatherDataBetween(Long.parseLong(hours[18]), Long.parseLong(hours[19]),
            "weather");
    Double[] hour18AvgData = weatherSummary.getAverageValuesInInterval(hour18Data);
    String hour18Type = weatherSummary.getWeatherSummary(hour18AvgData);

    List<Double[]> hour19Data =
        sqlHandler.selectWeatherDataBetween(Long.parseLong(hours[19]), Long.parseLong(hours[20]),
            "weather");
    Double[] hour19AvgData = weatherSummary.getAverageValuesInInterval(hour19Data);
    String hour19Type = weatherSummary.getWeatherSummary(hour19AvgData);

    List<Double[]> hour20Data =
        sqlHandler.selectWeatherDataBetween(Long.parseLong(hours[20]), Long.parseLong(hours[21]),
            "weather");
    Double[] hour20AvgData = weatherSummary.getAverageValuesInInterval(hour20Data);
    String hour20Type = weatherSummary.getWeatherSummary(hour20AvgData);

    List<Double[]> hour21Data =
        sqlHandler.selectWeatherDataBetween(Long.parseLong(hours[21]), Long.parseLong(hours[22]),
            "weather");
    Double[] hour21AvgData = weatherSummary.getAverageValuesInInterval(hour21Data);
    String hour21Type = weatherSummary.getWeatherSummary(hour21AvgData);

    List<Double[]> hour22Data =
        sqlHandler.selectWeatherDataBetween(Long.parseLong(hours[22]), Long.parseLong(hours[23]),
            "weather");
    Double[] hour22AvgData = weatherSummary.getAverageValuesInInterval(hour22Data);
    String hour22Type = weatherSummary.getWeatherSummary(hour22AvgData);

    List<Double[]> hour23Data =
        sqlHandler.selectWeatherDataBetween(Long.parseLong(hours[23]), Long.parseLong(hours[24]),
            "weather");
    Double[] hour23AvgData = weatherSummary.getAverageValuesInInterval(hour23Data);
    String hour23Type = weatherSummary.getWeatherSummary(hour23AvgData);

    return new String[] {
        hour0Type,
        hour1Type,
        hour2Type,
        hour3Type,
        hour4Type,
        hour5Type,
        hour6Type,
        hour6Type,
        hour7Type,
        hour8Type,
        hour9Type,
        hour10Type,
        hour11Type,
        hour12Type,
        hour13Type,
        hour14Type,
        hour15Type,
        hour16Type,
        hour17Type,
        hour18Type,
        hour19Type,
        hour20Type,
        hour21Type,
        hour22Type,
        hour23Type,
    };
  }

  public boolean isSpike(JSONObject jsonObject) {
    // current data
    Double tempCurrent = Double.parseDouble(
        jsonObject.getJSONObject("Reading1").getJSONObject("Temperature").get("celsius")
            .toString());
    Double precipCurrent = Double.parseDouble(
        jsonObject.getJSONObject("Reading1").getJSONObject("Precipitation").get("mm").toString());
    Double pressCurrent = Double.parseDouble(
        jsonObject.getJSONObject("Reading1").getJSONObject("Air_pressure").get("hPa").toString());
    Double lightCurrent = Double.parseDouble(
        jsonObject.getJSONObject("Reading1").getJSONObject("Light").get("lux").toString());
    Double speedCurrent = Double.parseDouble(
        jsonObject.getJSONObject("Reading1").getJSONObject("Wind").get("W_speed").toString());

    // last data in DB
    JSONObject json = new JSONObject(sqlHandler.selectLast());
    Double temp = Double.parseDouble(json.getJSONObject("Temperature").get("celsius").toString());
    Double precip = Double.parseDouble(json.getJSONObject("Precipitation").get("mm").toString());
    Double press = Double.parseDouble(json.getJSONObject("Air_pressure").get("hPa").toString());
    Double light = Double.parseDouble(json.getJSONObject("Light").get("lux").toString());
    Double speed = Double.parseDouble(json.getJSONObject("Wind").get("W_speed").toString());

    boolean spike = false;

    if (tempCurrent > (temp * 10)
        ||
        precipCurrent > (precip * 10)
        ||
        pressCurrent > (press * 20)
        ||
        lightCurrent > (light * 10)
        ||
        speedCurrent > (speed * 10)) {
      spike = true;
    }

    return spike;
  }

  public List<JSONObject> getLastTenObjects(Long ms) {
    ArrayList<JSONObject> objects = new ArrayList<>();
    sqlHandler.selectLastTen(ms).forEach(jsonString -> objects.add(new JSONObject(jsonString)));

    return objects;
  }

  public double predictValue(double[] previousTenValues) {
    // Prepare input timeseries data.
    double[] dataArray = previousTenValues;

    // Set ARIMA model parameters.
    int p = 3;
    int d = 0;
    int q = 3;
    int P = 1;
    int D = 1;
    int Q = 0;
    int m = 0;
    ArimaParams arimaParams = new ArimaParams(p, d, q, P, D, Q, m);

    int forecastSize = 1;

    // Obtain forecast result. The structure contains forecasted values and performance metric etc.
    ForecastResult forecastResult = Arima.forecast_arima(dataArray, forecastSize, arimaParams);
    DecimalFormat decimalFormat = new DecimalFormat("#.##");
    // Read forecast values
    double[] forecastData = forecastResult.getForecast(); // in this example, it will return { 2 };

    return Double.parseDouble(decimalFormat.format(forecastData[0]));
  }

  public double[] getLastTenValues(List<JSONObject> jsons, String attributeName) {
    // TODO: use data with a wider range
    double[] previousTenValues = new double[10];
    String unit = null;

    switch (attributeName) {
      case "Temperature":
        unit = "celsius";
        break;
      case "Precipitation":
        unit = "mm";
        break;
      case "Air_pressure":
        unit = "hPa";
        break;
      case "Light":
        unit = "lux";
        break;
    }

    for (int i = 0; i < previousTenValues.length; i++) {
      previousTenValues[i] =
          Double.parseDouble(jsons.get(i).getJSONObject(attributeName).get(unit).toString());
    }

    return previousTenValues;
  }

  public double[] getLastTenWindSpeed(List<JSONObject> jsons) {
    double[] previousTenWindSpeed = new double[10];

    for (int i = 0; i < previousTenWindSpeed.length; i++) {
      previousTenWindSpeed[i] =
          Double.parseDouble(jsons.get(i).getJSONObject("Wind").get("W_speed").toString());
    }

    return previousTenWindSpeed;
  }

  public double[] getLastTenWindDirection(List<JSONObject> jsons) {
    double[] previousTenWindDirection = new double[10];

    for (int i = 0; i < previousTenWindDirection.length; i++) {
      previousTenWindDirection[i] =
          Double.parseDouble(jsons.get(i).getJSONObject("Wind").get("W_direction").toString());
    }

    return previousTenWindDirection;
  }

  public void predict() throws ParseException, java.text.ParseException {
    Long currentObjectTime = Long.parseLong(
        new JSONObject(sqlHandler.selectLast()).getJSONObject("Time").get("ms").toString());
    DateFormat obj = new SimpleDateFormat("dd MM yyyy HH mm");
    Date res = new Date(currentObjectTime);
    String[] date = obj.format(res).split(" ");
    Integer day = Integer.parseInt(date[0]) + 1;
    Integer month = Integer.parseInt(date[1]);
    Integer year = Integer.parseInt(date[2]);

    int hourMs = 3600000;

    while (res.getTime()
        <
        (obj.parse(day + " " + month + " " + year + " " + 0 + " " + 0).getTime())) {
      List<JSONObject> jsons = getLastTenObjects(currentObjectTime);
      res = new Date(currentObjectTime += hourMs);
      double predictedTemperature = predictValue(getLastTenValues(jsons, "Temperature"));
      double predictedPrecipitation = predictValue(getLastTenValues(jsons, "Precipitation"));
      double predictedAirPressure = predictValue(getLastTenValues(jsons, "Air_pressure"));
      double predictedLight = predictValue(getLastTenValues(jsons, "Light"));
      double predictedWindSpeed = predictValue(getLastTenWindSpeed(jsons));
      double predictedWindDirection = predictValue(getLastTenWindDirection(jsons));

      JsonObject json = Json.createObjectBuilder()
          .add("Reading1",
              Json.createObjectBuilder()
                  .add("Time", Json.createObjectBuilder()
                      .add("ms", currentObjectTime))
                  .add("Temperature", Json.createObjectBuilder()
                      .add("celsius", predictedTemperature))
                  .add("Precipitation", Json.createObjectBuilder()
                      .add("mm", predictedPrecipitation))
                  .add("Air_pressure", Json.createObjectBuilder()
                      .add("hPa", predictedAirPressure))
                  .add("Light", Json.createObjectBuilder()
                      .add("lux", predictedLight))
                  .add("Wind", Json.createObjectBuilder()
                      .add("W_speed", predictedWindSpeed)
                      .add("W_direction", predictedWindDirection)))
          .build();
      JSONParser parser = new JSONParser();
      org.json.simple.JSONObject jsonDB =
          (org.json.simple.JSONObject) parser.parse(json.toString());
      sqlHandler.addData(jsonDB, "weather");
      if (!isSpike(new JSONObject(jsonDB))) {
        sqlHandler.addData(jsonDB, "weather");
      } else {
        res = new Date(currentObjectTime -= hourMs);
      }

    }

  }


  public void saveData(JSONObject jsonObject) throws ParseException, java.text.ParseException {
    // Saving the given object in a variable
    JSONObject currentObject = jsonObject;

    // Making a DB suitable copy of the object (simple json)
    JSONParser parser = new JSONParser();
    org.json.simple.JSONObject currentObjectDB =
        (org.json.simple.JSONObject) parser.parse(currentObject.toString());

    Long currentObjectTime = Long.parseLong(
        currentObject.getJSONObject("Reading1").getJSONObject("Time").get("ms").toString());
    int hourMs = 3600000;
    int dayMs = 86400000;

    if (isSpike(currentObject)) {
      sqlHandler.addData(currentObjectDB, "spike");
    }

    // If the given object with the given time doesn't exist in DB
    if (sqlHandler.select(currentObjectTime).equals("null")) {
      sqlHandler.addData(currentObjectDB, "weather");

      // If there is no object in the DB for the next hour => no objects for the coming days
      if (sqlHandler.select(currentObjectTime + hourMs).equals("null")) {
        // predict for the rest of the current day and 3 days ahead.
        for (int i = 0; i <= 3; i++) {
          predict();
        }
      }

      // If there is no object in the DB for the 4th day
      if (sqlHandler.select(currentObjectTime + (3 * dayMs)).equals("null")) {
        // predict for the 4th day only
        predict();
      }
    }

    // if the given object with the given time exists in the DB
    if (!sqlHandler.select(currentObjectTime).equals("null")) {
      // delete it, because it's a prediction
      sqlHandler.delete(currentObjectTime);
      // save the actual object instead
      sqlHandler.addData(currentObjectDB, "weather");
    }
  }
}
