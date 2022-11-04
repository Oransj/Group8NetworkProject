package no.ntnu.idata2304.group8.weather;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Get a simplified summary of the weather based on factors like
 * time, temperature, precipitation, atmospheric pressure, and wind.
 *
 * Sources
 * Sun/moon:    https://wolfcrow.com/wp-content/uploads/2013/04/QuantityLightChart.jpg
 *              https://shamay.com/content/images/2022/01/image.png
 * Rain/snow:   http://www.bom.gov.au/climate/data-services/content/faqs-elements.html
 * Thunder:     https://www.weather.gov/source/zhu/ZHU_Training_Page/thunderstorm_stuff/Thunderstorms/thunderstorms.htm
 */
public class WeatherSummary {
    /**
     * Get the summary for a day.
     *
     * @param dayData A list of weather data rows for the entire day.
     *                Each row in the format {temperature, precipitation, pressure, light, wind}.
     * @return  A list of minimum temperature, maximum temperature,
     *          average precipitation and average wind speed.
     */
    public Float[] getDaySummary(List<Float[]> dayData) {
        if (dayData == null || dayData.isEmpty()) {
            throw new IllegalArgumentException("Data cannot be null or empty.");
        }
        
        float minTemp = dayData.get(0)[0];
        float maxTemp = dayData.get(0)[0];
        float totalPrecip = 0;
        float totalWind = 0;
        float avgPrecip;
        float avgWind;
    
        for (Float[] row : dayData) {
            if (row[0] < minTemp) {
                minTemp = row[0];
            } else if (row[0] > maxTemp) {
                maxTemp = row[0];
            }
            
            totalPrecip += row[1];
            totalWind += row[4];
        }
        
        avgPrecip = totalPrecip / dayData.size();
        avgWind = totalWind / dayData.size();
        
        return new Float[]{minTemp, maxTemp, avgPrecip, avgWind};
    }
    
    /**
     * Get list of average values from a list of rows with values.
     *
     * @param dataRows A list of weather data rows for a time interval.
     *                 Each row is in the format {temperature,
     *                 precipitation, pressure, light, wind}.
     * @return  An array of float values in the format
     *          {temperature, precipitation, pressure, light, wind}.
     */
    public Float[] getAverageValuesInInterval(List<Float[]> dataRows) {
        if (dataRows == null || dataRows.isEmpty()) {
            throw new IllegalArgumentException("Data cannot be null or empty.");
        }
        
        float totalTemp = 0;
        float totalPrecip = 0;
        float totalPressure = 0;
        float totalLight = 0;
        float totalWind = 0;
        int size = dataRows.size();
        
        for (Float[] row : dataRows) {
            totalTemp += row[0];
            totalPrecip += row[1];
            totalPressure += row[2];
            totalLight += row[3];
            totalWind += row[4];
        }
        
        return new Float[]{totalTemp/size, totalPrecip/size,
            totalPressure/size, totalLight/size, totalWind/size};
    }
    
    /**
     * Take numerical values representing weather types and convert it to a single text value.
     *
     * @param summary Weather values that correspond to a type of weather.
     *                {sunOrMoon, rainOrSnow, thunder}.
     * @return The type of weather.
     */
    public String getWeatherType(List<Integer> summary) {
        if (summary == null || summary.isEmpty()) {
            throw new IllegalArgumentException("Data cannot be null or empty.");
        }
        
        // The values in the summary list
        int sunOrMoon = summary.get(0);
        int rainOrSnow = summary.get(1);
        int thunder = summary.get(2);
        
        String weatherAsString;
        
        // Based on the summary list values, find the corresponding weather types
        if (thunder == 1) { // Thunder
            weatherAsString = "thunder";
        } else if (rainOrSnow == 0) { // No precipitation
            weatherAsString = switch (sunOrMoon) {
                case -2 -> "cloud-with-moon";
                case -1 -> "moon";
                case 0 -> "cloud";
                case 1 -> "cloud-with-sun";
                case 2 -> "sun";
                default -> "";
            };
        } else if (rainOrSnow < 0) { // Snow
             if (sunOrMoon == 1 || sunOrMoon == 2) {
                 weatherAsString = "snow-with-sun";
             } else {
                 weatherAsString = "snow";
             }
        } else { // Rain
            if (sunOrMoon == 1 || sunOrMoon == 2) {
                weatherAsString = "rain-with-sun";
            } else {
                weatherAsString = "rain";
            }
        }
        
        return weatherAsString;
    }
  
