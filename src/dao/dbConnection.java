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
    static java.sql.Connection conn = null;
    static Statement stmt = null;
    static ResultSet rs = null;


    // The constructors for the class
    public dbConnection() {};

    // Methods and Functions of the class

    // function to make a connection to the database
    public static void makeConnection() throws SQLException {
        try{
            System.out.println("Connection to database...");
            conn = DriverManager.getConnection(CONNECTION_ADDR, USERNAME, PASSWORD);
            stmt = conn.createStatement();
            System.out.println("Connection complete....");
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

    // function to close the connection to the database
    public static void closeConnection() throws SQLException {
        try{
            conn.close();
        }catch (SQLException se2){
            se2.printStackTrace();
        }
        System.out.println("The connection has been closed.");

    }
}
