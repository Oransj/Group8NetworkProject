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

/**
 * SQLHandler is responsible for writing to and reading from the SQL database.
 */
public class SqlHandler {
    private static final String TIME = "Time";
    private static final String TEMPERATURE = "Temperature";
    private static final String PRECIPITATION = "Precipitation";
    private static final String AIR_PRESSURE = "Air_pressure";
    private static final String LIGHT = "Light";
    private static final String WIND = "Wind";
    
    /**
     * Connects to the SQL database.
     *
     * @return The connection to the database.
     */
    private Connection connect() {
        // Location of SQLite database
         String fileName = "/home/ubuntu/project/src/main/resources/database/test.db";
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
     * Adds the given JSON object to the database.
     * @param jsonObject The JSON object to be added to the database.
     * @param database   The database to save into: "weather"/"spike".
     */
    public void addData(JSONObject jsonObject, String database) {
        for (int i = 1; i <= jsonObject.size(); i++) {
            String read = "Reading" + i;
            try {
                JSONObject object = (JSONObject) jsonObject.get(read);
                JSONObject time = (JSONObject) object.get(TIME);
                JSONObject temperature = (JSONObject) object.get(TEMPERATURE);
                JSONObject precipitation = (JSONObject) object.get(PRECIPITATION);
                JSONObject airPressure = (JSONObject) object.get(AIR_PRESSURE);
                JSONObject light = (JSONObject) object.get(LIGHT);
                JSONObject wind = (JSONObject) object.get(WIND);

                String sql = "INSERT INTO " + database + "(Time, Temperature, Precipitation, "
                    + "Air_pressure, Light, Wind_Speed, Wind_dir) VALUES(?,?,?,?,?,?,?)";
                try (Connection connection = this.connect();
                     PreparedStatement pstmt = connection.prepareStatement(sql)) {
                    pstmt.setLong(1, Long.parseLong(time.get("ms").toString()));
                    pstmt.setDouble(2, Double.parseDouble(temperature.get("celsius").toString()));
                    pstmt.setDouble(3, Double.parseDouble(precipitation.get("mm").toString()));
                    pstmt.setDouble(4, Double.parseDouble(airPressure.get("hPa").toString()));
                    pstmt.setDouble(5, Double.parseDouble(light.get("lux").toString()));
                    pstmt.setDouble(6, Double.parseDouble(wind.get("W_speed").toString()));
                    pstmt.setDouble(7, Double.parseDouble(wind.get("W_direction").toString()));
                    pstmt.executeUpdate();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
    
    /**
     * Return a list of all database entries between dayStart and dayEnd.
     *
     * @return list of all data for a day.
     */
    public ArrayList selectAllBetween(Long dayStart, Long dayEnd, String database) {
        String sql = "SELECT Time, Temperature, Precipitation, Air_pressure, Light, "
            + "Wind_Speed, Wind_dir " + "FROM " + database
            + " WHERE Time BETWEEN " + dayStart + " AND " + dayEnd;
        ArrayList<JsonObject> jsonObjectArray = new ArrayList<>();
        try (Connection connection = this.connect();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            // loop through the result set
            while (rs.next()) {
                JsonObject builder = Json.createObjectBuilder()
                    .add(TIME, Json.createObjectBuilder()
                        .add("ms", rs.getLong(TIME)))
                    .add(TEMPERATURE, Json.createObjectBuilder()
                        .add("celsius", rs.getLong(TEMPERATURE)))
                    .add(PRECIPITATION, Json.createObjectBuilder()
                        .add("mm", rs.getLong(PRECIPITATION)))
                    .add(AIR_PRESSURE, Json.createObjectBuilder()
                        .add("hPa", rs.getLong(AIR_PRESSURE)))
                    .add(LIGHT, Json.createObjectBuilder()
                        .add("lux", rs.getLong(LIGHT)))
                    .add(WIND,
                        Json.createObjectBuilder().add("W_speed", rs.getLong("Wind_Speed"))
                            .add("W_direction", rs.getLong("Wind_Dir")))
                    .build();
                jsonObjectArray.add(builder);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return jsonObjectArray;
    }
    
    /**
     * Queries the database between dayStart and dayEnd to return temperature, precipitation, air pressure, light and wind speed.
     *
     * @param dayStart The start point of the query.
     * @param dayEnd The end point of the query.
     * @param database Database to connect to.
     * @return a list with temperature, precipitation, air pressure, light and wind speed.
     */
    public List<Double[]> selectWeatherDataBetween(Long dayStart, Long dayEnd, String database) {
        String sql = "SELECT Temperature, Precipitation, Air_pressure, Light, Wind_Speed "
            + "FROM " + database + " WHERE Time BETWEEN " + dayStart + " AND " + dayEnd;
        List<Double[]> dataList = new ArrayList();
        
        try (Connection connection = this.connect();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            // loop through the result set
            while (rs.next()) {
                Double[] data = new Double[]{rs.getDouble(TEMPERATURE),
                    rs.getDouble(PRECIPITATION),
                    rs.getDouble(AIR_PRESSURE),
                    rs.getDouble(LIGHT),
                    rs.getDouble("Wind_Speed")
                };
                dataList.add(data);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return dataList;
    }

     /**
     * Queries the database between dayStart and dayEnd to return temperature, precipitation, wind direction and wind speed.
     * @param dayStart The start point of the query.
     * @param dayEnd The end point of the query.
     * @param database Database to connect to.
     * @return a list with temperature, precipitation, wind direction and wind speed.
     */
    public List<Double[]> selectWeatherRapportBetween(Long dayStart, Long dayEnd, String database) {
        String sql = "SELECT Temperature, Precipitation, Wind_Speed, Wind_dir "
            + "FROM " + database
            + " WHERE Time BETWEEN " + dayStart + " AND " + dayEnd;
        List<Double[]> dataList = new ArrayList();
        
        try (Connection connection = this.connect();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            // loop through the result set
            while (rs.next()) {
                Double[] data = new Double[]{rs.getDouble(TEMPERATURE),
                    rs.getDouble(PRECIPITATION),
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
    
    /**
     * Queries the weather table for the most recent entry.
     * @return String of the most recent entry.
     */
    public String selectLast() {
        String sql = "SELECT * " + "FROM weather "
            + "WHERE Time=(SELECT max(Time) FROM weather)";
        JsonObject builder = null;
        try (Connection connection = this.connect();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            // loop through the result set
            while (rs.next()) {
                builder = Json.createObjectBuilder()
                    .add(TIME, Json.createObjectBuilder()
                        .add("ms", rs.getLong(TIME)))
                    .add(TEMPERATURE, Json.createObjectBuilder()
                        .add("celsius", rs.getDouble(TEMPERATURE)))
                    .add(PRECIPITATION, Json.createObjectBuilder()
                        .add("mm", rs.getDouble(PRECIPITATION)))
                    .add(AIR_PRESSURE, Json.createObjectBuilder()
                        .add("hPa", rs.getDouble(AIR_PRESSURE)))
                    .add(LIGHT, Json.createObjectBuilder()
                        .add("lux", rs.getDouble(LIGHT)))
                    .add(WIND,
                        Json.createObjectBuilder().add("W_speed", rs.getDouble("Wind_Speed"))
                            .add("W_direction", rs.getDouble("Wind_Dir")))
                    .build();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        assert builder != null;
        return builder.toString();
    }

    /**
     * Queries the weather table for the 10 most recent readings.
     * @param Time in milliseconds.
     * @return list of the 10 most recent readings from the weather table.
     */
    public List<String> selectLastTen(Long ms) {
        String sql = "SELECT * " + "FROM weather "
            + "ORDER BY Time DESC LIMIT 10";
        ArrayList<String> jsonObjectArray = new ArrayList();
        try (Connection connection = this.connect();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            // loop through the result set
            while (rs.next()) {
                JsonObject builder = Json.createObjectBuilder()
                    .add(TIME, Json.createObjectBuilder()
                        .add("ms", rs.getLong(TIME)))
                    .add(TEMPERATURE, Json.createObjectBuilder()
                        .add("celsius", rs.getDouble(TEMPERATURE)))
                    .add(PRECIPITATION, Json.createObjectBuilder()
                        .add("mm", rs.getDouble(PRECIPITATION)))
                    .add(AIR_PRESSURE, Json.createObjectBuilder()
                        .add("hPa", rs.getDouble(AIR_PRESSURE)))
                    .add(LIGHT, Json.createObjectBuilder()
                        .add("lux", rs.getDouble(LIGHT)))
                    .add(WIND,
                        Json.createObjectBuilder().add("W_speed", rs.getDouble("Wind_Speed"))
                            .add("W_direction", rs.getDouble("Wind_Dir")))
                    .build();
                jsonObjectArray.add(builder.toString());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return jsonObjectArray;
    }
    
    /**
     * Deletes an entry from weather table where the Time equal ms.
     * @param ms Time in milliseconds.
     */
    public void delete(Long ms) {
        String sql = "DELETE FROM Weather WHERE Time =" + ms;
        
        try (Connection connection = this.connect();
             Statement stmt = connection.createStatement();) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * Queries the weather table where Time equals ms then returns a String.
     * @param ms Time in milliseconds.
     * @return String of the object at ms.
     */
    public String select(Long ms) {
        String sql = "SELECT * " + "FROM weather " + "WHERE Time = " + ms;
        JsonObject builder = null;
        try (Connection connection = this.connect();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            // loop through the result set
            while (rs.next()) {
                builder = Json.createObjectBuilder()
                    .add(TIME, Json.createObjectBuilder()
                        .add("ms", rs.getLong(TIME)))
                    .add(TEMPERATURE, Json.createObjectBuilder()
                        .add("celsius", rs.getDouble(TEMPERATURE)))
                    .add(PRECIPITATION, Json.createObjectBuilder()
                        .add("mm", rs.getDouble(PRECIPITATION)))
                    .add(AIR_PRESSURE, Json.createObjectBuilder()
                        .add("hPa", rs.getDouble(AIR_PRESSURE)))
                    .add(LIGHT, Json.createObjectBuilder()
                        .add("lux", rs.getDouble(LIGHT)))
                    .add(WIND,
                        Json.createObjectBuilder().add("W_speed", rs.getDouble("Wind_Speed"))
                            .add("W_direction", rs.getDouble("Wind_Dir")))
                    .build();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        assert builder != null;
        return builder == null ? "null" : builder.toString();
    }
    
    /**
     * Select all rows in the table and returns a ArrayList with all entries.
     * @return - List of all values
     */
    public ArrayList selectAll(String database) {
        String sql = "SELECT Time, Temperature, Precipitation, Air_pressure, Light, "
            + "Wind_Speed, Wind_dir FROM " + database;
        ArrayList jsonObjectArray = new ArrayList();
        try (Connection connection = this.connect();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            // loop through the result set
            while (rs.next()) {
                JsonObject builder = Json.createObjectBuilder()
                    .add(TIME, Json.createObjectBuilder()
                        .add("ms", rs.getLong(TIME)))
                    .add(TEMPERATURE, Json.createObjectBuilder()
                        .add("celsius", rs.getLong(TEMPERATURE)))
                    .add(PRECIPITATION, Json.createObjectBuilder()
                        .add("mm", rs.getLong(PRECIPITATION)))
                    .add(AIR_PRESSURE, Json.createObjectBuilder()
                        .add("hPa", rs.getLong(AIR_PRESSURE)))
                    .add(LIGHT, Json.createObjectBuilder()
                        .add("lux", rs.getLong(LIGHT)))
                    .add(WIND,
                        Json.createObjectBuilder().add("W_speed", rs.getLong("Wind_Speed"))
                            .add("W_direction", rs.getLong("Wind_Dir")))
                    .build();
                jsonObjectArray.add(builder);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return jsonObjectArray;
    }
}

