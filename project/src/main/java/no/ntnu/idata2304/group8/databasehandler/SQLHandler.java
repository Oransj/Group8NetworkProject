package no.ntnu.idata2304.group8.databasehandler;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.json.Json;
import javax.json.JsonObject;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

//TODO: fix data types in database to use the proper ones


public class SQLHandler {
    /**
     * Connects to the SQL database
     * @return connection
     */

    private Connection connect() {
        // Location of SQLlite database
        String fileName = "src/main/resources/database/test.db";
        String url = "jdbc:sqlite:" + fileName;
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return connection;
    }

    /**
     * Reads JSON and adds data to database
     * @throws IOException
     * @throws ParseException
     */
        //TODO: TAKE A JSON AS PARAMETER THEN READ FROM THAT
    public void addData(JSONObject jsonObject, String database) throws IOException, ParseException {
        for (int i = 1; i <= jsonObject.size(); i++) {
            String read = "Reading" + i;
            try{
            JSONObject object = (JSONObject) jsonObject.get(read);
            JSONObject time = (JSONObject) object.get("Time");
            JSONObject temperature = (JSONObject) object.get("Temperature");
            JSONObject precipitaion = (JSONObject) object.get("Precipitaion");
            JSONObject air_pressure = (JSONObject) object.get("Air_pressure");
            JSONObject light = (JSONObject) object.get("Light");
            JSONObject wind = (JSONObject) object.get("Wind");

            String sql = "INSERT INTO "+database+"(Time, Temprature, Precipitation, Air_pressure, Light, Wind_Speed, Wind_dir) VALUES(?,?,?,?,?,?,?)";
            try (Connection connection = this.connect();
                PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setLong(1, Long.parseLong(time.get("ms").toString()));
                pstmt.setDouble(2, Double.parseDouble(temperature.get("celsius").toString()));
                pstmt.setDouble(3,Double.parseDouble(precipitaion.get("mm").toString()));
                pstmt.setDouble(4,Integer.parseInt(air_pressure.get("hPa").toString()));
                pstmt.setDouble(5,Double.parseDouble(light.get("lux").toString()));
                pstmt.setDouble(6,Double.parseDouble(wind.get("W_speed").toString()));
                pstmt.setDouble(7,Double.parseDouble(wind.get("W_direction").toString()));
                pstmt.executeUpdate();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            } catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
    }



    /**
     * Returns an arraylist of all database entries between dayStart and dayEnd
     * @Return - ArrayList
     * TODO: CHANGE TO PROPER DATABASE AND CHANGE QUERY ACCORDINGLY
     */
    public ArrayList selectDate(String database, long dayStart, long dayEnd){
        String sql = "SELECT Time, Temprature, Precipitation, Air_pressure, Light, Wind_Speed, Wind_dir " +
                     "FROM " +database+
                     " WHERE Time BETWEEN " +dayStart+ " AND " +dayEnd;
        ArrayList jArray = new ArrayList();
        try (Connection connection = this.connect();
             Statement stmt  = connection.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            // loop through the result set
            while (rs.next()) {
                JsonObject builder = Json.createObjectBuilder()
                    .add("Time", Json.createObjectBuilder()
                        .add("ms", rs.getLong("Time")))
                    .add("Temperature", Json.createObjectBuilder()
                        .add("celsius", rs.getLong("Temprature")))
                    .add("Precipitation", Json.createObjectBuilder()
                        .add("mm", rs.getLong("Precipitation")))
                    .add("Air_Pressure", Json.createObjectBuilder()
                        .add("hPa", rs.getLong("Air_pressure")))
                    .add("Light", Json.createObjectBuilder()
                        .add("lux", rs.getLong("Light")))
                    .add("Wind",
                        Json.createObjectBuilder().add("W_speed",rs.getLong("Wind_Speed"))
                            .add("W_direction",rs.getLong("Wind_Dir")))
                    .build();
                jArray.add(builder);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return jArray;
    }



    /**
     * select all rows in the table and returns a ArrayList with all entries
     * @Return - List of all values
     * TODO: CHANGE TO PROPER DATABASE AND CHANGE QUERY ACCORDINGLY
     */
    public ArrayList selectAll(String database){
        String sql = "SELECT Time, Temprature, Precipitation, Air_pressure, Light, Wind_Speed, Wind_dir FROM " +database;
        ArrayList jArray = new ArrayList();
        try (Connection connection = this.connect();
             Statement stmt  = connection.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            // loop through the result set
            while (rs.next()) {
                JsonObject builder = Json.createObjectBuilder()
                    .add("Time", Json.createObjectBuilder()
                        .add("ms", rs.getLong("Time")))
                    .add("Temperature", Json.createObjectBuilder()
                        .add("celsius", rs.getLong("Temprature")))
                    .add("Precipitation", Json.createObjectBuilder()
                        .add("mm", rs.getLong("Precipitation")))
                    .add("Air_Pressure", Json.createObjectBuilder()
                        .add("hPa", rs.getLong("Air_pressure")))
                    .add("Light", Json.createObjectBuilder()
                        .add("lux", rs.getLong("Light")))
                        .add("Wind",
                            Json.createObjectBuilder().add("W_speed",rs.getLong("Wind_Speed"))
                                .add("W_direction",rs.getLong("Wind_Dir")))
                    .build();
                jArray.add(builder);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return jArray;
    }


    /**
     * @param args the command line arguments
     */
    //TESTING

    public static void main(String[] args) throws IOException, ParseException {
        SQLHandler app = new SQLHandler();

        System.out.println(app.selectAll("SPIKE"));


    }
}

