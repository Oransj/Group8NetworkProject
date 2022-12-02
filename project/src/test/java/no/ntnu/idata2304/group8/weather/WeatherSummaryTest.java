package no.ntnu.idata2304.group8.weather;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import no.ntnu.idata2304.group8.logic.weather.WeatherSummary;
import org.junit.jupiter.api.Test;

/**
 * Tests for the Weather Summary class. Tests for positive,
 * negative, zero and no values for the functions
 * getDaySummary, getWeatherSummary and getAvgInInterval
 */
public class WeatherSummaryTest {
    private List<Double[]> dayData = new ArrayList<>();
    private WeatherSummary ws = new WeatherSummary();
    private DecimalFormat df = new DecimalFormat("0.0#");

    private void setUpZero() {
        // {[temperature, precipitation, pressure, light, wind]}
        dayData.add(new Double[]{0.0, 0.0, 0.0, 0.0, 0.0});
        dayData.add(new Double[]{0.0, 0.0, 0.0, 0.0, 0.0});
        dayData.add(new Double[]{0.0, 0.0, 0.0, 0.0, 0.0});
    }
    
    private void setUpPositive() {
        dayData.add(new Double[]{8.1, 0.4, 24.0, 1000.0, 1.3});
        dayData.add(new Double[]{8.6, 0.3, 24.0, 2000.0, 1.7});
        dayData.add(new Double[]{8.9, 0.5, 24.0, 3000.0, 2.3});
    }
    
    private void setUpNegative() {
        dayData.add(new Double[]{-8.1, -0.4, -24.0, -1000.0, -1.3});
        dayData.add(new Double[]{-8.6, -0.3, -24.0, -2000.0, -1.7});
        dayData.add(new Double[]{-8.9, -0.5, -24.0, -3000.0, -2.3});
    }
    
    @Test
    public void testGetDaySummaryWithPositiveValues() {
        setUpPositive();
    
        Double[] exp = new Double[]{8.10, 8.90, 0.40, 1.77};
        Double[] real = ws.getDaySummary(dayData);
        for (int i = 0; i < real.length; i++) {
            assertEquals(String.valueOf(exp[i]), df.format(real[i]));
        }
    }
    
    @Test
    public void testGetDaySummaryWithNegativeValues() {
        setUpNegative();
    
        Double[] expected = new Double[]{-8.90, -8.10, -0.40, -1.77};
        Double[] actual = ws.getDaySummary(dayData);
        for (int i = 0; i < actual.length; i++) {
            assertEquals(String.valueOf(expected[i]), df.format(actual[i]));
        }
    }
    
    @Test
    public void testGetDaySummaryWithZeroValues() {
        setUpZero();
    
        Double[] expected = new Double[]{0.00, 0.00, 0.00, 0.00};
        Double[] actual = ws.getDaySummary(dayData);
        for (int i = 0; i < actual.length; i++) {
            assertEquals(String.valueOf(expected[i]), df.format(actual[i]));
        }
    }
    
    @Test
    public void testGetDaySummaryWithNoValues() {
        assertThrows(IllegalArgumentException.class, () -> ws.getDaySummary(dayData));
    }
    
    @Test
    public void testGetAvgInIntervalWithPositiveValues() {
        setUpPositive();
    
        Double[] expected = new Double[]{8.53, 0.40, 24.00, 2000.00, 1.77};
        Double[] actual = ws.getAverageValuesInInterval(dayData);
        for (int i = 0; i < actual.length; i++) {
            assertEquals(String.valueOf(expected[i]), df.format(actual[i]));
        }
    }
    
    @Test
    public void testGetAvgInIntervalWithNegativeValues() {
        setUpNegative();
    
        Double[] expected = new Double[]{-8.53, -0.40, -24.00, -2000.00, -1.77};
        Double[] actual = ws.getAverageValuesInInterval(dayData);
        for (int i = 0; i < actual.length; i++) {
            assertEquals(String.valueOf(expected[i]), df.format(actual[i]));
        }
    }
    
    @Test
    public void testGetAvgInIntervalWithZeroValues() {
        setUpZero();
    
        Double[] expected = new Double[]{0.00, 0.00, 0.00, 0.00, 0.00};
        Double[] actual = ws.getAverageValuesInInterval(dayData);
        for (int i = 0; i < actual.length; i++) {
            assertEquals(String.valueOf(expected[i]), df.format(actual[i]));
        }
    }
    
    @Test
    public void testGetAvgInIntervalWithNoValues() {
        assertThrows(IllegalArgumentException.class, () -> ws.getAverageValuesInInterval(dayData));
    }
    
//    @Test
//    public void testGetWeatherSummaryWithPositiveValues() {
//        setUpPositive();
//
//        List<Integer> expected = Arrays.asList(1, 1, 0);
//        List<Integer> actual = ws.getWeatherSummary(dayData.get(0));
//
//        for (int i = 0; i < actual.size(); i++) {
//            assertEquals(expected.get(i), actual.get(i));
//        }
//    }
    
    @Test
    public void testGetWeatherSummaryWithNegativeValues() {
        setUpNegative();
        assertThrows(IllegalArgumentException.class, () -> ws.getWeatherSummary(dayData.get(0)));
    }
    
//    @Test
//    public void testGetWeatherSummaryWithZeroValues() {
//        setUpZero();
//
//        List<Integer> expected = Arrays.asList(-2, 0, 0);
//        List<Integer> actual = ws.getWeatherSummary(dayData.get(0));
//
//        for (int i = 0; i < actual.size(); i++) {
//            assertEquals(expected.get(i), actual.get(i));
//        }
//    }
    
    @Test
    public void testGetWeatherSummaryWithNoValues() {
        assertThrows(IllegalArgumentException.class, () -> ws.getWeatherSummary(new Double[0]));
    }
}
