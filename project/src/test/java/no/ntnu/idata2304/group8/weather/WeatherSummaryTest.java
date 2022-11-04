package no.ntnu.idata2304.group8.weather;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class WeatherSummaryTest {
    private List<Float[]> dayData;
    private WeatherSummary ws;
    
    @BeforeEach
    public void setUp() {
        // {[temperature, precipitation, pressure, light, wind]}
        dayData = new ArrayList<>();
        dayData.add(new Float[]{8.1f, 0.4f, 24.0f, 1000.0f, 1.3f});
        dayData.add(new Float[]{8.6f, 0.3f, 24.0f, 2000.0f, 1.7f});
        dayData.add(new Float[]{8.9f, 0.5f, 24.0f, 3000.0f, 2.3f});
        ws = new WeatherSummary();
    }
    
    @Test
    public void testGetDaySummaryWithPositiveValues () {
        Float[] sum = new Float[]{8.10f, 8.90f, 0.40f, 1.766666f};
        assertEquals(sum, ws.getDaySummary(dayData));
    }
}
