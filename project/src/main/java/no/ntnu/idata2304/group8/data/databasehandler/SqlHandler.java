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
 * Writes to and reads from the SQL database.
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
        // String fileName = "project/src/main/resources/database/test.db";
//        String fileName = "D:\\NTNU\\Semester 3\\IDATA2304 Computer networks and "
//            + "network programming\\Group8NetworkProject\\project\\src\\main\\"
//            + "resources\\database\\test.db";
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
     *
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
    // TODO: CHANGE TO PROPER DATABASE AND CHANGE QUERY ACCORDINGLY
    // TODO: Define ArrayList data type. I.e. ArrayList<String> or something.
    public ArrayList selectAllBetween(Long dayStart, Long dayEnd, String database) {
        String sql = "SELECT Time, Temperature, Precipitation, Air_pressure, Light, "
            + "Wind_Speed, Wind_dir " + "FROM " + database
            + " WHERE Time BETWEEN " + dayStart + " AND " + dayEnd;
        ArrayList jArray = new ArrayList(); // TODO: Refactor variable name
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
                jArray.add(builder);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return jArray;
    }
    
    /**
     * TODO: Document this function
     *
     * @param dayStart
     * @param dayEnd
     * @param database
     * @return
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
     * TODO: Fill in JavaDoc
     *
     * @param dayStart
     * @param dayEnd
     * @param database
     * @return
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
     * TODO: Fill in JavaDoc.
     *
     * @return
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
     * TODO: Fill in JavaDoc.
     *
     * @param ms
     * @return
     */
    public List<String> selectLastTen(Long ms) {
        String sql = "SELECT * " + "FROM weather "
            + "ORDER BY Time DESC LIMIT 10";
        ArrayList<String> jArray = new ArrayList(); // TODO: Refactor variable to match checkstyle
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
                jArray.add(builder.toString());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return jArray;
    }
    
    /**
     * TODO: Fill in JavaDoc.
     *
     * @param ms
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
     * TODO: Fill in JavaDoc.
     *
     * @param ms
     * @return
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
     *
     * @return - List of all values
     */
    // TODO: CHANGE TO PROPER DATABASE AND CHANGE QUERY ACCORDINGLY
    public ArrayList selectAll(String database) {
        String sql = "SELECT Time, Temperature, Precipitation, Air_pressure, Light, "
            + "Wind_Speed, Wind_dir FROM " + database;
        ArrayList jArray = new ArrayList(); // TODO: Refactor variable name to work with checkstyle
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
                jArray.add(builder);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return jArray;
    }
}

