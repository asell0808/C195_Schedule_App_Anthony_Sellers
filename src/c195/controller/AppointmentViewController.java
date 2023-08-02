package c195.controller;

import c195.dao.AppointmentDAO;
import c195.dao.ContactDAO;
import c195.dao.CustomerDAO;
import c195.dao.UserDAO;
import c195.exception.InvalidApptException;
import c195.model.Appointment;
import c195.model.Contact;
import c195.model.Customer;
import c195.util.LocalDateTimeHelper;
import c195.util.ModalHelper;
import c195.util.NavigationHelper;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;
import java.io.IOException;
import java.net.URL;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/**
 * @author Anthony Sellers
 */
public class AppointmentViewController implements Initializable {

    @FXML public ComboBox<Customer> customerComboBox;
    @FXML public ComboBox<Contact> contactComboBox;
    @FXML public TextField idAppointmentField;
    @FXML public TextField titleField;
    @FXML public TextField descriptionField;
    @FXML public TextField locationField;
    @FXML public TextField typeField;
    @FXML public DatePicker startDateField;
    @FXML public TextField startHourTime;
    @FXML public TextField startMinuteTime;
    @FXML public ComboBox<String> startPmAm;
    @FXML public DatePicker endDateField;
    @FXML public TextField endHourTime;
    @FXML public TextField endMinuteTime;
    @FXML public ComboBox<String> endPmAm;
    @FXML public Button saveButton;
    @FXML public Button cancelButton;

    private ObservableList<Contact> contactList = ContactDAO.getAllContacts();
    private ObservableList<Customer> customerList = CustomerDAO.getAllCustomers();
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd h:m a");


    /**
     * This lambda expression defines a callback that creates and configures a custom ListCell for a ListView of Customers.
     * The lambda expression takes a ListView<Customer> as an input parameter and returns a ListCell<Customer>. It allows
     * for the creation of a customized ListCell with specific behavior and appearance for each item in the ListView.
     * The overridden updateItem method is responsible for updating the content and visual representation of each cell based
     * on the Customer item being displayed. If the item is null or empty, the cell is cleared. Otherwise, the cell's text is
     * set to the concatenation of the Customer's ID and name.
     * By using a lambda expression, we can define the behavior of the ListCell inline, making the code more concise and
     * expressive. It provides a clear and self-contained definition of how the ListCell should be created and updated,
     * enhancing readability and maintainability of the code.
     * It is a constructor.
     *
     * /**
     *  * Represents a cell factory for a {@link ListView} that displays a list of {@link Customer}s.
     *  * The cell factory creates {@link ListCell} instances to customize the appearance and behavior of each cell.
     *  * This implementation uses a lambda expression to create the {@link ListCell} with custom content.
     *  *
     *  * <p>The cell displays the customer's ID and name in the format: "CustomerID - CustomerName".
     *  * If the item is null or empty, the cell displays no content.
     *  *
     *  * <p>Example usage:
     *  * <pre>{@code
     *  * ListView<Customer> customerListView = new ListView<>();
     *  * customerListView.setCellFactory(customerListCell);
     *  * }</pre>
     *
     *
     * @param item
     * @param empty
     */

    private Callback<ListView<Customer>, ListCell<Customer>> customerListCell = listView -> new ListCell<>() {
        @Override
        protected void updateItem(Customer item, boolean empty) {

            super.updateItem(item, empty);
            if (item == null || empty) {
                setGraphic(null);
            } else {
                setText(item.getCustomerID() + " - " + item.getCustomerName());
            }
        }
    };

    private Callback<ListView<Contact>, ListCell<Contact>> contactListCell = listView -> new ListCell<>() {
        @Override
        protected void updateItem(Contact item, boolean empty) {
            super.updateItem(item, empty);
            if (item == null || empty) {
                setGraphic(null);
            } else {
                setText(item.getContactID() + " - " + item.getContactName());
            }
        }
    };

    /**
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupComboBoxes();
        setupPmAmSelections();
        setupNumericFields();
    }

    private void setupComboBoxes() {
        customerComboBox.setButtonCell(customerListCell.call(null));
        customerComboBox.setCellFactory(customerListCell);
        contactComboBox.setButtonCell(contactListCell.call(null));
        contactComboBox.setCellFactory(contactListCell);
        customerComboBox.setItems(customerList);
        contactComboBox.setItems(contactList);
    }

    private void setupPmAmSelections() {
        startPmAm.getItems().addAll("PM", "AM");
        startPmAm.getSelectionModel().select(0);
        endPmAm.getItems().addAll("PM", "AM");
        endPmAm.getSelectionModel().select(0);
    }

    private void setupNumericFields() {
        numbersField(startHourTime);
        numbersField(startMinuteTime);
        numbersField(endHourTime);
        numbersField(endMinuteTime);
    }


    private void numbersField(TextField someTextField) {
        someTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            String filteredValue = newValue.replaceAll("[^\\d]", "");
            if (filteredValue.length() > 2) {
                filteredValue = filteredValue.substring(0, 2);
            }
            someTextField.setText(filteredValue);
        });
    }


    /**
     * Navigates user to the Main View.
     * @param event
     */
    @FXML
    private void cancelApptAction(ActionEvent event) {
        try {
            NavigationHelper.manageAppt(event);
        } catch (IOException exception) {
            System.err.println("Error occurred while navigating: " + exception.getMessage());
        }
    }


