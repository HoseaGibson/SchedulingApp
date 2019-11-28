/**
 * City class that will retrieve and insert data into mysql database
 */
package DBManager;

import static DBManager.ConnectorData.DB_CONN;
import Model.City;
import static ViewController.LoginScreenController.Loggers;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class CityData {
    
    public static int getCityId(String city) {
        String sqlCity = "SELECT cityId FROM city WHERE city = ?";
        int cityId = 0;
        
        try {
            PreparedStatement stmt = DB_CONN.prepareStatement(sqlCity);
            stmt.setString(1, city);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                cityId = rs.getInt("cityId");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return cityId;
    }
    
    public static City getCityById(int cityId) {
        String sqlCityId = "SELECT * FROM city WHERE cityId = ?";
        City getCityById = new City();
        
        try {
            PreparedStatement stmt = DB_CONN.prepareStatement(sqlCityId);
            stmt.setInt(1, cityId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                getCityById.setCityId(rs.getInt("cityId"));
                getCityById.setCity(rs.getString("city"));
                getCityById.setCountryId(rs.getInt("countryId"));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return getCityById;
    }
    
    private static int getMaxCityId() {
        int maxID = 0;
        String sqlMaxID = "SELECT MAX(cityId) FROM city";
        
        try {
            Statement stmt = DB_CONN.createStatement();
            ResultSet rs = stmt.executeQuery(sqlMaxID);
            
            if (rs.next()) {
                maxID = rs.getInt(1);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return maxID + 1;
    }

    public static City addCity(City city) {
        String sqlAddCity = String.join(" ",
                "INSERT INTO city (cityId, city, countryId, createDate, createdBy, lastUpdate, lastUpdateBy)",
                "VALUES (?, ?, ?, NOW(), ?, NOW(), ?)");
        
        int cityId = getMaxCityId();
        try {
            PreparedStatement stmt = DB_CONN.prepareStatement(sqlAddCity);
            stmt.setInt(1, cityId);
            stmt.setString(2, city.getCity());
            stmt.setInt(3, city.getCountryId());
            stmt.setString(4, Loggers.getUserName());
            stmt.setString(5, Loggers.getUserName());
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return city;
    }
    public static void updateCity(City city) {
        String sqlUpdated = String.join(" ",
                "UPDATE city",
                "SET city=?, countryId=?, lastUpdate=NOW(), lastUpdateBy=?",
                "WHERE cityId=?");
        
        try {
            PreparedStatement stmt = DB_CONN.prepareStatement(sqlUpdated);
            stmt.setString(1, city.getCity());
            stmt.setInt(2, city.getCountryId());
            stmt.setString(3, Loggers.getUserName());
            stmt.setInt(4, city.getCityId());
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteCity(City city) {
        String sqlDelete = "DELETE FROM city WHERE cityId = ?";
        
        try {
            PreparedStatement stmt = DB_CONN.prepareStatement(sqlDelete);
            stmt.setInt(1, city.getCityId());
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
