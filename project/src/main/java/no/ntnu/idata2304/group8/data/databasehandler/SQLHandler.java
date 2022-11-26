package no.ntnu.idata2304.group8.data.databasehandler;

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

public class SQLHandler {
    /**
     * Connects to the SQL database
     * @return connection
     */

    private Connection connect() {
        // Location of SQLlite database
//        String fileName = "project/src/main/resources/database/test.db";
        String fileName = "D:\\NTNU\\Semester 3\\IDATA2304 Computer networks and network programming\\Group8NetworkProject\\project\\src\\main\\resources\\database\\test.db";
//        String fileName = "/home/ubuntu/project/src/main/resources/database/test.db";
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
     * Adds the given JSON object to the database
     * @param jsonObject The JSON object to be added to the database
     * @param database The database to save into: "weather"/"spike"
     */
    public void addData(JSONObject jsonObject, String database) {
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
                pstmt.setDouble(4,Double.parseDouble(air_pressure.get("hPa").toString()));
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
    public ArrayList selectAllBetween(Long dayStart, Long dayEnd, String database){
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
                    .add("Air_pressure", Json.createObjectBuilder()
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

    public List<Double[]> selectWeatherDataBetween(Long dayStart, Long dayEnd, String database){
        String sql = "SELECT Temprature, Precipitation, Air_pressure, Light, Wind_Speed " +
                "FROM " +database+
                " WHERE Time BETWEEN " +dayStart+ " AND " +dayEnd;
        List<Double[]> dataList = new ArrayList();

        try (Connection connection = this.connect();
             Statement stmt  = connection.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            // loop through the result set
            while (rs.next()) {
                Double[] data = new Double[]{rs.getDouble("Temprature"),
                        rs.getDouble("Precipitation"),
                        rs.getDouble("Air_pressure"),
                        rs.getDouble("Light"),
                        rs.getDouble("Wind_Speed")
                };
                dataList.add(data);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return dataList;
    }

    public List<Double[]> selectWeatherRapportBetween(Long dayStart, Long dayEnd, String database){
        String sql = "SELECT Temprature, Precipitation, Wind_Speed, Wind_dir " +
                "FROM " +database+
                " WHERE Time BETWEEN " +dayStart+ " AND " +dayEnd;
        List<Double[]> dataList = new ArrayList();

        try (Connection connection = this.connect();
             Statement stmt  = connection.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            // loop through the result set
            while (rs.next()) {
                Double[] data = new Double[]{rs.getDouble("Temprature"),
                        rs.getDouble("Precipitation"),
                        rs.getDouble("Wind_Speed"),
                        rs.getDouble("Wind_dir")
                };
                dataList.add(data);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return dataList;
    }

    public String selectLast(){
        String sql = "SELECT * " +
                "FROM weather " +
                "DESC LIMIT 1";
        JsonObject builder = null;
        try (Connection connection = this.connect();
             Statement stmt  = connection.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            // loop through the result set
            while (rs.next()) {
                builder = Json.createObjectBuilder()
                        .add("Time", Json.createObjectBuilder()
                                .add("ms", rs.getLong("Time")))
                        .add("Temperature", Json.createObjectBuilder()
                                .add("celsius", rs.getDouble("Temprature")))
                        .add("Precipitation", Json.createObjectBuilder()
                                .add("mm", rs.getDouble("Precipitation")))
                        .add("Air_pressure", Json.createObjectBuilder()
                                .add("hPa", rs.getDouble("Air_pressure")))
                        .add("Light", Json.createObjectBuilder()
                                .add("lux", rs.getDouble("Light")))
                        .add("Wind",
                                Json.createObjectBuilder().add("W_speed",rs.getDouble("Wind_Speed"))
                                        .add("W_direction",rs.getDouble("Wind_Dir")))
                        .build();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        assert builder != null;
        return builder.toString();
    }

    public List<String> selectLastTen(Long ms){
        String sql = "SELECT * " +
                "FROM weather " +
                "DESC LIMIT 10";
        ArrayList<String> jArray = new ArrayList();
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
                        .add("Air_pressure", Json.createObjectBuilder()
                                .add("hPa", rs.getLong("Air_pressure")))
                        .add("Light", Json.createObjectBuilder()
                                .add("lux", rs.getLong("Light")))
                        .add("Wind",
                                Json.createObjectBuilder().add("W_speed",rs.getLong("Wind_Speed"))
                                        .add("W_direction",rs.getLong("Wind_Dir")))
                        .build();
                jArray.add(builder.toString());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return jArray;
    }

    public void delete(Long ms) {
        String sql = "DELETE FROM Weather WHERE Time =" + ms;

        try(Connection connection = this.connect();
            Statement stmt  = connection.createStatement();) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public String select(Long ms){
        String sql = "SELECT * " +
                "FROM weather " +
                "WHERE Time = " + ms;
        JsonObject builder = null;
        try (Connection connection = this.connect();
             Statement stmt  = connection.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            // loop through the result set
            while (rs.next()) {
                builder = Json.createObjectBuilder()
                        .add("Time", Json.createObjectBuilder()
                                .add("ms", rs.getLong("Time")))
                        .add("Temperature", Json.createObjectBuilder()
                                .add("celsius", rs.getDouble("Temprature")))
                        .add("Precipitation", Json.createObjectBuilder()
                                .add("mm", rs.getDouble("Precipitation")))
                        .add("Air_pressure", Json.createObjectBuilder()
                                .add("hPa", rs.getDouble("Air_pressure")))
                        .add("Light", Json.createObjectBuilder()
                                .add("lux", rs.getDouble("Light")))
                        .add("Wind",
                                Json.createObjectBuilder().add("W_speed",rs.getDouble("Wind_Speed"))
                                        .add("W_direction",rs.getDouble("Wind_Dir")))
                        .build();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        assert builder != null;
        return builder == null ? "null" : builder.toString();
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
                    .add("Air_pressure", Json.createObjectBuilder()
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
}