    /**
     * Load Appointment View Controller using Appointment.
     * @param appointment
     */
    public void loadAppt(Appointment appointment) {
        // Set customer and contact ComboBox values
        customerComboBox.setValue(appointment.getCustomer());
        contactComboBox.setValue(appointment.getContact());

        // Set text fields
        idAppointmentField.setText(String.valueOf(appointment.getAppointmentID()));
        titleField.setText(appointment.getTitle());
        descriptionField.setText(appointment.getDescription());
        locationField.setText(appointment.getLocation());
        typeField.setText(appointment.getType());

        // Set date fields
        startDateField.setValue(appointment.getStart().toLocalDate());
        endDateField.setValue(appointment.getEnd().toLocalDate());

        // Set start time fields
        startHourTime.setText(LocalDateTimeHelper.get12Hr(appointment.getStart()));
        startMinuteTime.setText(LocalDateTimeHelper.get12Min(appointment.getStart()));
        startPmAm.setValue(LocalDateTimeHelper.get12AMPM(appointment.getStart()).toUpperCase());

        // Set end time fields
        endHourTime.setText(LocalDateTimeHelper.get12Hr(appointment.getEnd()));
        endMinuteTime.setText(LocalDateTimeHelper.get12Min(appointment.getEnd()));
        endPmAm.setValue(LocalDateTimeHelper.get12AMPM(appointment.getEnd()).toUpperCase());
    }


    /**
     * Saves Appts.
     * @param event
     */
    @FXML
    private void saveAppointmentAction(ActionEvent event) {
        try {
            boolean updatingAppt = !idAppointmentField.getText().isEmpty();
            LocalDateTime startLocalDateTime = createLocalDateTime(startDateField, startHourTime, startMinuteTime, startPmAm);
            LocalDateTime endLocalDateTime = createLocalDateTime(endDateField, endHourTime, endMinuteTime, endPmAm);

            Appointment appointment = createAppointment(updatingAppt, startLocalDateTime, endLocalDateTime);
            appointment.validate();

            saveOrUpdateAppointment(appointment, updatingAppt);
            cancelApptAction(event);
        } catch (InvalidApptException e) {
            ModalHelper.displayAlert(Alert.AlertType.ERROR, "Error(s)", "Please Address the following Error(s)", e.getMessage());
        }
    }

    private LocalDateTime createLocalDateTime(DatePicker dateField, TextField hourField, TextField minuteField, ComboBox<String> amPmComboBox) {
        LocalDate date = dateField.getValue();
        int hour = Integer.parseInt(hourField.getText());
        int minute = Integer.parseInt(minuteField.getText());
        String amPm = amPmComboBox.getValue();

        if (amPm.equals("PM") && hour != 12) {
            hour += 12;
        } else if (amPm.equals("AM") && hour == 12) {
            hour = 0;
        }

        return LocalDateTime.of(date, LocalTime.of(hour, minute));
    }

    private Appointment createAppointment(boolean updatingAppt, LocalDateTime startLocalDateTime, LocalDateTime endLocalDateTime) {
        Appointment appointment = new Appointment();
        appointment.setTitle(titleField.getText());
        appointment.setDescription(descriptionField.getText());
        appointment.setLocation(locationField.getText());
        appointment.setType(typeField.getText());
        appointment.setStart(startLocalDateTime);
        appointment.setEnd(endLocalDateTime);
        appointment.setCustomer(customerComboBox.getValue());
        appointment.setContact(contactComboBox.getValue());
        appointment.setUser(UserDAO.getCurrentUser());

        if (updatingAppt) {
            appointment.setAppointmentID(Long.parseLong(idAppointmentField.getText()));
        }

        return appointment;
    }

    private void saveOrUpdateAppointment(Appointment appointment, boolean updatingAppt) {
        if (updatingAppt) {
            AppointmentDAO.updateAppt(appointment);
        } else {
            AppointmentDAO.addAppt(appointment);
        }
    }

    /**
     * Creates the LocalDateTime from hour, minute, and pmAM.
     * @param localDate
     * @param hour
     * @param minute
     * @param pmAM
     * @return LocalDateTime
     * @throws InvalidApptException
     */
    private LocalDateTime createLocalDateTime(LocalDate localDate, String hour, String minute, String pmAM) throws InvalidApptException {
        try {
            final String localDateTime = localDate.toString() + " " + hour + ":" + minute + " " + pmAM;
            return LocalDateTime.parse(localDateTime, dateTimeFormatter);
        } catch (Exception e) {
            throw new InvalidApptException("Missing Date(s)");
        }
    }
}