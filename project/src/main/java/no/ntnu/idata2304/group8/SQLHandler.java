package no.ntnu.idata2304.group8;

import java.sql.*;

/**
     *
     * @author sqlitetutorial.net
     */
    public class SQLHandler {
    private Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:C:/Users/Kim Andre/OneDrive - NTNU/gitHub/Group8NetworkProject/project/src/main/test.db";

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public void insert(int Rid, String name) {
        String sql = "INSERT INTO Reviewer(Rid, name) VALUES(?,?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, Rid);
            pstmt.setString(2, name);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }



        /**
         * @param args the command line arguments
         */
        public static void main(String[] args) throws SQLException {
        SQLHandler app = new SQLHandler();
        app.insert(123, "Kim Andre");
        }
    }

