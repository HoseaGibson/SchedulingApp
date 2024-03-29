package Model;

import Exception.CustExcep;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import javafx.beans.property.*;


/**
 *
 * @author Hosea Gibson
 */
public class Customer {
    private IntegerProperty customerId = new SimpleIntegerProperty();
    private StringProperty customerName = new SimpleStringProperty();
    private IntegerProperty addressId = new SimpleIntegerProperty();
    private BooleanProperty active = new SimpleBooleanProperty();
    private LocalDateTime createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdateBy;
    
    public Customer () {
    }

    public Customer(String customerId, String customerName) {
        customerId = customerId;
        customerName = customerName;
    }
    

    public int getCustomerId() {
        return customerId.get();
    }

    public void setCustomerId(int customerId) {
        this.customerId.set(customerId);
    }
    
    public IntegerProperty customerIdProperty() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName.get();
    }

    public void setCustomerName(String customerName) {
        this.customerName.set(customerName);
    }
    
    public StringProperty customerNameProperty() {
        return customerName;
    }

    public int getAddressId() {
        return addressId.get();
    }

    public void setAddressId(int addressId) {
        this.addressId.set(addressId);
    }
    
    public IntegerProperty addressIdProperty() {
        return addressId;
    }

    public boolean isActive() {
        return active.get();
    }

    public void setActive(boolean active) {
        this.active.set(active);
    }
    
    public BooleanProperty activeProperty() {
        return active;
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
    
    public static boolean isValidInput(Customer Cust, Address custAddress, City custCity, Country custCountry) throws CustExcep {
        if (Cust.getCustomerName().equals("")) {
            throw new CustExcep("You must enter a customer!");
        }
        if (custAddress.getAddress().equals("")) {
            throw new CustExcep("You must enter an address in the address field!");
        }
        if (custAddress.getPhone().equals("")) {
            throw new CustExcep("You must enter a phone number!");
        }
        if (custAddress.getPostalCode().equals("")) {
            throw new CustExcep("You must enter a postal code!");
        }
        if (custCity.getCity().equals("")) {
            throw new CustExcep("You must enter a city!");
        }
        if (custCountry.getCountry().equals("")) {
            throw new CustExcep("You must select a country!");
        }
        return true;
    }
}