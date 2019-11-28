/**
 * Appointment class that will get retrieve and insert data into mysql database
 */
package DBManager;

import static DBManager.ConnectorData.DB_CONN;
import Model.Appointment;
import Model.Customer;
import static ViewController.LoginScreenController.Loggers;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class AppointmentData {
    
     
    private static final ZoneId zId = ZoneId.systemDefault();
    private static Appointment currentAppointment = new Appointment();
    
    
    public static Appointment getCurrentAppointment() {
        return currentAppointment;
    }
      
    public static ObservableList<Appointment> getApptsByWeek() {
        ObservableList<Appointment> apptsByWeek = FXCollections.observableArrayList();
        String sqlWeek = "SELECT customer.*, appointment.* FROM customer "
                + "RIGHT JOIN appointment ON customer.customerId = appointment.customerId "
                + "WHERE start BETWEEN NOW() AND (SELECT ADDDATE(NOW(), INTERVAL 7 DAY))";
        
        try {
            PreparedStatement stmt = DB_CONN.prepareStatement(sqlWeek);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Customer selectedCust = CustomerData.getActiveCustomerById(rs.getInt("customerId"));
                Appointment getWeeklyAppts = new Appointment();
                getWeeklyAppts.setCustomer(selectedCust);
                getWeeklyAppts.setAppointmentId(rs.getInt("appointmentId"));
                getWeeklyAppts.setCustomerId(rs.getInt("customerId"));
                getWeeklyAppts.setUserId(rs.getInt("userId"));
                getWeeklyAppts.setTitle(rs.getString("title"));
                getWeeklyAppts.setDescription(rs.getString("description"));
                getWeeklyAppts.setLocation(rs.getString("location"));
                getWeeklyAppts.setContact(rs.getString("contact"));
                getWeeklyAppts.setType(rs.getString("type"));
                getWeeklyAppts.setUrl(rs.getString("url"));
                
                LocalDateTime startUTC = rs.getTimestamp("start").toLocalDateTime();
                LocalDateTime endUTC = rs.getTimestamp("end").toLocalDateTime();
                ZonedDateTime startLocal = ZonedDateTime.ofInstant(startUTC.toInstant(ZoneOffset.UTC), zId);
                ZonedDateTime endLocal = ZonedDateTime.ofInstant(endUTC.toInstant(ZoneOffset.UTC), zId);
                
                getWeeklyAppts.setStart(startLocal);
                getWeeklyAppts.setEnd(endLocal);
                apptsByWeek.add(getWeeklyAppts);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return apptsByWeek;
    }
    public static ObservableList<Appointment> getApptsByMonth() {
        ObservableList<Appointment> apptsByMonth = FXCollections.observableArrayList();
        String sqlMonth = "SELECT customer.*, appointment.* FROM customer "
                + "RIGHT JOIN appointment ON customer.customerId = appointment.customerId "
                + "WHERE start BETWEEN NOW() AND (SELECT LAST_DAY(NOW()))";
        
        try {
            PreparedStatement stmt = DB_CONN.prepareStatement(sqlMonth);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Customer selectedCust = CustomerData.getActiveCustomerById(rs.getInt("customerId"));
                Appointment getMonthlyAppts = new Appointment();
                getMonthlyAppts.setCustomer(selectedCust);
                getMonthlyAppts.setAppointmentId(rs.getInt("appointmentId"));
                getMonthlyAppts.setCustomerId(rs.getInt("customerId"));
                getMonthlyAppts.setUserId(rs.getInt("userId"));
                getMonthlyAppts.setTitle(rs.getString("title"));
                getMonthlyAppts.setDescription(rs.getString("description"));
                getMonthlyAppts.setLocation(rs.getString("location"));
                getMonthlyAppts.setContact(rs.getString("contact"));
                getMonthlyAppts.setType(rs.getString("type"));
                getMonthlyAppts.setUrl(rs.getString("url"));
                
                LocalDateTime startUTC = rs.getTimestamp("start").toLocalDateTime();
                LocalDateTime endUTC = rs.getTimestamp("end").toLocalDateTime();
                ZonedDateTime startLocal = ZonedDateTime.ofInstant(startUTC.toInstant(ZoneOffset.UTC), zId);
                ZonedDateTime endLocal = ZonedDateTime.ofInstant(endUTC.toInstant(ZoneOffset.UTC), zId);
                
                getMonthlyAppts.setStart(startLocal);
                getMonthlyAppts.setEnd(endLocal);
                apptsByMonth.add(getMonthlyAppts);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return apptsByMonth;
    }
    
    public static ObservableList<Appointment> getApptsByUser() {
        ObservableList<Appointment> apptsByUser = FXCollections.observableArrayList();
        String sqlUserAppt = "SELECT user.userId, customer.customerId, appointment.start FROM user JOIN appointment ON user.userId = appointment.userId JOIN customer ON appointment.customerId = customer.customerId";
        
        try {
            PreparedStatement stmt = DB_CONN.prepareStatement(sqlUserAppt);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Appointment appt = new Appointment();
                appt.setUserId(rs.getInt("userId"));
                appt.setCustomerId(rs.getInt("customerId"));
                LocalDateTime startUTC = rs.getTimestamp("start").toLocalDateTime();
                ZonedDateTime startLocal = ZonedDateTime.ofInstant(startUTC.toInstant(ZoneOffset.UTC), zId);
                appt.setStart(startLocal);
                apptsByUser.add(appt);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return apptsByUser;
    }
    
    public Appointment getApptById(int appointmentId) {
        String sqlApptID = "SELECT customer.customerId, customer.customerName, appointment.* FROM customer "
                + "RIGHT JOIN appointment ON customer.customerId = appointment.customerId " 
                + "WHERE appointmentId = ?";
        Appointment getApptById = new Appointment();
        try {
            PreparedStatement stmt = DB_CONN.prepareStatement(sqlApptID);
            stmt.setInt(1, appointmentId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Customer selectedCust = new Customer();
                selectedCust.setCustomerId(rs.getInt("customerId"));
                selectedCust.setCustomerName(rs.getString("customerName"));
                getApptById.setCustomer(selectedCust);
                getApptById.setCustomerId(rs.getInt("customerId"));
                getApptById.setUserId(rs.getInt("userId"));
                getApptById.setTitle(rs.getString("title"));
                getApptById.setDescription(rs.getString("description"));
                getApptById.setLocation(rs.getString("location"));
                getApptById.setContact(rs.getString("contact"));
                getApptById.setType(rs.getString("type"));
                getApptById.setUrl(rs.getString("url"));
                
                LocalDateTime startUTC = rs.getTimestamp("start").toLocalDateTime();
                LocalDateTime endUTC = rs.getTimestamp("end").toLocalDateTime();
                ZonedDateTime startLocal = ZonedDateTime.ofInstant(startUTC.toInstant(ZoneOffset.UTC), zId);
                ZonedDateTime endLocal = ZonedDateTime.ofInstant(endUTC.toInstant(ZoneOffset.UTC), zId);
                
                getApptById.setStart(startLocal);
                getApptById.setEnd(endLocal);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return getApptById;
    }
    
    public static Appointment getUpcomingAppt() {
       
        
        Appointment upcomingAppt = new Appointment();
        try {
              
            ZonedDateTime localZT = ZonedDateTime.now(zId);
            ZonedDateTime zdtUTC = localZT.withZoneSameInstant(ZoneId.of("UTC"));
            LocalDateTime localUTC = zdtUTC.toLocalDateTime();
            String sqlUpcoming = "SELECT customer.customerName, appointment.* FROM appointment "
                + "JOIN customer ON appointment.customerId = customer.customerId "
                + "WHERE (start BETWEEN ? AND ADDTIME(NOW(), '00:15:00'))";
            PreparedStatement stmt = DB_CONN.prepareStatement(sqlUpcoming);
            
            stmt.setTimestamp(1, Timestamp.valueOf(localUTC));
            
            ResultSet rs = stmt.executeQuery();
            
            
       
                while (rs.next()) {
                    Customer cust = new Customer();
                    cust.setCustomerName(rs.getString("customerName"));
                    upcomingAppt.setCustomer(cust);
                    upcomingAppt.setAppointmentId(rs.getInt("appointmentId"));
                    upcomingAppt.setCustomerId(rs.getInt("customerId"));
                    upcomingAppt.setUserId(rs.getInt("userId"));
                    upcomingAppt.setTitle(rs.getString("title"));
                    LocalDateTime startUTC = rs.getTimestamp("start").toLocalDateTime();
                    ZonedDateTime startZDT = ZonedDateTime.ofInstant(startUTC.toInstant(ZoneOffset.UTC), zId);
                    upcomingAppt.setStart(startZDT);
                    return upcomingAppt;
            }
            
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return upcomingAppt;
    }
    
    public static ObservableList<Appointment> getOverlappingAppts(LocalDateTime start, LocalDateTime end) {
       ObservableList<Appointment> getOverlappedAppts = FXCollections.observableArrayList();
        
        try {
            
            String sqlOverlap = "SELECT * FROM appointment " 
                + "WHERE (start >= ? AND end <= ?) "
                + "OR (start <= ? AND end >= ?) "
                + "OR (start BETWEEN ? AND ? OR end BETWEEN ? AND ?)";
                
            LocalDateTime startLDT = start.atZone(zId).withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime();
            LocalDateTime endLDT = end.atZone(zId).withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime();
            PreparedStatement stmt = DB_CONN.prepareStatement(sqlOverlap);
            stmt.setTimestamp(1, Timestamp.valueOf(startLDT));
            stmt.setTimestamp(2, Timestamp.valueOf(endLDT));
            stmt.setTimestamp(3, Timestamp.valueOf(startLDT));
            stmt.setTimestamp(4, Timestamp.valueOf(endLDT));
            stmt.setTimestamp(5, Timestamp.valueOf(startLDT));
            stmt.setTimestamp(6, Timestamp.valueOf(endLDT));
            stmt.setTimestamp(7, Timestamp.valueOf(startLDT));
            stmt.setTimestamp(8, Timestamp.valueOf(endLDT));
            //stmt.setTimestamp(9, Timestamp.valueOf(startLDT));
            //stmt.setTimestamp(10, Timestamp.valueOf(endLDT));
            
            ResultSet rs = stmt.executeQuery();
            
                while (rs.next()) {
                    Appointment overlaps = new Appointment();
                    overlaps.setAppointmentId(rs.getInt("appointmentId"));
                    overlaps.setTitle(rs.getString("title"));
                    overlaps.setDescription(rs.getString("description"));
                    overlaps.setLocation(rs.getString("location"));
                    overlaps.setContact(rs.getString("contact"));
                    overlaps.setType(rs.getString("type"));
                    overlaps.setUrl(rs.getString("url"));
                    LocalDateTime startUTC = rs.getTimestamp("start").toLocalDateTime();
                    LocalDateTime endUTC = rs.getTimestamp("end").toLocalDateTime();
                    ZonedDateTime startLocal = ZonedDateTime.ofInstant(startUTC.toInstant(ZoneOffset.UTC), zId);
                    ZonedDateTime endLocal = ZonedDateTime.ofInstant(endUTC.toInstant(ZoneOffset.UTC), zId);
                    overlaps.setStart(startLocal);
                    overlaps.setEnd(endLocal);
                    getOverlappedAppts.add(overlaps);
                }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return getOverlappedAppts;
    } 
    
    private static int getMaxAppointmentId() {
        int maxID = 0;
        String sqlAppID = "SELECT MAX(appointmentId) FROM appointment";
        
        try {
            Statement stmt = DB_CONN.createStatement();
            ResultSet rs = stmt.executeQuery(sqlAppID);
            
            if (rs.next()) {
                maxID = rs.getInt(1);
            }
        }
        catch (SQLException e) {
        }
        return maxID + 1;
    }

    public static Appointment addAppointment(Appointment appointment) {
        String sqlAddA = String.join(" ",
                "INSERT INTO appointment (appointmentId, customerId, userId, title, "
                            + "description, location, contact, type, url, start, end, "
                            + "createDate, createdBy, lastUpdate, lastUpdateBy) ",
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), ?, NOW(), ?)");
        
        int appointmentId = getMaxAppointmentId();
        try {
            PreparedStatement stmt = DB_CONN.prepareStatement(sqlAddA);
            stmt.setInt(1, appointmentId);
            stmt.setObject(2, appointment.getCustomerId());
            stmt.setObject(3, appointment.getUserId());
            stmt.setObject(4, appointment.getTitle());
            stmt.setObject(5, appointment.getDescription());
            stmt.setObject(6, appointment.getLocation());
            stmt.setObject(7, appointment.getContact());
            stmt.setObject(8, appointment.getType());
            stmt.setObject(9, appointment.getUrl());
            
            ZonedDateTime startZDT = appointment.getStart().withZoneSameInstant(ZoneId.of("UTC"));
            ZonedDateTime endZDT = appointment.getEnd().withZoneSameInstant(ZoneId.of("UTC"));
            stmt.setTimestamp(10, Timestamp.valueOf(startZDT.toLocalDateTime()));
            stmt.setTimestamp(11, Timestamp.valueOf(endZDT.toLocalDateTime()));
            
            stmt.setString(12, Loggers.getUserName());
            stmt.setString(13, Loggers.getUserName());
            stmt.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return appointment;
    }
    public static void updateAppointment(Appointment appointment) {
        String sqlUpdates = String.join(" ",
                "UPDATE appointment",
                "SET customerId=?, userId=?, title=?, description=?, location=?," +
                "contact=?, type=?, url=?, start=?, end=?, lastUpdate=NOW(), lastUpdateBy=?",
                "WHERE appointmentId=?");
        
        try {
            PreparedStatement stmt = DB_CONN.prepareStatement(sqlUpdates);
            stmt.setObject(1, appointment.getCustomerId());
            stmt.setObject(2, appointment.getUserId());
            stmt.setObject(3, appointment.getTitle());
            stmt.setObject(4, appointment.getDescription());
            stmt.setObject(5, appointment.getLocation());
            stmt.setObject(6, appointment.getContact());
            stmt.setObject(7, appointment.getType());
            stmt.setObject(8, appointment.getUrl());
            
            ZonedDateTime startZDT = appointment.getStart().withZoneSameInstant(ZoneId.of("UTC"));
            ZonedDateTime endZDT = appointment.getEnd().withZoneSameInstant(ZoneId.of("UTC"));
            stmt.setTimestamp(9, Timestamp.valueOf(startZDT.toLocalDateTime()));
            stmt.setTimestamp(10, Timestamp.valueOf(endZDT.toLocalDateTime()));
            
            stmt.setString(11, Loggers.getUserName());
            stmt.setObject(12, appointment.getAppointmentId());
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
            
    public static void deleteAppointment(Appointment appointment) {
        String sqlDeletes = "DELETE FROM appointment WHERE appointmentId = ?";
        
        try {
            PreparedStatement stmt = DB_CONN.prepareStatement(sqlDeletes);
            stmt.setObject(1, appointment.getAppointmentId());
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }   
}
