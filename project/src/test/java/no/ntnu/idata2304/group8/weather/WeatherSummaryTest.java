package no.ntnu.idata2304.group8.weather;

import org.junit.jupiter.api.Test;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the Weather Summary class. Tests for positive,
 * negative, zero and no values for the functions
 * getDaySummary, getWeatherSummary and getAvgInInterval
 */
public class WeatherSummaryTest {
    private List<Float[]> dayData = new ArrayList<>();
    private WeatherSummary ws = new WeatherSummary();
    private DecimalFormat df = new DecimalFormat("0.0#");

    private void setUpZero() {
        // {[temperature, precipitation, pressure, light, wind]}
        dayData.add(new Float[]{0.0f, 0.0f, 0.0f, 0.0f, 0.0f});
        dayData.add(new Float[]{0.0f, 0.0f, 0.0f, 0.0f, 0.0f});
        dayData.add(new Float[]{0.0f, 0.0f, 0.0f, 0.0f, 0.0f});
    }
    
    private void setUpPositive() {
        dayData.add(new Float[]{8.1f, 0.4f, 24.0f, 1000.0f, 1.3f});
        dayData.add(new Float[]{8.6f, 0.3f, 24.0f, 2000.0f, 1.7f});
        dayData.add(new Float[]{8.9f, 0.5f, 24.0f, 3000.0f, 2.3f});
    }
    
    private void setUpNegative() {
        dayData.add(new Float[]{-8.1f, -0.4f, -24.0f, -1000.0f, -1.3f});
        dayData.add(new Float[]{-8.6f, -0.3f, -24.0f, -2000.0f, -1.7f});
        dayData.add(new Float[]{-8.9f, -0.5f, -24.0f, -3000.0f, -2.3f});
    }
    
    @Test
    public void testGetDaySummaryWithPositiveValues () {
        setUpPositive();
        
        Float[] exp = new Float[]{8.10f, 8.90f, 0.40f, 1.77f};
        Float[] real = ws.getDaySummary(dayData);
        for (int i = 0; i < real.length; i++) {
            assertEquals(String.valueOf(exp[i]), df.format(real[i]));
        }
    }
    
    @Test
    public void testGetDaySummaryWithNegativeValues () {
        setUpNegative();
        
        Float[] expected = new Float[]{-8.90f, -8.10f, -0.40f, -1.77f};
        Float[] actual = ws.getDaySummary(dayData);
        for (int i = 0; i < actual.length; i++) {
            assertEquals(String.valueOf(expected[i]), df.format(actual[i]));
        }
    }
    
    @Test
    public void testGetDaySummaryWithZeroValues () {
        setUpZero();
        
        Float[] expected = new Float[]{0.00f, 0.00f, 0.00f, 0.00f};
        Float[] actual = ws.getDaySummary(dayData);
        for (int i = 0; i < actual.length; i++) {
            assertEquals(String.valueOf(expected[i]), df.format(actual[i]));
        }
    }
    
    @Test
    public void testGetDaySummaryWithNoValues () {
        assertThrows(IllegalArgumentException.class, () -> ws.getDaySummary(dayData));
    }
    
    @Test
    public void testGetAvgInIntervalWithPositiveValues() {
        setUpPositive();
        
        Float[] expected = new Float[]{8.53f, 0.40f, 24.00f, 2000.00f, 1.77f};
        Float[] actual = ws.getAverageValuesInInterval(dayData);
        for (int i = 0; i < actual.length; i++) {
            assertEquals(String.valueOf(expected[i]), df.format(actual[i]));
        }
    }
    
    @Test
    public void testGetAvgInIntervalWithNegativeValues() {
        setUpNegative();
        
        Float[] expected = new Float[]{-8.53f, -0.40f, -24.00f, -2000.00f, -1.77f};
        Float[] actual = ws.getAverageValuesInInterval(dayData);
        for (int i = 0; i < actual.length; i++) {
            assertEquals(String.valueOf(expected[i]), df.format(actual[i]));
        }
    }
    
    @Test
    public void testGetAvgInIntervalWithZeroValues() {
        setUpZero();
    
        Float[] expected = new Float[]{0.00f, 0.00f, 0.00f, 0.00f, 0.00f};
        Float[] actual = ws.getAverageValuesInInterval(dayData);
        for (int i = 0; i < actual.length; i++) {
            assertEquals(String.valueOf(expected[i]), df.format(actual[i]));
        }
    }
    
    @Test
    public void testGetAvgInIntervalWithNoValues () {
        assertThrows(IllegalArgumentException.class, () -> ws.getAverageValuesInInterval(dayData));
    }
    
    @Test
    public void testGetWeatherSummaryWithPositiveValues() {
        setUpPositive();
        
        List<Integer> expected = Arrays.asList(1, 1, 0);
        List<Integer> actual = ws.getWeatherSummary(dayData.get(0));
        
        for (int i = 0; i < actual.size(); i++) {
            assertEquals(expected.get(i), actual.get(i));
        }
    }
    
    @Test
    public void testGetWeatherSummaryWithNegativeValues() {
        setUpNegative();
        assertThrows(IllegalArgumentException.class, () -> ws.getWeatherSummary(dayData.get(0)));
    }
    
    @Test
    public void testGetWeatherSummaryWithZeroValues() {
        setUpZero();
        
        List<Integer> expected = Arrays.asList(-2, 0, 0);
        List<Integer> actual = ws.getWeatherSummary(dayData.get(0));
        
        for (int i = 0; i < actual.size(); i++) {
            assertEquals(expected.get(i), actual.get(i));
        }
    }
    
    @Test
    public void testGetWeatherSummaryWithNoValues() {
        assertThrows(IllegalArgumentException.class, () -> ws.getWeatherSummary(new Float[0]));
    }
}
