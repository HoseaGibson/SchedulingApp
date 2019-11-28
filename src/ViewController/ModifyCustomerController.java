package ViewController;

import DBManager.AddressData;
import DBManager.CityData;
import DBManager.CountryData;
import DBManager.CustomerData;
import Exception.CustExcep;
import Model.Address;
import Model.City;
import Model.Country;
import Model.Customer;
import static ViewController.CustomerSelectionController.selectedCust;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 * @author Hosea Gibson
 */
public class ModifyCustomerController implements Initializable {

    // Variables
    @FXML
    private Label lblCustName;

    @FXML
    private Label lblCustAddress;

    @FXML
    private Label lblCustAddress2;

    @FXML
    private Label lblCity;

    @FXML
    private Label lblCustPostalCode;

    @FXML
    private Label lblCustPhone;

    @FXML
    private TextField txtCustAddress;
    
    @FXML
    private TextField txtCustAddress2;

    @FXML
    private TextField txtCustCity;

    @FXML
    private TextField txtCustPostalCode;

    @FXML
    private TextField txtCustPhone;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnExit;

    @FXML
    private TextField txtCustName;
    
    @FXML
    private Label lblCountry;

    @FXML
    private ComboBox<Country> cbCountry;
    
    @FXML
    private Address cAddress = AddressData.getAddressById(selectedCust.getAddressId());
    private City cCity = CityData.getCityById(cAddress.getCityId());
    private Country cCountry = CountryData.getCountryById(cCity.getCountryId());

    // Method to exit Modify Customer Screen
    @FXML
    void onClickExit(ActionEvent eExitButton) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit Customer Modification");
        alert.setHeaderText("Are you sure you want to exit?");
        alert.setContentText("Press OK to exit the program. \nPress Cancel to stay on this screen.");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            try {
                FXMLLoader aCLoader = new FXMLLoader(AppointmentCalendarController.class.getResource("AppointmentCalendar.fxml"));
                Parent aCScreen = aCLoader.load();
                Scene aCScene = new Scene(aCScreen);
                Stage aCStage = new Stage();
                aCStage.setTitle("Appointment Calendar");
                aCStage.setScene(aCScene);
                aCStage.show();
                Stage modStage = (Stage) btnExit.getScene().getWindow();
                modStage.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            alert.close();
        }
    }

    // Method to save modified customer information
    @FXML
    void onClickSave(ActionEvent event) {
        Alert saveAlert = new Alert(Alert.AlertType.CONFIRMATION);
        saveAlert.setTitle("Save Customer Modifications");
        saveAlert.setHeaderText("Are you sure you want to save?");
        saveAlert.setContentText("Press OK to save the modifications. \nPress Cancel to stay on this screen.");
        saveAlert.showAndWait();
        if (saveAlert.getResult() == ButtonType.OK) {
            try {
                updateCustInfo();
                if (Customer.isValidInput(selectedCust, cAddress, cCity, cCountry)) {
                    try {
                        CustomerData.updateCustomer(selectedCust);
                        AddressData.updateAddress(cAddress);
                        CityData.updateCity(cCity);
                        FXMLLoader aCLoader = new FXMLLoader(AppointmentCalendarController.class.getResource("AppointmentCalendar.fxml"));
                        Parent aCScreen = aCLoader.load();
                        Scene aCScene = new Scene(aCScreen);
                        Stage aCStage = new Stage();
                        aCStage.setTitle("Appointment Calendar");
                        aCStage.setScene(aCScene);
                        aCStage.show();
                        Stage modStage = (Stage) btnSave.getScene().getWindow();
                        modStage.close();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            catch (CustExcep e) {
                Alert exAlert = new Alert(Alert.AlertType.ERROR);
                exAlert.setTitle("Exception");
                exAlert.setHeaderText("There was an exception!");
                exAlert.setContentText(e.getMessage());
                exAlert.showAndWait().filter(response -> response == ButtonType.OK);
            }
        }
        else {
            saveAlert.close();
        }
    }
    
    // Method to get text entered into text fields that will be updated
    public void updateCustInfo() {
        try {
            selectedCust.setCustomerName(txtCustName.getText());
            cAddress.setAddress(txtCustAddress.getText());
            cAddress.setAddress2(txtCustAddress2.getText());
            cAddress.setPostalCode(txtCustPostalCode.getText());
            cAddress.setPhone(txtCustPhone.getText());
            cCity.setCity(txtCustCity.getText());
            cCity.setCountryId(cbCountry.getSelectionModel().getSelectedItem().getCountryId());
        }
        catch (NullPointerException e) {
            Alert nullAlert = new Alert(Alert.AlertType.ERROR);
            nullAlert.setTitle("Customer Addition Error");
            nullAlert.setHeaderText("The customer is not able to be added!");
            nullAlert.setContentText("One or more fields are empty!" + "\n" + e.getCause().toString());
            nullAlert.showAndWait();
        }
    }
    
    // Method to set combobox for the countries
    public void setCountries() {
        cbCountry.setItems(CountryData.getAllCountries());
    }
    
   
    public void setSelectedCustomerInfo() {
        txtCustName.setText(selectedCust.getCustomerName());
        txtCustAddress.setText(cAddress.getAddress());
        txtCustAddress2.setText(cAddress.getAddress2());
        txtCustPostalCode.setText(cAddress.getPostalCode());
        txtCustPhone.setText(cAddress.getPhone());
        txtCustCity.setText(cCity.getCity());
        cbCountry.setValue(cCountry);
    }
    
    public void convertCountryString() {
        cbCountry.setConverter(new StringConverter<Country>() {
            @Override
            public String toString(Country country) {
                return country.getCountry();
            }

            @Override
            public Country fromString(String string) {
                return cbCountry.getValue();
            }
        });
    }
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setCountries();
        convertCountryString();
        setSelectedCustomerInfo();
    }    
    
}