    /**
     * Get the weather summary for a data set.
     *
     * @param data Array of data in the format
     *             '[temperature, precipitation, pressure, light, wind speed]'.
     * @return List of the weather summary in the format
     * '{sunOrMoon, rainOrSnow, thunder}', where each variable has numerical values.
     */
    public List<Integer> getWeatherSummary(Float[] data) {
        if (data == null || data.length == 0) {
            throw new IllegalArgumentException("Data cannot be null or empty.");
        }
        
        List<Integer> weatherSummary = new ArrayList<>();
        
        // The items in the data array
        float temp = data[0];
        float precipitation = data[1];
        float pressure = data[2];
        float light = data[3];
        float wind = data[4];
        
        // The summaries
        int sunOrMoon = getSunSummary(light);
        int rainOrSnow = getPrecipitationSummary(temp, precipitation);
        int thunder = getThunderSummary(temp, rainOrSnow, wind);

        // Add the info to the map
        weatherSummary.add(sunOrMoon);
        weatherSummary.add(rainOrSnow);
        weatherSummary.add(thunder);
        
        return weatherSummary;
    }
    
    /**
     * Get a summary for the sun/moon.
     *
     * @param light The amount of light (lux).
     * @return -2 for cloudy night, -1 for moonlight, 0 for clouds, 1 for clouds and sun, 2 for sun.
     */
    private int getSunSummary(float light) {
        if (light < 0) {
            throw new IllegalArgumentException("Light cannot be below zero.");
        }
        
        int sun;
        if (light < 1) {
            sun = -2; // Cloudy night
        } else if (light < 10) {
            sun = -1; // Night
        } else if (light < 1000) {
            sun = 0; // Clouds
        } else if (light < 10000){
            sun = 1; // Sun with clouds
        } else {
            sun = 2; // Sun
        }
        return sun;
    }
    
    /**
     * Get a summary for the rain/snow.
     *
     * @param temperature The temperature (celsius).
     * @param precipitation The amount of precipitation (millimeter).
     * @return -2 for much snow, -1 for snow, 0 for no precipitation, 1 for rain, 2 for much rain.
     */
    private int getPrecipitationSummary(float temperature, float precipitation) {
        if (precipitation < 0) {
            throw new IllegalArgumentException("Precipitation cannot be below zero.");
        }
        
        int rain;
        if (precipitation < 0.2) {
            rain = 0;
        } else if (precipitation > 10) {
            if (temperature < 0) {
                rain = -2;
            } else {
                rain = 2;
            }
        } else {
            if (temperature < 0) {
                rain = -1;
            } else {
                rain = 1;
            }
        }
        return rain;
    }
    
    /**
     * Get a summary for the thunder.
     *
     * @param temperature The temperature (celsius).
     * @param precipitationLevel The precipitation level (-1, 0, 1).
     * @param wind The wind speed (m/s).
     * @return 0 for no thunder, 1 for thunder.
     */
    private int getThunderSummary(float temperature, int precipitationLevel, float wind) {
        if (wind < 0) {
            throw new IllegalArgumentException("Wind speed cannot be below zero.");
        }
        
        int thunder = 0;
        if ((temperature > 13 && precipitationLevel == 1)
        || (precipitationLevel == 2 && wind > 10)) {
            thunder = 1;
        }
        return thunder;
    }
    
    // TODO: Implement method to get changes in pressure, for a more complex thunder summary method
}
