package no.ntnu.idata2304.group8.weather;

import java.util.ArrayList;
import java.util.List;

public class WeatherSummary {
    private boolean day; // Affected by time of day?
    private boolean rain; // Affected temperature by precipitation
    private boolean cloud; // Affected by day variable and amount of light
    private boolean snow; // Affected by temperature and precipitation
    private boolean thunder; // Affected by temperature (warm), precipitation, pressure and wind
    
    /**
     * Get the weather summary for a data set.
     *
     * @param data Array of data in the format
     *             '[time, temperature, precipitation, pressure, light, wind_strength]'
     * @return List of the weather summary in the format
     *         '{day, rain, cloud, snow, thunder}'
     */
    private List<Boolean> getWeatherSummary(String[] data) {
        List<Boolean> weatherSummary = new ArrayList<>();
        
        // getDaySummary
        // getRainSummary
        // getCloudSummary
        // getSnowSummary
        // getThunderSummary
        
        return weatherSummary;
    }
}
