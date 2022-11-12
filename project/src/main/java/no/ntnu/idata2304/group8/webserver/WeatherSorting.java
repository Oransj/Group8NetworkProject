package no.ntnu.idata2304.group8.webserver;

import no.ntnu.idata2304.group8.databasehandler.SQLHandler;
import no.ntnu.idata2304.group8.weather.WeatherSummary;

import java.util.ArrayList;
import java.util.List;

public class WeatherSorting {
    SQLHandler sqlHandler = new SQLHandler();
    WeatherSummary weatherSummary = new WeatherSummary();
    public ArrayList<Double[]> getHomePage(String[] days) {
        // TODO: get weather type
        List<Double[]> day1Data = sqlHandler.selectWeatherDataBetween(Long.parseLong(days[0]), Long.parseLong(days[1]), "weather");
        Double[] day1AvgData = weatherSummary.getDaySummary(day1Data);

        List<Double[]> day2Data = sqlHandler.selectWeatherDataBetween(Long.parseLong(days[1]), Long.parseLong(days[2]), "weather");
        Double[] day2AvgData = weatherSummary.getDaySummary(day2Data);

        List<Double[]> day3Data = sqlHandler.selectWeatherDataBetween(Long.parseLong(days[2]), Long.parseLong(days[3]), "weather");
        Double[] day3AvgData = weatherSummary.getDaySummary(day3Data);

        List<Double[]> day4Data = sqlHandler.selectWeatherDataBetween(Long.parseLong(days[3]), Long.parseLong(days[4]), "weather");
        Double[] day4AvgData = weatherSummary.getDaySummary(day4Data);

        ArrayList<Double[]> avgDaysData = new ArrayList<>();
        avgDaysData.add(day1AvgData);
        avgDaysData.add(day2AvgData);
        avgDaysData.add(day3AvgData);
        avgDaysData.add(day4AvgData);

        return avgDaysData;
    }
}
