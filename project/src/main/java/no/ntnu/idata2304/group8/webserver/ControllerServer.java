package no.ntnu.idata2304.group8.webserver;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ControllerServer {

  @GetMapping("/today")
  public String today(
      @RequestParam(name = "name", required = false, defaultValue = "World") String name,
      Model model) {
    model.addAttribute("name", name);
    return "weather_report";
  }

  @GetMapping("/tomorrow")
  public String tomorrow(
      @RequestParam(name = "name", required = false, defaultValue = "World") String name,
      Model model) {
    model.addAttribute("name", name);
    return "weather_report";
  }

}
