package c195.controller;

import c195.Main;
import c195.dao.AppointmentDAO;
import c195.dao.UserDAO;
import c195.model.Appointment;
import c195.util.Logger;
import c195.util.NavigationHelper;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * View Controller for Login View
 * @author Anthony Sellers
 */
public class LoginViewController implements Initializable {

    @FXML public Label title;
    @FXML public TextField userIdInput;
    @FXML public PasswordField passwordInput;
    @FXML public Label zoneID;
    @FXML public Button signInBtn;
    @FXML public Label usernameInset;
    @FXML public Label passwordInset;
    @FXML public Button exitBtn;

    private String alertTitle;
    private String alertHeader;
    private String alertContext;

    Stage stage;

    /**
     *  Login button for main screen.
     * @throws SQLException
     * @throws IOException
     * @throws Exception
     **/

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String displayName = ZoneId.systemDefault().toString();
        zoneID.setText(displayName);

        try {
            ResourceBundle bundle = ResourceBundle.getBundle("Languages");
            title.setText(bundle.getString("title"));
            usernameInset.setText(bundle.getString("username"));
            passwordInset.setText(bundle.getString("password"));
            userIdInput.setPromptText(bundle.getString("username"));
            passwordInput.setPromptText(bundle.getString("password"));
            signInBtn.setText(bundle.getString("login"));
            alertTitle = bundle.getString("alertTitle");
            alertHeader = bundle.getString("alertHeader");
            alertContext = bundle.getString("alertContext");
            exitBtn.setText(bundle.getString("exit"));
        } catch (MissingResourceException e) {
            showError("Missing resource bundle: " + e.getMessage());
        }
    }

    private void showError(String message) {
        System.err.println(message);
    }



    /**
     * Login Using User ID: Test and Password: Test, this event also contains a Lambda expression that notifies the console of a successful or unsuccessful login, and the justification for this Lambda is that it adds a layer of extra functionality for future updates.
     * @param event
     */
    @FXML
    public void appLogin(ActionEvent event) throws IOException {
        String userID = userIdInput.getText();
        String password = passwordInput.getText();
        Boolean validUser = UserDAO.login(userID, password);
        Logger.log(userID, validUser, "Login Attempt");

        if (validUser) {
            AppointmentDAO appointmentAccess = null;
            ObservableList<Appointment> getAllAppointments = appointmentAccess.getAllAppts();
            boolean appointmentWithin15Min = false;
            Appointment upcomingAppointment = null;

            for (Appointment appointment : getAllAppointments) {
                LocalDateTime startTime = appointment.getStart();
                LocalDateTime currentTimeMinus15Min = LocalDateTime.now().minusMinutes(15);
                LocalDateTime currentTimePlus15Min = LocalDateTime.now().plusMinutes(15);

                if (startTime.isAfter(currentTimeMinus15Min) && startTime.isBefore(currentTimePlus15Min)) {
                    appointmentWithin15Min = true;
                    upcomingAppointment = appointment;
                    break; // Exit the loop once we find the first appointment within 15 minutes
                }
            }

            if (appointmentWithin15Min) {
                int getAppointmentID = (int) upcomingAppointment.getAppointmentID();
                LocalDateTime displayTime = upcomingAppointment.getStart();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Appointment within 15 minutes: "
                        + getAppointmentID + " and appointment start time of: " + displayTime);
                Optional<ButtonType> confirmation = alert.showAndWait();
                System.out.println("There is an appointment within 15 minutes");
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "No upcoming appointments.");
                Optional<ButtonType> confirmation = alert.showAndWait();
                System.out.println("No upcoming appointments");
            }

            // Print successful login message
            Main.loginInterfaceOne successful = s -> "Login " + s;
            System.out.println(successful.getSuccessful("Successful.\nFor more information, check login_activity.txt in the file directory."));

            NavigationHelper.home(event);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(alertTitle);
            alert.setHeaderText(alertHeader);
            alert.setContentText(alertContext);
            alert.showAndWait();

            // Print unsuccessful login message
            Main.loginInterfaceTwo unsuccessful = u -> "Login " + u;
            System.out.println(unsuccessful.getUnsuccessful("Unsuccessful.\nFor more information, check login_activity.txt in the file directory."));
        }
    }


    /**
     * Lambda expression that exits application on exitBtn click, and it is justified because it streamlines the code needed to exit the stage on the action event.
     * @param event
     */
    public void appLogout(ActionEvent event) {
        exitBtn.setOnMouseClicked(e -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.close();
                System.out.println("The user has exited the application");
            }
        });
    }

}