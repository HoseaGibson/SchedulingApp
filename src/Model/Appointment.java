
package Model;

import DBManager.AppointmentData;
import Exception.AppointExcept;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

/**
 *
 * @author Hosea Gibson
 */
public class Appointment {
    private IntegerProperty appointmentId = new SimpleIntegerProperty();
    private IntegerProperty customerId = new SimpleIntegerProperty();
    private IntegerProperty userId = new SimpleIntegerProperty();
    private StringProperty title = new SimpleStringProperty();
    private StringProperty description = new SimpleStringProperty();
    private StringProperty location = new SimpleStringProperty();
    private StringProperty contact = new SimpleStringProperty();
    private StringProperty type = new SimpleStringProperty();
    private StringProperty url = new SimpleStringProperty();
    private StringProperty aptStart = new SimpleStringProperty();
     private StringProperty aptEnd = new SimpleStringProperty();
    private ZonedDateTime start;
    private ZonedDateTime end;
    private LocalDateTime createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdateBy;
    private Customer customer = new Customer();
    
    public Appointment() {
    }

    public Appointment(int appointmentId, int customerId, ZonedDateTime start, ZonedDateTime end, String title, String description, String location, String contact) {
        setAppointmentId(appointmentId);
        setCustomerId(customerId);
        setStart(start);
        setEnd(end);
        setTitle(title);
        setDescription(description);
        setLocation(location);
        setContact(contact);
    }

    public Appointment(String appointmentId, String start, String end, String title, String type, Customer customer, String userId) {
        appointmentId = appointmentId;    
        start = start;
        end = end;
        title = title;
        description = description;
        customer = customer;
        userId = userId;   
        
    }

    public StringProperty getAptEnd() {
        return aptEnd;
    }

    public void setAptEnd(StringProperty aptEnd) {
        this.aptEnd = aptEnd;
    }

    
    public StringProperty getAptStart() {
        return aptStart;
    }

    public void setAptStart(StringProperty aptStart) {
        this.aptStart = aptStart;
    }
    
  

    

    public final int getAppointmentId() {
        return appointmentId.get();
    }

    public final void setAppointmentId(int appointmentId) {
        this.appointmentId.set(appointmentId);
    }
    
    public IntegerProperty appointmentIdProperty() {
        return appointmentId;
    }

    public final int getCustomerId() {
        return customerId.get();
    }

    public final void setCustomerId(int customerId) {
        this.customerId.set(customerId);
    }
    
    public IntegerProperty customerIdProperty() {
        return customerId;
    }

    public final int getUserId() {
        return userId.get();
    }

    public final void setUserId(int userId) {
        this.userId.set(userId);
    }
    
    public IntegerProperty userIdProperty() {
        return userId;
    }

    public String getTitle() {
        return title.get();
    }

    public void setTitle(String title) {
        this.title.set(title);
    }
    
    public StringProperty titleProperty() {
        return title;
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description.set(description);
    }
    
    public StringProperty descriptionProperty() {
        return description;
    }

    public String getLocation() {
        return location.get();
    }

    public void setLocation(String location) {
        this.location.set(location);
    }
    
    public StringProperty locationProperty() {
        return location;
    }

    public String getContact() {
        return contact.get();
    }

    public void setContact(String contact) {
        this.contact.set(contact);
    }
    
    public StringProperty contactProperty() {
        return contact;
    }

    public String getType() {
        return type.get();
    }

    public void setType(String type) {
        this.type.set(type);
    }
    
    public StringProperty typeProperty() {
        return type;
    }

    public String getUrl() {
        return url.get();
    }

    public void setUrl(String url) {
        this.url.set(url);
    }
    
    public StringProperty urlProperty() {
        return url;
    }

    public ZonedDateTime getStart() {
        return start;
    }

    public void setStart(ZonedDateTime start) {
        this.start = start;
    }

    public ZonedDateTime getEnd() {
        return end;
    }

    public void setEnd(ZonedDateTime end) {
        this.end = end;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }
    
    public Customer getCustomer() {
        return customer;
    }
    
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    
    public boolean isValidInput() throws AppointExcept {
        if (this.customer == null) {
            throw new AppointExcept("There was no customer selected!");
        }
        if (this.title.get().equals("")) {
            throw new AppointExcept("You must enter a title!");
        }
        if (this.description.get().equals("")) {
            throw new AppointExcept("You must enter a description!");
        }
        if (this.location.get().equals("")) {
            throw new AppointExcept("You must enter a location!");
        }
        if (this.contact.get().equals("")) {
            throw new AppointExcept("You must enter a contact!");
        }
        if (this.type.get().equals("")) {
            throw new AppointExcept("You must enter a type!");
        }
        if (this.url.get().equals("")) {
            throw new AppointExcept("You must enter a url!");
        }
        isNotOverlapping();
        isValidTime();
        return true;
    }
    
    public boolean isValidTime() throws AppointExcept {
        LocalTime midnight = LocalTime.MIDNIGHT;
        LocalDate apptStartDate = this.start.toLocalDate();
        LocalTime apptStartTime = this.start.toLocalTime();
        LocalDate apptEndDate = this.end.toLocalDate();
        LocalTime apptEndTime = this.end.toLocalTime();
        int weekDay = apptStartDate.getDayOfWeek().getValue();
        
        if (!apptStartDate.isEqual(apptEndDate)) {
            throw new AppointExcept("An appoinment can only be a single day!");
        }
        if (weekDay == 6 || weekDay == 7) {
            throw new AppointExcept("An appointment can only be scheduled on weekdays!");
        }
        if (apptStartTime.isBefore(midnight.plusHours(8))) {
            throw new AppointExcept("An appointment cannot be scheduled before normal business hours!");
        }
        if (apptEndTime.isAfter(midnight.plusHours(17))) {
            throw new AppointExcept("An appointment cannot be scheduled after normal business hours!");
        }
        if (apptStartDate.isBefore(LocalDate.now()) || apptStartTime.isBefore(LocalTime.MIDNIGHT)) {
            throw new AppointExcept("An appointment cannot be scheduled in the past!");
        }
        return true;
    }
    
    
    public boolean isNotOverlapping() throws AppointExcept {
        ObservableList<Appointment> overlappingAppt = AppointmentData.getOverlappingAppts(this.start.toLocalDateTime(), this.end.toLocalDateTime());
        if (overlappingAppt.size() > 1) {
            throw new AppointExcept("An appointment cannot be scheduled at the same time as another appointment!");
        }
        return true;
    }
}
