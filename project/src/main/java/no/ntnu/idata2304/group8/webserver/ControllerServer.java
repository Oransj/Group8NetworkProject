package no.ntnu.idata2304.group8.webserver;


import no.ntnu.idata2304.group8.databasehandler.SQLHandler;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class ControllerServer {
  SQLHandler sqlHandler = new SQLHandler();

  @PostMapping(value = "/api/front")
  public void processJSON(@RequestBody Map<String, Object> payload) {
    System.out.println(payload);
  }
}
