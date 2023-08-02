package c195.controller;

import c195.dao.CountryDAO;
import c195.dao.CustomerDAO;
import c195.dao.FirstLevelDivisionDAO;
import c195.dao.UserDAO;
import c195.model.Country;
import c195.model.Customer;
import c195.model.FirstLevelDivision;
import c195.util.ModalHelper;
import c195.util.NavigationHelper;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * @author Anthony Sellers
 */
public class CustomerViewController implements Initializable {

    @FXML public TextField customerIdField;
    @FXML public TextField customerNameField;
    @FXML public TextField customerAddressField;
    @FXML public ComboBox<Country> customerCountry;
    @FXML public ComboBox<FirstLevelDivision> customerStateProvince;
    @FXML public TextField customerPostalCode;
    @FXML public TextField customerPhoneNumber;
    private boolean update = false;

    /**
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        handleCombo();
    }

    /**
     * Load Manage Customer View using Customer.
     * @param customer
     */

    public void loadCustomer(Customer customer) {
        customerIdField.setText(String.valueOf(customer.getCustomerID()));
        customerNameField.setText(customer.getCustomerName());
        customerAddressField.setText(customer.getAddress());
        customerPostalCode.setText(customer.getPostalCode());
        customerPhoneNumber.setText(customer.getPhone());

        selectComboBoxValue(customerCountry, customer.getDivision().getCountry());
        selectComboBoxValue(customerStateProvince, customer.getDivision());

        update = true;
    }

    private <T> void selectComboBoxValue(ComboBox<T> comboBox, T value) {
        if (value != null) {
            comboBox.setValue(value);
        }
    }


    /**
     * Joins TextFields to create Customer.
     * @param customerID
     * @param name
     * @param address
     * @param firstLevelDivision
     * @param postalCode
     * @param phoneNumber
     * @return
     */
    private Customer joinCustomerFields(long customerID, String name, String address, FirstLevelDivision firstLevelDivision, String postalCode, String phoneNumber) {
        Customer customer = createCustomerObject();
        customer.setCustomerID(customerID);
        customer.setCustomerName(name);
        customer.setAddress(address);
        customer.setDivision(firstLevelDivision);
        customer.setPostalCode(postalCode);
        customer.setPhone(phoneNumber);
        return customer;
    }

    private Customer createCustomerObject() {
        Customer customer = new Customer();
        customer.setLastUpdate(LocalDateTime.now());
        customer.setLastUpdatedBy(UserDAO.getCurrentUser().getUsername());
        return customer;
    }

    /**
     * Navigates user to the Main View.
     * @param actionEvent
     */
    @FXML
    private void cancelAction(ActionEvent actionEvent) {
        try {
            NavigationHelper.manageCustomer(actionEvent);
        } catch (IOException e) {
            displayNavigationError();
        }
    }

    private void displayNavigationError() {
        ModalHelper.displayAlert(Alert.AlertType.ERROR, "Navigation Error",
                "There was an error navigating to the main view.");
    }



    /**
     *
     * It is a constructor. With a lambda expression inside it
     *
     */
    private void handleCombo() {

        ObservableList<Country> allCountries = CountryDAO.getAllCountries();
        allCountries.sort(Comparator.comparing(Country::getCountry));
        customerCountry.setItems(allCountries);

        /**
         * This lambda expression defines an action event handler for the customerCountry ComboBox's action event.
         * When the user selects a country from the customerCountry ComboBox, this lambda expression is invoked to handle
         * the action event. It retrieves the selected Country object from the customerCountry ComboBox, retrieves a list of
         * FirstLevelDivision objects associated with the selected country from the database, sorts the divisions based on their
         * division names, sets the divisions as the items of the customerStateProvince ComboBox, resets the selection of
         * customerStateProvince, and sets up the prompt text for customerStateProvince.
         *
         * <p>By using a lambda expression, we can directly define the action event handler inline, making the code more concise and
         * readable. It provides a clear and focused definition of the behavior associated with the action event, improving the
         * understandability and maintainability of the code.
         *
         * <p>This lambda expression serves as a callback to handle the country selection in the customerCountry ComboBox and dynamically
         * populate the customerStateProvince ComboBox based on the selected country. The use of lambda expressions in event handling
         * allows for a more functional programming approach, making the code easier to reason about and maintain.
         *
         * @param actionEvent The action event triggered when the user selects a country in the customerCountry ComboBox.
         */
        customerCountry.setOnAction(actionEvent -> {
            Country selectedCountry = customerCountry.getSelectionModel().getSelectedItem();
            ObservableList<FirstLevelDivision> divisions = FirstLevelDivisionDAO.getByCountryId(selectedCountry.getCountryID());
            divisions.sort(Comparator.comparing(FirstLevelDivision::getDivision));
            customerStateProvince.setItems(divisions);
            resetCustomerStateProvinceSelection();
            setupCustomerStateProvincePrompt();
        });
    }

