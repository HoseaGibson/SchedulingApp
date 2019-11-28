package DBManager;

import Model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.Logger;

/**
 *  User class that will retrieve and insert data into mysql 
 */
public class UserData {
    
    private final static Connection DB_CONN = ConnectorData.DB_CONN;
    private static User currentUser;
    public static User getCurrentUser(){return currentUser;}
    
    public UserData () {
    }
    
    public static ObservableList<User> getActiveUsers() {
        ObservableList<User> activeUsers = FXCollections.observableArrayList();
        String getActiveUsers = "SELECT * FROM user WHERE active = 1";
        
        try {
            PreparedStatement stmt = DB_CONN.prepareStatement(getActiveUsers);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                User activeUser = new User();
                activeUser.setUserId(rs.getInt("userId"));
                activeUser.setUserName(rs.getString("userName"));
                activeUser.setPassword(rs.getString("password"));
                activeUser.setActive(rs.getBoolean("active"));
                
                activeUsers.add(activeUser);
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return activeUsers;
    }
    
    public static User getUserById(int userId) {
        String sqlID = "SELECT * FROM user WHERE userId=?";
        User user = new User();
        
        try {
            PreparedStatement stmt = DB_CONN.prepareStatement(sqlID);
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                user.setUserId(rs.getInt("userId"));
                user.setUserName(rs.getString("userName"));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
    
    public static Boolean login(String username, String password) {
        try {
            Statement statement = ConnectorData.DB_CONN.createStatement();
            String query = "SELECT * FROM user WHERE userName='" + username + "' AND password='" + password + "'";
            ResultSet results = statement.executeQuery(query);
            if(results.next()) {
                currentUser = new User();
                currentUser.setUserName(results.getString("userName"));
                statement.close();
                Logger.log(username, true);
                return true;
            } else {
                Logger.log(username, false);
                return false;
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return false;
        }
    }
    
}
