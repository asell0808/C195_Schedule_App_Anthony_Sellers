package c195.controller;

import c195.util.NavigationHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Anthony Sellers
 */
public class HomeViewController implements Initializable {

    @FXML
    public Button reportBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
        } catch (Exception e) {
            showError("Error during initialization: " + e.getMessage());
        }
    }


    /**
     * Navigates the user to the ReportView
     * @param event ActionEvent
     * @throws IOException
     */
    @FXML
    public void navToReportView(ActionEvent event) {
        try {
            NavigationHelper.reportView(event);
        } catch (IOException e) {
            showError("Error navigating to ReportView: " + e.getMessage());
        }
    }

    /**
     * Navigates the user to the CustomerView.
     * @param event ActionEvent
     * @throws IOException
     */
    @FXML
    public void navToCustomerView(ActionEvent event) {
        try {
            NavigationHelper.manageCustomer(event);
        } catch (IOException e) {
            showError("Error navigating to CustomerView: " + e.getMessage());
        }
    }

    /**
     * Navigates the user to the AppointmentView.
     * @param event ActionEvent
     * @throws IOException
     */
    @FXML
    public void navToAppointmentView(ActionEvent event) {
        try {
            NavigationHelper.manageAppt(event);
        } catch (IOException e) {
            showError("Error navigating to AppointmentView: " + e.getMessage());
        }
    }

    private void showError(String message) {
        System.err.println(message);
    }

}