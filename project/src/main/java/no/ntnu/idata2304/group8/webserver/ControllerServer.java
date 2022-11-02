package no.ntnu.idata2304.group8.webserver;


import com.google.gson.Gson;
import no.ntnu.idata2304.group8.databasehandler.SQLHandler;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

@RestController
public class ControllerServer {
  SQLHandler sqlHandler = new SQLHandler();

  @PostMapping(value = "/api/front")
  public void processJSON(@RequestBody Map<String, Object> payload) {
    System.out.println(payload);
  }

  @GetMapping("/api/front")
  public String getReports() throws IOException, ParseException {
    SQLHandler sqlHandler = new SQLHandler();
//    ArrayList<String> list = sqlHandler.selectAll();

    ArrayList<String> list = sqlHandler.selectDate(1666262249300L, 1666262249400L);
    Gson gson = new Gson();
    String jsonArray = gson.toJson(list);
    return jsonArray;
  }
}
