package no.ntnu.idata2304.group8.databasehandler;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;




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
     * Inserts data into the Database
     * TODO: CHANGE TO PROPER DATABASE AND CHANGE QUERY ACCORDINGLY
     */

    public void insert(int Time, float Temprature, float Precipitation, int Air_pressure, float Light, float Wind_Speed, float Wind_dir) {
        String sql = "INSERT INTO Weather(Time, Temprature, Precipitation, Air_pressure, Light, Wind_Speed, Wind_dir) VALUES(?,?,?,?,?,?,?)";
        try (Connection connection = this.connect();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, Time);
            pstmt.setFloat(2, Temprature);
            pstmt.setFloat(3,Precipitation);
            pstmt.setInt(4,Air_pressure);
            pstmt.setFloat(5,Light);
            pstmt.setFloat(6,Wind_Speed);
            pstmt.setFloat(7,Wind_dir);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


        /**
         * select all rows in the table and returns a ArrayList with all entries
         * @Return - List of all values
         * TODO: CHANGE TO PROPER DATABASE AND CHANGE QUERY ACCORDINGLY
         * TODO: CHANGE RETURN TYPE TO WHATEVER WE DECIDE IS NEEDED
         */

        public List selectAll(){
            String sql = "SELECT Time, Temprature, Precipitation, Air_pressure, Light, Wind_Speed, Wind_dir FROM Weather";

            ArrayList array = new ArrayList<>();
            try (Connection connection = this.connect();
                 Statement stmt  = connection.createStatement();
                 ResultSet rs    = stmt.executeQuery(sql)){

                // loop through the result set
                while (rs.next()) {
                    array.add(rs.getLong("Time"));
                    array.add(rs.getDouble("Temprature"));
                    array.add(rs.getDouble("Precipitation"));
                    array.add(rs.getDouble("Air_pressure"));
                    array.add(rs.getDouble("Light"));
                    array.add(rs.getDouble("Wind_Speed"));
                    array.add((rs.getDouble("Wind_dir")));
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            return array;
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
     * Queries the database to find Temprature at a certain time
     * @param Time - Time
     * @return List - of Temprature
     */

    public List getTemprature(int Time){
        String sql = "SELECT Temprature FROM Weather WHERE Time = ?;";

        ArrayList array = new ArrayList<>();

        try (Connection connection = this.connect();
             PreparedStatement pstmt  = connection.prepareStatement(sql)) {
                pstmt.setInt(1,Time);
                ResultSet rs  = pstmt.executeQuery();

            // loop through the result set
            while (rs.next()) {
                array.add(rs.getFloat("Temprature"));

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return array;
    }
    /**
     * Queries the database to find Precipitation at a certain time
     * @param Time - Time
     * @return List - of Precipitation
     */

    public List getPrecipitation(int Time){
        String sql = "SELECT Precipitation FROM Weather WHERE Time = ?;";

        ArrayList array = new ArrayList<>();

        try (Connection connection = this.connect();
             PreparedStatement pstmt  = connection.prepareStatement(sql)) {
            pstmt.setInt(1,Time);
            ResultSet rs  = pstmt.executeQuery();

            // loop through the result set
            while (rs.next()) {
                array.add(rs.getFloat("Precipitation"));

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return array;
    }

    /**
     * Queries the database to find Air_pressure at a certain time
     * @param Time - Time
     * @return List - of Air_pressure
     */


    public List getAir_pressure(int Time){
        String sql = "SELECT Air_pressure FROM Weather WHERE Time = ?;";

        ArrayList array = new ArrayList<>();

        try (Connection connection = this.connect();
             PreparedStatement pstmt  = connection.prepareStatement(sql)) {
            pstmt.setInt(1,Time);
            ResultSet rs  = pstmt.executeQuery();

            // loop through the result set
            while (rs.next()) {
                array.add(rs.getInt("Air_pressure"));

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return array;
    }

    /**
     * Queries the database to find Wind_Speed at a certain time
     * @param Time - Time
     * @return List - of Wind_Speed
     */

    public List getWind_Speed(int Time){
        String sql = "SELECT Wind_Speed FROM Weather WHERE Time = ?;";

        ArrayList array = new ArrayList<>();

        try (Connection connection = this.connect();
             PreparedStatement pstmt  = connection.prepareStatement(sql)) {
            pstmt.setInt(1,Time);
            ResultSet rs  = pstmt.executeQuery();

            // loop through the result set
            while (rs.next()) {
                array.add(rs.getFloat("Wind_Speed"));

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return array;
    }
    /**
     * Queries the database to find Light at a certain time
     * @param Time - Time
     * @return List - of Light
     */

    public List getLight(int Time){
        String sql = "SELECT Light FROM Weather WHERE Time = ?;";

        ArrayList array = new ArrayList<>();

        try (Connection connection = this.connect();
             PreparedStatement pstmt  = connection.prepareStatement(sql)) {
            pstmt.setInt(1,Time);
            ResultSet rs  = pstmt.executeQuery();

            // loop through the result set
            while (rs.next()) {
                array.add(rs.getFloat("Light"));

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return array;
    }

    /**
     * Queries the database to find Wind_dir at a certain time
     * @param Time - Time
     * @return List - of Wind_dir
     */

    public List getWind_dir(int Time){
        String sql = "SELECT Wind_dir FROM Weather WHERE Time = ?;";

        ArrayList array = new ArrayList<>();

        try (Connection connection = this.connect();
             PreparedStatement pstmt  = connection.prepareStatement(sql)) {
            pstmt.setInt(1,Time);
            ResultSet rs  = pstmt.executeQuery();

            // loop through the result set
            while (rs.next()) {
                array.add(rs.getFloat("Wind_dir"));

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
            List s = app.selectAll();
            for(int i=0; i< s.size();i++){
                if(i%7 == 0){
                    System.out.println("");
                }
                System.out.println(s.get(i));
        }
        }
}

