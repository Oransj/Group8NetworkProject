package no.ntnu.idata2304.group8.webserver;

import no.ntnu.idata2304.group8.databasehandler.SQLHandler;
import no.ntnu.idata2304.group8.weather.WeatherSummary;

import java.util.ArrayList;
import java.util.List;

public class WeatherSorting {
    SQLHandler sqlHandler = new SQLHandler();
    WeatherSummary weatherSummary = new WeatherSummary();
    public ArrayList<Double[]> getHomePage(String[] days) {
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

    public String[] getWeatherType(String[] days) {
        List<Double[]> day1Data = sqlHandler.selectWeatherDataBetween(Long.parseLong(days[0]), Long.parseLong(days[1]), "weather");
        Double[] day1AvgData = weatherSummary.getAverageValuesInInterval(day1Data);
        String day1Type = weatherSummary.getWeatherSummary(day1AvgData);

        List<Double[]> day2Data = sqlHandler.selectWeatherDataBetween(Long.parseLong(days[1]), Long.parseLong(days[2]), "weather");
        Double[] day2AvgData = weatherSummary.getAverageValuesInInterval(day1Data);
        String day2Type = weatherSummary.getWeatherSummary(day2AvgData);

        List<Double[]> day3Data = sqlHandler.selectWeatherDataBetween(Long.parseLong(days[2]), Long.parseLong(days[3]), "weather");
        Double[] day3AvgData = weatherSummary.getAverageValuesInInterval(day1Data);
        String day3Type = weatherSummary.getWeatherSummary(day3AvgData);

        List<Double[]> day4Data = sqlHandler.selectWeatherDataBetween(Long.parseLong(days[3]), Long.parseLong(days[4]), "weather");
        Double[] day4AvgData = weatherSummary.getAverageValuesInInterval(day1Data);
        String day4Type = weatherSummary.getWeatherSummary(day4AvgData);

        return new String[]{day1Type, day2Type, day3Type, day4Type};
    }

    public ArrayList<Double[]> getDayRapport(String[] hours) {
        List<Double[]> hour0Data = sqlHandler.selectWeatherRapportBetween(Long.parseLong(hours[0]), Long.parseLong(hours[1]), "weather");
        Double[] hour0AvgData = weatherSummary.getAverageValuesIDayRapport(hour0Data);

        List<Double[]> hour1Data = sqlHandler.selectWeatherRapportBetween(Long.parseLong(hours[1]), Long.parseLong(hours[2]), "weather");
        Double[] hour1AvgData = weatherSummary.getAverageValuesIDayRapport(hour1Data);

        List<Double[]> hour2Data = sqlHandler.selectWeatherRapportBetween(Long.parseLong(hours[2]), Long.parseLong(hours[3]), "weather");
        Double[] hour2AvgData = weatherSummary.getAverageValuesIDayRapport(hour2Data);

        List<Double[]> hour3Data = sqlHandler.selectWeatherRapportBetween(Long.parseLong(hours[3]), Long.parseLong(hours[4]), "weather");
        Double[] hour3AvgData = weatherSummary.getAverageValuesIDayRapport(hour3Data);

        List<Double[]> hour4Data = sqlHandler.selectWeatherRapportBetween(Long.parseLong(hours[4]), Long.parseLong(hours[5]), "weather");
        Double[] hour4AvgData = weatherSummary.getAverageValuesIDayRapport(hour4Data);

        List<Double[]> hour5Data = sqlHandler.selectWeatherRapportBetween(Long.parseLong(hours[5]), Long.parseLong(hours[6]), "weather");
        Double[] hour5AvgData = weatherSummary.getAverageValuesIDayRapport(hour5Data);

        List<Double[]> hour6Data = sqlHandler.selectWeatherRapportBetween(Long.parseLong(hours[6]), Long.parseLong(hours[7]), "weather");
        Double[] hour6AvgData = weatherSummary.getAverageValuesIDayRapport(hour6Data);

        List<Double[]> hour7Data = sqlHandler.selectWeatherRapportBetween(Long.parseLong(hours[7]), Long.parseLong(hours[8]), "weather");
        Double[] hour7AvgData = weatherSummary.getAverageValuesIDayRapport(hour7Data);

        List<Double[]> hour8Data = sqlHandler.selectWeatherRapportBetween(Long.parseLong(hours[8]), Long.parseLong(hours[9]), "weather");
        Double[] hour8AvgData = weatherSummary.getAverageValuesIDayRapport(hour8Data);

        List<Double[]> hour9Data = sqlHandler.selectWeatherRapportBetween(Long.parseLong(hours[9]), Long.parseLong(hours[10]), "weather");
        Double[] hour9AvgData = weatherSummary.getAverageValuesIDayRapport(hour9Data);

        List<Double[]> hour10Data = sqlHandler.selectWeatherRapportBetween(Long.parseLong(hours[10]), Long.parseLong(hours[11]), "weather");
        Double[] hour10AvgData = weatherSummary.getAverageValuesIDayRapport(hour10Data);

        List<Double[]> hour11Data = sqlHandler.selectWeatherRapportBetween(Long.parseLong(hours[11]), Long.parseLong(hours[12]), "weather");
        Double[] hour11AvgData = weatherSummary.getAverageValuesIDayRapport(hour11Data);

        List<Double[]> hour12Data = sqlHandler.selectWeatherRapportBetween(Long.parseLong(hours[12]), Long.parseLong(hours[13]), "weather");
        Double[] hour12AvgData = weatherSummary.getAverageValuesIDayRapport(hour12Data);

        List<Double[]> hour13Data = sqlHandler.selectWeatherRapportBetween(Long.parseLong(hours[13]), Long.parseLong(hours[14]), "weather");
        Double[] hour13AvgData = weatherSummary.getAverageValuesIDayRapport(hour13Data);

        List<Double[]> hour14Data = sqlHandler.selectWeatherRapportBetween(Long.parseLong(hours[14]), Long.parseLong(hours[15]), "weather");
        Double[] hour14AvgData = weatherSummary.getAverageValuesIDayRapport(hour14Data);

        List<Double[]> hour15Data = sqlHandler.selectWeatherRapportBetween(Long.parseLong(hours[15]), Long.parseLong(hours[16]), "weather");
        Double[] hour15AvgData = weatherSummary.getAverageValuesIDayRapport(hour15Data);

        List<Double[]> hour16Data = sqlHandler.selectWeatherRapportBetween(Long.parseLong(hours[16]), Long.parseLong(hours[17]), "weather");
        Double[] hour16AvgData = weatherSummary.getAverageValuesIDayRapport(hour16Data);

        List<Double[]> hour17Data = sqlHandler.selectWeatherRapportBetween(Long.parseLong(hours[17]), Long.parseLong(hours[18]), "weather");
        Double[] hour17AvgData = weatherSummary.getAverageValuesIDayRapport(hour17Data);

        List<Double[]> hour18Data = sqlHandler.selectWeatherRapportBetween(Long.parseLong(hours[18]), Long.parseLong(hours[19]), "weather");
        Double[] hour18AvgData = weatherSummary.getAverageValuesIDayRapport(hour18Data);

        List<Double[]> hour19Data = sqlHandler.selectWeatherRapportBetween(Long.parseLong(hours[19]), Long.parseLong(hours[20]), "weather");
        Double[] hour19AvgData = weatherSummary.getAverageValuesIDayRapport(hour19Data);

        List<Double[]> hour20Data = sqlHandler.selectWeatherRapportBetween(Long.parseLong(hours[20]), Long.parseLong(hours[21]), "weather");
        Double[] hour20AvgData = weatherSummary.getAverageValuesIDayRapport(hour20Data);

        List<Double[]> hour21Data = sqlHandler.selectWeatherRapportBetween(Long.parseLong(hours[21]), Long.parseLong(hours[22]), "weather");
        Double[] hour21AvgData = weatherSummary.getAverageValuesIDayRapport(hour21Data);

        List<Double[]> hour22Data = sqlHandler.selectWeatherRapportBetween(Long.parseLong(hours[22]), Long.parseLong(hours[23]), "weather");
        Double[] hour22AvgData = weatherSummary.getAverageValuesIDayRapport(hour22Data);

        List<Double[]> hour23Data = sqlHandler.selectWeatherRapportBetween(Long.parseLong(hours[23]), Long.parseLong(hours[24]), "weather");
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
}
