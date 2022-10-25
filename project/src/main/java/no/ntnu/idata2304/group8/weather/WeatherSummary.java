package no.ntnu.idata2304.group8.weather;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Gives a simplified summary of the weather based on factors like
 * time, temperature, precipitation, atmospheric pressure, and wind.
 *
 * Sources
 * Sun/moon:    https://wolfcrow.com/wp-content/uploads/2013/04/QuantityLightChart.jpg
 *              https://shamay.com/content/images/2022/01/image.png
 * Rain/snow:   http://www.bom.gov.au/climate/data-services/content/faqs-elements.html
 * Thunder:     https://www.weather.gov/source/zhu/ZHU_Training_Page/thunderstorm_stuff/Thunderstorms/thunderstorms.htm
 */
public class WeatherSummary {
    public String convertSummaryToText(List<Boolean> summary) {
        return null;
    }
  
    /**
     * Get the weather summary for a data set.
     *
     * @param data Array of data in the format
     *             '[temperature, precipitation, pressure, light, wind_strength]'.
     * @return List of the weather summary in the format
     * '{sunOrMoon, rainOrSnow, thunder}', where each variable has numerical values.
     */
    public List<Integer> getWeatherSummary(String[] data) {
        List<Integer> weatherSummary = new ArrayList<>();
        
        // The items in the data array
        int temp = Integer.parseInt(data[0]);
        int precipitation = Integer.parseInt(data[1]);
        int pressure = Integer.parseInt(data[2]);
        int light = Integer.parseInt(data[3]);
        int wind = Integer.parseInt(data[4]);
        
        // The summaries
        int sunOrMoon = getSunSummary(light);
        int rainOrSnow = getPrecipitationSummary(temp, precipitation);
        int thunder = getThunderSummary(temp, rainOrSnow, pressure, wind);

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
    private int getSunSummary(int light) {
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
    private int getPrecipitationSummary(int temperature, int precipitation) {
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
     * @param pressure The atmospheric pressure (hPa).
     * @param wind The wind strength (m/s).
     * @return 0 for no thunder, 1 for thunder.
     */
    private int getThunderSummary(int temperature, int precipitationLevel, int pressure, int wind) {
        int thunder = 0;
        if ((temperature > 13 && precipitationLevel == 1)
        || (precipitationLevel == 2 && wind > 10)) {
            thunder = 1;
        }
        return thunder;
    }
    
    // TODO: Implement method to get changes in pressure, for a more complex summary method
}
