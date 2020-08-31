package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
import model.Customer;
import dao.dbConnection.*;

import static dao.dbConnection.stmt;

public class dbCustomer {

    // Variables for use in storing customer data (the data structures)

    public static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
    public static ObservableList<String> allCities = FXCollections.observableArrayList();

    public static int addressCount;
    public static int addressId;

    // CLASS FUNCTIONS AND METHODS:

    // Methods used for retrieving customer data
    // Returns a single customer
    public static Customer getCustomer(int id) {
        try {
            String getCustQuery = "SELECT * FROM customer WHERE customerId='" + id + "'";
            ResultSet results = stmt.executeQuery(getCustQuery);
            if(results.next()) {
                Customer customer = new Customer();
                customer.setcName(results.getString("customerName"));
                return customer;
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
        return null;
    }

    // Returns all Customers in Database
    public static ObservableList<Customer> getAllCustomers() {
        try {
            String allCustQuery = "SELECT customer.customerId, customer.customerName, address.address, address.phone, address.postalCode, city.city, city.cityId"
                    + " FROM customer INNER JOIN address ON customer.addressId = address.addressId "
                    + "INNER JOIN city ON address.cityId = city.cityId";
            ResultSet results = stmt.executeQuery(allCustQuery);
            while(results.next()) {
                Customer customer = new Customer(
                        results.getInt("customerId"),
                        results.getString("customerName"),
                        results.getString("address"),
                        results.getString("city"),
                        results.getInt("cityID"),
                        results.getString("postalCode"),
                        results.getString("phone"));
                allCustomers.add(customer);
            }
            System.out.println("You have " + allCustomers.size() + " customers returned.");
            return allCustomers;
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return null;
        }
    }



    // Create new customer record
    public static boolean saveCustomer(String name, String address, int cityId, String zip, String phone) {
        try {
            // This is your test string to check to see if the city id exists in the address database already.
            ResultSet resultSet = stmt.executeQuery("SELECT COUNT(*) AS total FROM address");
            while(resultSet.next()){
                addressCount = resultSet.getInt("total");
            }
            String queryOne = "INSERT INTO address SET address='" + address + "',address2='', phone='" + phone + "', createDate=NOW(), "
                    + "createdBy=' ', lastUpdate=NOW(), lastUpdateBy=' ', postalCode='" + zip + "', cityId=" + cityId;
            int updateOne = stmt.executeUpdate(queryOne);
            if(updateOne == 1) {
                int addressId = addressCount + 1;
                String queryTwo = "INSERT INTO customer SET customerName='" + name + "', addressId=" + addressId + ", active= 1, "
                        + "createDate=NOW(), createdBy=' ', lastUpdate=NOW(), lastUpdateBy=' '";
                int updateTwo = stmt.executeUpdate(queryTwo);
                if(updateTwo == 1) {
                    return true;
                }
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
        return false;
    }




    // Updates customer record
    public static boolean updateCustomer(int id, String name, String address, int cityId, String zip, String phone) {
        try {
            ResultSet rs1 = stmt.executeQuery("SELECT addressId FROM customer where customerId='" + id + "'");
            while(rs1.next()){
                addressId = rs1.getInt("addressId");
            }
            String queryOne = "UPDATE address SET address='" + address + "', cityId=" + cityId + ", postalCode='" + zip + "', phone='" + phone + "' "
                    + "WHERE addressId=" + id;
            int updateOne = stmt.executeUpdate(queryOne);
            if(updateOne == 1) {
                String queryTwo = "UPDATE customer SET customerName='" + name + "', addressId=" + id + " WHERE customerId=" + id;
                int updateTwo = stmt.executeUpdate(queryTwo);
                if(updateTwo == 1) {
                    return true;
                }
            }
        } catch(SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
        return false;
    }



    // Delete customer record
    public static boolean deleteCustomer(int id) {
        try {
            String queryOne = "DELETE FROM customer WHERE customerId=" + id;
            int updateOne = stmt.executeUpdate(queryOne);
            if(updateOne == 1) {
                String queryTwo = "DELETE FROM address WHERE addressId=" + id;
                int updateTwo = stmt.executeUpdate(queryTwo);
                if(updateTwo == 1) {
                    return true;
                }
            }
        } catch(SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
        return false;
    }



    // Return list of cities
    public static ObservableList<String> getAllCities(){
        String allCityQuery = "SELECT city FROM city";
        try{
            ResultSet rs = stmt.executeQuery(allCityQuery);
            while (rs.next()){
                allCities.add(rs.getString("city"));
            }
            System.out.println("You have " + allCities.size() + " cities returned.");
            return allCities;
        }catch (SQLException e){
            System.out.println("SQLException: " + e.getMessage());
            return null;
        }
    }

}
