package c195.util;

import c195.Main;
import c195.controller.AppointmentViewController;
import c195.controller.CustomerViewController;
import c195.model.Appointment;
import c195.model.Customer;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * @author Anthony Sellers
 */
public class NavigationHelper {

    /**
     * Navigates to the Home View.
     * @param event
     * @throws IOException
     */
    public static void home(ActionEvent event) throws IOException {
        nav(event, "view/home-view.fxml");
    }

    /**
     * Navigates to the Manage Appointment View.
     * @param event
     * @throws IOException
     */
    public static void manageAppt(ActionEvent event) throws IOException {
        nav(event, "view/manage-appointment-view.fxml");
    }

    /**
     * Navigates to the Manage Customer View.
     * @param event
     * @throws IOException
     */
    public static void manageCustomer(ActionEvent event) throws IOException {
        nav(event, "view/manage-customer-view.fxml");
    }

    /**
     * Navigates to the CustomerView.
     * @param event
     * @param customer
     * @throws IOException
     */
    public static void customerView(ActionEvent event, Customer customer) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view/customer-view.fxml"));
        Parent parent = fxmlLoader.load();
        if (customer != null) {
            CustomerViewController customerViewController = fxmlLoader.getController();
            customerViewController.loadCustomer(customer);
        }
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
    }

    /**
     * Navigates to the Appointment View.
     * @param event
     * @param appointment
     * @throws IOException
     */
    public static void appointmentView(ActionEvent event, Appointment appointment) throws IOException {
        final FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view/appointment-view.fxml"));
        Parent parent = fxmlLoader.load();
        if (appointment != null) {
            AppointmentViewController appointmentViewController = fxmlLoader.getController();
            appointmentViewController.loadAppt(appointment);
        }
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
    }


    /**
     * Navigates to the Report View.
     * @param event
     * @throws IOException
     */
    public static void reportView(ActionEvent event) throws IOException {
        nav(event, "view/report-view.fxml");
    }

    public static void nav(ActionEvent event, String designation) throws IOException {
        if (designation != null) {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(designation));
            Parent parent = fxmlLoader.load();
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
            stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
        } else {
            // Handle the case where the designation is null
            // display an error message or perform alternative actions
            System.out.println("Invalid designation. Cannot navigate to null view.");
        }
    }

}