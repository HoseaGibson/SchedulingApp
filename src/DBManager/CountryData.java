
package DBManager;

import static DBManager.ConnectorData.DB_CONN;
import Model.Country;
import static ViewController.LoginScreenController.Loggers;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *  Country class that will retrieve and insert data in to mysql database
 */
public class CountryData {
    
    public static ObservableList<Country> getAllCountries() {
        ObservableList<Country> allCountries = FXCollections.observableArrayList();
        String sqlCountry = "SELECT * FROM country";
        
        try {
            PreparedStatement stmt = DB_CONN.prepareStatement(sqlCountry);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Country country = new Country();
                country.setCountryId(rs.getInt("countryId"));
                country.setCountry(rs.getString("country"));
                allCountries.add(country);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return allCountries;
    }
    
    public static Country getCountryById(int countryId) {
        String sqlCountryID = "SELECT * FROM country WHERE countryId = ?";
        Country getCountryById = new Country();
        
        try {
            PreparedStatement stmt = DB_CONN.prepareStatement(sqlCountryID);
            stmt.setInt(1, countryId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                getCountryById.setCountryId(rs.getInt("countryId"));
                getCountryById.setCountry(rs.getString("country"));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return getCountryById;
    }
    private static int getMaxCountryId() {
        int maxCID = 0;
        String sqlMAx = "SELECT MAX(countryId) FROM country";
        
        try {
            Statement stmt = DB_CONN.createStatement();
            ResultSet rs = stmt.executeQuery(sqlMAx);
            
            if (rs.next()) {
                maxCID = rs.getInt(1);
            }
        }
        catch (SQLException e) {
        }
        return maxCID + 1;
    }
    
    public static int addCcountry(Country country) {
        String sqlAdd = String.join(" ",
                "INSERT INTO country (countryId, country, createDate, createdBy, lastUpdate, lastUpdateBy)",
                "VALUES (?, ?, NOW(), ?, NOW(), ?)");
        
        int countryId = getMaxCountryId();
        try {
            PreparedStatement stmt = DB_CONN.prepareStatement(sqlAdd);
            stmt.setInt(1, countryId);
            stmt.setString(2, country.getCountry());
            stmt.setString(3, Loggers.getUserName());
            stmt.setString(4, Loggers.getUserName());
            stmt.executeUpdate();
        }
        catch (SQLException e) {
        }
        return countryId;
    }
    public static void updateCountry(Country country) {
        String sqlCUpdate = String.join(" ",
                "UPDATE country",
                "SET country=?, lastUpdate=NOW(), lastUpdateBy=?",
                "WHERE countryId=?");
        
        try {
            PreparedStatement stmt = DB_CONN.prepareStatement(sqlCUpdate);
            stmt.setString(1, country.getCountry());
            stmt.setString(2, Loggers.getUserName());
            stmt.setInt(3, country.getCountryId());
            stmt.executeUpdate();
        }
        catch (SQLException e) {
        }
    }
    public void deleteCountry(Country country) {
        String sqlDeleteC = "DELETE FROM country WHERE countryId = ?";
        
        try {
            PreparedStatement stmt = DB_CONN.prepareStatement(sqlDeleteC);
            stmt.setInt(1, country.getCountryId());
            stmt.executeUpdate();
        }
        catch (SQLException e) {
        }
    }
}
