package no.ntnu.idata2304.group8.databasehandler;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import javax.json.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//TODO: fix data types in database to use the proper ones


public class SQLHandler {
    /**
     * Connects to the SQL database
     * @return connection
     */

    private Connection connect() {
        // Location of SQLlite database
        String fileName = "project/src/main/resources/database/test.db";
        String url = "jdbc:sqlite:" + fileName;
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return connection;
    }

    //TODO: CHANGE addData() to use newly decided upon JSON format
    public void addData() throws IOException, ParseException {
        String fileName = "src/main/resources/database/test.json";
        Object obj = new JSONParser().parse(new FileReader(fileName));
        JSONObject jo = (JSONObject) obj;
        long millis = (long) jo.get("Millis");
        double temperature = (double) jo.get("Temperature");
        double precipitation = (double) jo.get("Precipitation");
        double air_pressure = (double) jo.get("Air_pressure");
        double light = (double) jo.get("Light");
        long wind_speed = (long) jo.get("Wind_Speed");
        double wind_dir = (double) jo.get("Wind_Dir");

        String sql = "INSERT INTO Weather(Time, Temprature, Precipitation, Air_pressure, Light, Wind_Speed, Wind_dir) VALUES(?,?,?,?,?,?,?)";
        try (Connection connection = this.connect();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, millis);
            pstmt.setDouble(2, temperature);
            pstmt.setDouble(3,precipitation);
            pstmt.setDouble(4,air_pressure);
            pstmt.setDouble(5,light);
            pstmt.setDouble(6,wind_speed);
            pstmt.setDouble(7,wind_dir);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * select all rows in the table and returns a ArrayList with all entries
     * @Return - List of all values
     * TODO: CHANGE TO PROPER DATABASE AND CHANGE QUERY ACCORDINGLY
     */
    public JsonObject selectAll(){
        String sql = "SELECT Time, Temprature, Precipitation, Air_pressure, Light, Wind_Speed, Wind_dir FROM Weather";
        JsonObject json = null;
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
                json = builder;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return json;
    }

    public List getAllAtTime(int Time){
        String sql = "SELECT Temprature, Precipitation, Air_pressure, Light, Wind_Speed, Wind_dir FROM Weather WHERE Time = ?;";

        ArrayList array = new ArrayList<>();

        try (Connection connection = this.connect();
             PreparedStatement pstmt  = connection.prepareStatement(sql)) {
            pstmt.setInt(1,Time);
            ResultSet rs  = pstmt.executeQuery();

            // loop through the result set
            while (rs.next()) {
                array.add(rs.getFloat("Temprature"));
                array.add(rs.getFloat("Precipitation"));
                array.add(rs.getInt("Air_pressure"));
                array.add(rs.getFloat("Light"));
                array.add(rs.getFloat("Wind_Speed"));
                array.add((rs.getFloat("Wind_dir")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return array;
    }

    /**
     * @param args the command line arguments
     */
    //TESTING

    public static void main(String[] args) throws IOException, ParseException {
        SQLHandler app = new SQLHandler();


        System.out.println(app.selectAll());




    }
}

