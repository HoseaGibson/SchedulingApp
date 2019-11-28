package ViewController;

import DBManager.AppointmentData;
import DBManager.UserData;
import Model.Appointment;
import Model.User;
import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.Stage;

/**
 *
 * @author Hosea Gibson
 */
public class LoginScreenController implements Initializable {
    
    // Variables
    ResourceBundle rb;
    Locale userLocale;
    Logger userLog = Logger.getLogger("userlog.txt");
    
    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtUsername;

    @FXML
    private Label lblUsername;

    @FXML
    private Label lblPassword;

    @FXML
    private Button btnLogin;

    @FXML
    private Button btnExit;
    
    private String invalidCred;
    private String invalidPass;
    private String invalidCredentialTitle;
    
  
    private final ZoneId newzid = ZoneId.systemDefault();
    
     // Creates a new user
    public static User Loggers = new User();

    // Method to exit Program
    @FXML
    void onClickExit(ActionEvent eExitButton) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit Program");
        alert.setHeaderText("Are you sure you want to exit?");
        alert.setContentText("Press OK to exit the program. \nPress Cancel to stay on this screen.");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            Stage winMainScreen = (Stage)((Node)eExitButton.getSource()).getScene().getWindow();
            winMainScreen.close();
        }
        else {
            alert.close();
        }
    }
    /*
       Handles the login once button is pressed
       Checks if there are any appointments that start within 15mins 
       Shows Alert of upcoming appointments
       If username and password matches database move to next
    */
    @FXML
    void onClickLogin(ActionEvent event) throws IOException, Exception {
        String username = txtUsername.getText();
        String password = txtPassword.getText();
        Loggers.setUserName(username);
        
        boolean validUser = UserData.login(username, password);
        try{
                 Appointment upcomingAppt = AppointmentData.getUpcomingAppt();
                        if (!(upcomingAppt.getAppointmentId() == 0 )) {
                            Alert apptAlert = new Alert(Alert.AlertType.INFORMATION);
                            apptAlert.setTitle("Upcoming Appointment Reminder");
                            apptAlert.setHeaderText("You have an appointment in 15 mins!");
                            apptAlert.setContentText("You have an appointment scheduled" 
                                    + "\non " + upcomingAppt.getStart().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL))
                                    + "\nat " + upcomingAppt.getStart().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.FULL))
                                    + " with client " + upcomingAppt.getCustomer().getCustomerName() + ".");
                            apptAlert.showAndWait();
                        }
            }catch(Exception e){
                    e.getMessage();
                    e.printStackTrace();
                         
                    
                    }
        if(validUser) {
                                      
            ((Node) (event.getSource())).getScene().getWindow().hide();
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("AppointmentCalendar.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();           
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(invalidCred);
            alert.setHeaderText(invalidPass);
            alert.setContentText(invalidCredentialTitle);
            alert.showAndWait();
        }
                
    
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Testing different languages      
        //remove comment to set Default value to spanish or french
        
        //this.userLocale.setDefault(new Locale("es", "ES"));
        //this.userLocale.setDefault(new Locale("fr", "FR"));
        
        //Default value is English
        this.userLocale = Locale.getDefault();
        this.rb = ResourceBundle.getBundle("Languages/rb", this.userLocale);
        this.lblUsername.setText(this.rb.getString("username") + ":");
        this.lblPassword.setText(this.rb.getString("password") + ":");
        this.txtUsername.setPromptText(this.rb.getString("usernamePrompt"));
        this.txtPassword.setPromptText(this.rb.getString("passwordPrompt"));
        this.btnLogin.setText(this.rb.getString("btnLoginText"));
        this.btnExit.setText(this.rb.getString("btnExitText"));
        this.invalidCred =this.rb.getString("invalidCred");
        this.invalidPass = this.rb.getString("invalidPass");
        this.invalidCredentialTitle = this.rb.getString("invalidCredentialTitle"); 
    }
}
