package no.ntnu.idata2304.group8.databasehandler;

public class DataBaseHandler {
  no.ntnu.idata2304.group8.databasehandler.SQLHandler sqlHandler = new SQLHandler();


  public void writeToDB() {
    sqlHandler.insert(12345, "testing");
    //TODO: Implement method
  }


  public void readFromDB(){
    //TODO: Implement method
  }


  public void updateData(){
    //TODO: Implement method
  }

  /**
   * Splits the incoming string into separate numbers and returns an int[].
   *
   * @param incomingString - String of numbers from sensorNode
   * @return Int[]
   */
  public static int[] convertStringToArray(String incomingString) {
    int[] intArray = new int[5];
    String[] parts = incomingString.split("(?<=\\G..)");
    for (int i = 0; i < parts.length; i++) {
      intArray[i] = Integer.parseInt(parts[i]);
    }
    return intArray;
  }


  //TESTING

  public static void main(String[] args)
  {
    int[] a = convertStringToArray("112233113");
    for (int i = 0; i < a.length; i++) {
      System.out.println(a[i]);
      DataBaseHandler dataBaseHandler = new DataBaseHandler();
      dataBaseHandler.writeToDB();
    }
  }


}