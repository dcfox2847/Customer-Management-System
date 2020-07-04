package dao;

import java.sql.*;

public class dbConnection {

    // All code to make the base connection to the database will go here.

    // Finalized and private variables needed for making connection to DB

    private static final String DB_NAME = "U05mLM";
    private static final String DB_URL = "3.227.166.251";
    private static final String USERNAME = "U05mLM";
    private static final String PASSWORD = "53688547362";
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String CONNECTION_ADDR = "jdbc:mysql://3.227.166.251/U05mLM";
    static Connection conn = null;
    static Statement stmt = null;
    static ResultSet rs = null;


    // The constructors for the class

    public dbConnection() {};

    // Methods and Functions of the class

    public static void makeConnection() throws SQLException {
        try{
            // Register the Driver
            Class.forName(DRIVER);

            //Open the connection
            System.out.println("Connection to database...");
            conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            //Execute a query
            System.out.println("Creating a statement....");
            stmt = conn.createStatement();
            String testStatement = "Select * from Customer";
            rs = stmt.executeQuery(testStatement);

            // Extract the data from the result set
            while(rs.next()){
                String name = rs.getString("customerName");
                int address = rs.getInt("addressID");
                System.out.println("Customer name:" + name);
                System.out.println("Address ID: " + address);
            }

        } catch (SQLException se){
            se.printStackTrace();
            if(stmt != null){
                stmt.close();
            }
        } catch (Exception exc){
            exc.printStackTrace();
            if(conn != null){
                conn.close();
            }
        }
    }

    public static void closeConnection() throws SQLException {
        try{
            rs.close();
            stmt.close();
            conn.close();
        }catch (SQLException se2){
            se2.printStackTrace();
        }
        System.out.println("The connection has been closed.");

    }


}
