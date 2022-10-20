package no.ntnu.idata2304.group8.databasehandler;

import java.sql.*;

    public class SQLHandler {
        /**
         * Connects to the SQL database
         * @return connection
         */

    private Connection connect() {
        // Location of SQLlite database
        String url = "jdbc:sqlite:C:/Users/Kim Andre/OneDrive - NTNU/gitHub/Group8NetworkProject/project/src/main/test.db";
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return connection;
    }

    /**
     * Inserts data into the Database
     * TODO: CHANGE TO PROPER DATABASE AND CHANGE QUERY
     * @param Rid
     * @param name
     */

    public void insert(int Rid, String name) {
        String sql = "INSERT INTO Reviewer(Rid, name) VALUES(?,?)";
        try (Connection connection = this.connect();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, Rid);
            pstmt.setString(2, name);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


        /**
         * select all rows in the table and prints it
         * TODO: CHANGE TO PROPER DATABASE AND CHANGE QUERY
         * TODO: CHANGE SO METHOD RETURNS INFO
         */

        public void selectAll(){
            String sql = "SELECT rid, name FROM Reviewer";

            try (Connection connection = this.connect();
                 Statement stmt  = connection.createStatement();
                 ResultSet rs    = stmt.executeQuery(sql)){

                // loop through the result set
                while (rs.next()) {
                    System.out.println(rs.getInt("rid") +  "\t" +
                        rs.getString("name"));
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }



        /**
         * @param args the command line arguments
         */

        //TESTING

        public static void main(String[] args) {
        SQLHandler app = new SQLHandler();
        app.insert(123, "Kim Andre");
        app.selectAll();
        }
    }

