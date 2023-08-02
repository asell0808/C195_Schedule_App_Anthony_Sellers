package c195.controller;

import c195.dao.AppointmentDAO;
import c195.dao.CustomerDAO;
import c195.model.Customer;
import c195.util.NavigationHelper;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.ResourceBundle;

import static c195.util.ModalHelper.displayAlert;

/**
 * @author Anthony Sellers
 */
public class ManageCustomerViewController implements Initializable {

    @FXML
    public TableColumn<Customer, Long> customerIdCol;
    @FXML
    public TableColumn<Customer, String> customerNameCol;
    @FXML
    public TableColumn<Customer, String> customerDivisionNameCol;
    @FXML
    public TableColumn<Customer, String> customerCountryCol;
    @FXML
    public TableColumn<Customer, String> customerAddressCol;
    @FXML
    public TableColumn<Customer, String> customerPostalCodeCol;
    @FXML
    public TableColumn<Customer, String> customerPhoneCol;
    @FXML
    public Button addCustomerBtn;
    @FXML
    public Button modifyCustomerBtn;
    @FXML
    public Button deleteCustomerBtn;
    @FXML
    public TableView<Customer> customerTable;

    private final ObservableList<Customer> customers = CustomerDAO.getAllCustomers();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        handleCustomerTable();
    }

    private void handleCustomerTable() {
        customerTable.setItems(customers);
        customerDivisionNameCol.setCellValueFactory(value -> new ReadOnlyObjectWrapper<>(value.getValue().getDivision().getDivision()));
        customerCountryCol.setCellValueFactory(value -> new ReadOnlyObjectWrapper<>(value.getValue().getDivision().getCountry().getCountry()));
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        customerPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        customerPostalCodeCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));

        customerTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Perform actions based on the selected customer
                System.out.println("Selected customer: " + newValue.getCustomerName());
            }
        });
    }


    @FXML
    private void addCustomer(ActionEvent actionEvent) {
        try {
            NavigationHelper.customerView(actionEvent, null);
        } catch (IOException e) {
            showErrorAlert("Error Opening Customer View", "Please check the application and try again.");
            e.printStackTrace();
        }
    }

    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    @FXML
    private void deleteCustomer() {
        Customer customer = customerTable.getSelectionModel().getSelectedItem();
        if (customer == null) {
            displayAlert(Alert.AlertType.INFORMATION, "No customer selected", "Please select a customer.");
            return;
        }

        if (!AppointmentDAO.getApptByCustomer(customer).isEmpty()) {
            displayAlert(Alert.AlertType.ERROR, "Deletion Error",
                    "Customer still has appointment(s)",
                    "Please delete the appointment(s) first.");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Customer");
        alert.setHeaderText("Confirmation");
        alert.setContentText("Are you sure you want to delete this customer?");
        alert.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            boolean customerRemoved = CustomerDAO.removeCustomer(customer);
            if (customerRemoved) {
                customers.remove(customer);
            } else {
                displayAlert(Alert.AlertType.ERROR,
                        "Customer Deletion Error",
                        "The customer was NOT deleted.",
                        "Please try again.");
            }
        }
    }

    @FXML
    private void updateCustomer(ActionEvent actionEvent) {
        Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
        if (selectedCustomer == null) {
            displayAlert(Alert.AlertType.INFORMATION, "No customer selected", "Please select a customer.");
            return;
        }

        try {
            NavigationHelper.customerView(actionEvent, selectedCustomer);
        } catch (IOException e) {
            displayAlert(Alert.AlertType.ERROR, "Error Opening Customer View", "Please see the developer.");
        }
    }

    @FXML
    public void backAction(ActionEvent event) {
        try {
            NavigationHelper.home(event);
        } catch (IOException e) {
            displayAlert(Alert.AlertType.ERROR, "Error Opening Home View", "Please see the developer.");
        }
    }
}