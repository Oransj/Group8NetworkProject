package no.ntnu.idata2304.group8.webserver;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ControllerServer {

  @GetMapping("/test")
  public String today() {
    return "asdsadsadsad";
  }
}
