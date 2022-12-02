package no.ntnu.idata2304.group8.logic.webserver;

import java.util.ArrayList;
import org.springframework.web.bind.annotation.*;

@RestController
public class ControllerServer {
    WeatherSorting weatherSorting = new WeatherSorting();
    
    @PostMapping(value = "/api/getData")
    public ArrayList<Double[]> getHomePageHandler(@RequestBody String[] days) {
        return weatherSorting.getHomePage(days);
    }
    
    @PostMapping(value = "/api/getWeatherType")
    public String[] getWeatherType(@RequestBody String[] days) {
        return weatherSorting.getWeatherType(days);
    }
    
    @PostMapping(value = "/api/getDayRapport")
    public ArrayList<Double[]> getDayRapport(@RequestBody String[] hours) {
        return weatherSorting.getDayRapport(hours);
    }
    
    @PostMapping(value = "/api/getWeatherTypeDayRapport")
    public String[] getWeatherTypeDayRapport(@RequestBody String[] hours) {
        return weatherSorting.getWeatherTypeDayRapport(hours);
    }
}
