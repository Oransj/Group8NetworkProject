package no.ntnu.idata2304.group8.databasehandler;

import java.util.ArrayList;

public class DataBaseHandler {
  SQLHandler sqlHandler = new SQLHandler();




  public void writeToDB(ArrayList array) {
    //TODO: Implement method
    sqlHandler.insert(Integer.parseInt(array.get(0).toString()), //TIME
        Float.parseFloat(array.get(1).toString()), //TEMPERATURE
        Float.parseFloat(array.get(2).toString()), //PRECIPITATION
        Integer.parseInt(array.get(3).toString()), //AIR_PRESSURE
        Float.parseFloat(array.get(4).toString()), //LIGHT
        Float.parseFloat(array.get(5).toString()), //WIND_SPEED
        Float.parseFloat(array.get(6).toString())); //WIND_DIR
  }


  public void readFromDB(){
    //TODO: Implement method
  }


  public void updateData(){
    //TODO: Implement method
  }

  //TESTING

  public static void main(String[] args)
  {
    ArrayList test = new ArrayList<>();
    test.add(12345);
    test.add(12.2f);
    test.add(0.2f);
    test.add(22);
    test.add(100);
    test.add(2);
    test.add(90);
    DataBaseHandler data = new DataBaseHandler();
    data.writeToDB(test);
  }


}