    private void resetCustomerStateProvinceSelection() {
        customerStateProvince.getSelectionModel().clearSelection();
        customerStateProvince.setValue(null);
    }

    private void setupCustomerStateProvincePrompt() {
        customerStateProvince.setPromptText("State / Province");
        customerStateProvince.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(FirstLevelDivision item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText("State / Province");
                } else {
                    setText(item.getDivision());
                }
            }
        });
    }


    /**
     * Save Customer.
     * @param actionEvent
     */
    /**
     * Save Customer.
     * @param actionEvent
     */
    @FXML
    private void saveCustomer(ActionEvent actionEvent) {
        String customerName = customerNameField.getText();
        String customerAddress = customerAddressField.getText();
        FirstLevelDivision firstLevelDivision = customerStateProvince.getSelectionModel().getSelectedItem();
        String postalCode = customerPostalCode.getText();
        String phoneNumber = customerPhoneNumber.getText();
        boolean customerResults = false;
        boolean validFields = validFields();
        if (validFields) {
            Customer customer;
            if (!update) {
                // Create New Customer
                customer = joinCustomerFields(-1, customerName, customerAddress, firstLevelDivision, postalCode, phoneNumber);
                customerResults = CustomerDAO.addCustomer(customer);
            } else {
                // Update Existing Customer
                long customerID = Long.parseLong(customerIdField.getText());
                customer = joinCustomerFields(customerID, customerName, customerAddress, firstLevelDivision, postalCode, phoneNumber);
                customerResults = CustomerDAO.updateCustomer(customer);
            }
            if (!customerResults) {
                String operation = update ? "Update" : "Save";
                displaySaveCustomerError(operation);
            } else {
                handleSaveCustomerSuccess(actionEvent);
            }
        }
    }
    private void displaySaveCustomerError(String operation) {
        String errorMessage = "Unable to " + operation + " Customer. Please check with an admin.";
        ModalHelper.displayAlert(Alert.AlertType.ERROR, operation + " Error", errorMessage);
    }
    private void handleSaveCustomerSuccess(ActionEvent actionEvent) {
        cancelAction(actionEvent);
    }


    /**
     * Boolean that validates Input Fields.
     * @return boolean
     */
    private boolean validFields() {
        List<String> errorFields = new ArrayList<>();

        validateFieldIsEmpty(customerNameField.getText(), "The Name is Empty", errorFields);
        validateFieldIsEmpty(customerAddressField.getText(), "The Address is Empty", errorFields);
        validateComboBoxSelection(customerCountry, "The Country is not selected", errorFields);
        validateComboBoxSelection(customerStateProvince, "The State / Province is not Selected", errorFields);
        validateFieldIsEmpty(customerPostalCode.getText(), "The Postal Code is Empty", errorFields);
        validateFieldIsEmpty(customerPhoneNumber.getText(), "The Phone Number is Empty", errorFields);

        if (!errorFields.isEmpty()) {
            String errorMessages = String.join("\n", errorFields);
            ModalHelper.displayAlert(Alert.AlertType.ERROR, "Error", "Please Address the Error(s)", errorMessages);
        }

        return errorFields.isEmpty();
    }

    private void validateFieldIsEmpty(String fieldValue, String errorMessage, List<String> errorFields) {
        if (fieldValue.isEmpty()) {
            errorFields.add(errorMessage);
        }
    }

    private <T> void validateComboBoxSelection(ComboBox<T> comboBox, String errorMessage, List<String> errorFields) {
        if (comboBox.getSelectionModel().isEmpty()) {
            errorFields.add(errorMessage);
        }
    }

}