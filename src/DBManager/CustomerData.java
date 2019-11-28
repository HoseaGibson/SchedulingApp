package DBManager;

import static DBManager.ConnectorData.DB_CONN;
import Model.Customer;
import static ViewController.LoginScreenController.Loggers;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *  Customer class that will retrieve and insert data into mysql database
 */
public class CustomerData {
    
    /**
     * This method creates an ObservableList and populates it with 
     * all currently active Customer records in the MySQL database.
     * @return activeCustomers
     */
    public static ObservableList<Customer> getActiveCustomers() {
        ObservableList<Customer> activeCustomers = FXCollections.observableArrayList();
        String getActiveCustomersSQL = "SELECT * FROM customer WHERE active = 1";
        
        try {
            PreparedStatement stmt = DB_CONN.prepareStatement(getActiveCustomersSQL);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Customer activeCustomer = new Customer();
                activeCustomer.setCustomerId(rs.getInt("customerId"));
                activeCustomer.setCustomerName(rs.getString("customerName"));
                activeCustomer.setAddressId(rs.getInt("addressId"));
                activeCustomer.setActive(rs.getBoolean("active"));
                activeCustomers.add(activeCustomer);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return activeCustomers;
    }
    
    public static ObservableList<Customer> getAllCustomers() {
        ObservableList<Customer> allCusts = FXCollections.observableArrayList();
        String sqlAllCust = "SELECT * FROM customer";
        
        try {
            PreparedStatement stmt = DB_CONN.prepareStatement(sqlAllCust);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Customer cust = new Customer();
                cust.setCustomerId(rs.getInt("customerId"));
                cust.setCustomerName(rs.getString("customerName"));
                cust.setAddressId(rs.getInt("addressId"));
                cust.setActive(rs.getBoolean("active"));
                allCusts.add(cust);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return allCusts;
    }
    
    /**
     * This method gets a Customer record from the MySQL database by customerId.
     * @param customerId
     * @return getCustomerQuery
     */
    public static Customer getActiveCustomerById(int customerId) {
        String sqlCustId = "SELECT * FROM customer WHERE customerId = ? AND active=1";
        Customer custQuery = new Customer();
        
        try {
            PreparedStatement stmt = DB_CONN.prepareStatement(sqlCustId);
            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                custQuery.setCustomerId(rs.getInt("customerId"));
                custQuery.setCustomerName(rs.getString("customerName"));
                custQuery.setAddressId(rs.getInt("addressId"));
                custQuery.setActive(rs.getBoolean("active"));
                custQuery.setLastUpdate(rs.getTimestamp("lastUpdate"));
                custQuery.setLastUpdateBy(rs.getString("lastUpdateBy"));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return custQuery;
    }
    
    private static int getMaxCustomerId() {
        int custMax = 0;
        String sqlMaxID = "SELECT MAX(customerId) FROM customer";
        
        try {
            Statement stmt = DB_CONN.createStatement();
            ResultSet rs = stmt.executeQuery(sqlMaxID);
            
            if (rs.next()) {
                custMax = rs.getInt(1);
            }
        }
        catch (SQLException e) {
        }
        return custMax + 1;
    }
    
    /**
     * This method adds a new Customer to the MySQL database.
     * @param customer
     * @return customer
     */
    public static Customer addCustomer(Customer customer) {
        String sqlAddCust = String.join(" ", 
                "INSERT INTO customer (customerId, customerName, addressId, active, createDate, createdBy, lastUpdate, lastUpdateBy)",
                "VALUES (?, ?, ?, 1, NOW(), ?, NOW(), ?)");
        
        int customerId = getMaxCustomerId();
        try {
            PreparedStatement stmt = DB_CONN.prepareStatement(sqlAddCust);
            stmt.setInt(1, customerId);
            stmt.setString(2, customer.getCustomerName());
            stmt.setInt(3, customer.getAddressId());
            stmt.setString(4, Loggers.getUserName());
            stmt.setString(5, Loggers.getUserName());
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }
    
    /**
     * This method updates an existing Customer record in the MySQL database.
     * @param customer
     */
    public static void updateCustomer(Customer customer) {
        String sqlUpdatesCust = String.join(" ", 
                "UPDATE U05lgp.customer",
                "SET customerName=?, addressId=?, lastUpdate=NOW(), lastUpdateBy=?",
                "WHERE customerId = ?");
        
        try {
            PreparedStatement stmt = DB_CONN.prepareStatement(sqlUpdatesCust);
            stmt.setString(1, customer.getCustomerName());
            stmt.setInt(2, customer.getAddressId());
            stmt.setString(3, Loggers.getUserName());
            stmt.setInt(4, customer.getCustomerId());
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * This method soft deletes an existing Customer from the MySQL database
     * by setting the active property to 0.
     * @param customer
     */
    public static void deleteCustomer(Customer customer) {
        String sqlDeleteCust = "UPDATE customer SET active=0 WHERE customerId = ?";
        
        try {
            PreparedStatement stmt = DB_CONN.prepareStatement(sqlDeleteCust);
            stmt.setInt(1, customer.getCustomerId());
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private static Customer currCustomer = new Customer();
    
    public static Customer getCurrentCustomer() {
        return currCustomer;
    }
    
    public static void loadCustomer(int customerID) throws SQLException {
            try {
            String query = "SELECT c.* "
                    + "FROM customer c "
                    + " WHERE c.customerid = " + customerID;
 
            //System.out.println(query);
            Statement st = DB_CONN.createStatement();
            ResultSet rs = st.executeQuery(query);
 
            // build current customer
            while (rs.next()) {
                
               currCustomer.setCustomerId(rs.getInt("customerId"));
               currCustomer.setAddressId(rs.getInt("addressId"));
               if (rs.getInt("active") == 1) {
                currCustomer.setActive(Boolean.TRUE);
               } else { 
                currCustomer.setActive(Boolean.FALSE);
               }
               currCustomer.setCustomerName(rs.getString("customerName"));
                currCustomer.setCustomerName(rs.getString("customerName"));
                currCustomer.setAddressId(rs.getInt("addressId"));
                currCustomer.setActive(rs.getBoolean("active"));
                currCustomer.setLastUpdate(rs.getTimestamp("lastUpdate"));
                currCustomer.setLastUpdateBy(rs.getString("lastUpdateBy"));
              
             
            }
    
            st.close();
            
                    
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }
            
    }
  
}