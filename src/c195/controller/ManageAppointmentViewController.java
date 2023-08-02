package c195.controller;

import c195.dao.AppointmentDAO;
import c195.model.Appointment;
import c195.util.LocalDateTimeHelper;
import c195.util.ModalHelper;
import c195.util.NavigationHelper;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.IOException;
import java.net.URL;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.stream.Collectors;
import static c195.util.ModalHelper.displayAlert;

/**
 * @author Anthony Sellers
 */
public class ManageAppointmentViewController implements Initializable {

    @FXML public TableColumn<Appointment, Long> apptIdCol;
    @FXML public TableColumn<Appointment, String> apptTitleCol;
    @FXML public TableColumn<Appointment, String> apptDescCol;
    @FXML public TableColumn<Appointment, String> apptLocationCol;
    @FXML public TableColumn<Appointment, Long> apptContactCol;
    @FXML public TableColumn<Appointment, String> apptTypeCol;
    @FXML public TableColumn<Appointment, LocalDateTime> apptStartCol;
    @FXML public TableColumn<Appointment, LocalDateTime> apptEndCol;
    @FXML public TableColumn<Appointment, Long> apptCustomerIDCol;
    @FXML public TableColumn<Appointment, Long> apptUserIDCol;
    @FXML public Button addApptBtn;
    @FXML public Button modifyApptBtn;
    @FXML public Button deleteApptBtn;
    @FXML public TableView<Appointment> apptTable;
    @FXML public Label upcomingAppts;
    @FXML public ToggleGroup sortByGroup;
    @FXML public RadioButton allSort;
    @FXML public RadioButton monthSort;
    @FXML public RadioButton weekSort;

    private final ObservableList<Appointment> appointments = AppointmentDAO.getAllAppts();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        handleApptTable();
        checkAppointments();
    }

    /**
     * Checks to see if an Appointment Exist within 15 minutes on appt view
     */

    private void checkAppointments() {
        LocalDateTime nowDateTime = LocalDateTime.now();
        LocalDateTime fifteenMinutesLater = nowDateTime.plusMinutes(15);
        for (Appointment currentAppointment : appointments) {
            LocalDateTime startDateTime = currentAppointment.getStart();
            if (startDateTime.isAfter(nowDateTime) && startDateTime.isBefore(fifteenMinutesLater)) {
                String formattedStartDateTime = startDateTime.format(DateTimeFormatter.ofPattern("MM-dd-yyyy hh:mm a"));
                String header = "Appointment " + currentAppointment.getAppointmentID() +
                        " is within 15 minutes. " +
                        formattedStartDateTime;
                upcomingAppts.setText(header);
                return;
            }
        }
    }


    /**
     * Opens Manage Appointment View to add appts.
     * @param event
     */
    public void addAppt(ActionEvent event) {
        try {
            // Custom code before opening the appointment view
            System.out.println("Adding a new appointment...");

            showAppointmentView(event, null);
        } catch (IOException e) {
            showErrorAlert("Error Opening Appointment View.", "Please Contact the Developer.");
        }
    }


    private void showAppointmentView(ActionEvent event, Appointment appointment) throws IOException {
        // Code to navigate to the appointment view
        NavigationHelper.appointmentView(event, appointment);

        // Custom code after opening the appointment view
        System.out.println("Appointment view opened.");
    }

    private void showErrorAlert(String title, String message) {
        ModalHelper.displayAlert(Alert.AlertType.ERROR, title, message);

        // Custom code after displaying the error alert
        System.out.println("Error alert displayed.");
    }


    private void handleApptTable() {
        appointments.sort(Comparator.comparing(Appointment::getAppointmentID));
        apptTable.setItems(FXCollections.observableList(appointments));

        apptIdCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getAppointmentID()));
        apptTitleCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        apptDescCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));
        apptLocationCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLocation()));
        apptTypeCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType()));

        apptStartCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getStart()));
        apptStartCol.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDateTime dateTime, boolean empty) {
                super.updateItem(dateTime, empty);
                if (empty || dateTime == null) {
                    setText(null);
                } else {
                    setText(dateTime.format(DateTimeFormatter.ofPattern("MM-dd-yyyy hh:mm a")));
                }
            }
        });

        apptEndCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getEnd()));
        apptEndCol.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDateTime dateTime, boolean empty) {
                super.updateItem(dateTime, empty);
                if (empty || dateTime == null) {
                    setText(null);
                } else {
                    setText(dateTime.format(DateTimeFormatter.ofPattern("MM-dd-yyyy hh:mm a")));
                }
            }
        });

        apptCustomerIDCol.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getCustomer().getCustomerID()));
        apptUserIDCol.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getUser().getUserID()));
        apptContactCol.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getContact().getContactID()));

        sortByGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (allSort.isSelected()) {
                apptTable.setItems(FXCollections.observableList(appointments));
            } else if (monthSort.isSelected()) {
                apptTable.setItems(FXCollections.observableList(sortApptsByThisMonth()));
            } else if (weekSort.isSelected()) {
                apptTable.setItems(FXCollections.observableList(sortApptsByThisWeek()));
            }
        });
    }


    /**
         * Opens Manage Appointment View w/ selected Appt to modify.
         * @param event
         */
    @FXML
    public void modifyAppt(ActionEvent event) {
        try {
            Appointment appointment = apptTable.getSelectionModel().getSelectedItem();
            if (appointment != null) {
                NavigationHelper.appointmentView(event, appointment);
            } else {
                ModalHelper.displayAlert(Alert.AlertType.ERROR, "Please Select an Appointment.");
            }
        } catch (IOException e) {
            ModalHelper.displayAlert(Alert.AlertType.ERROR, "Error Opening Appointment View.", "Please See Developer");
        }
    }

    @FXML
    public void deleteAppt(ActionEvent event) {
        Appointment selectedAppointment = apptTable.getSelectionModel().getSelectedItem();

        if (selectedAppointment == null) {
            ModalHelper.displayAlert(Alert.AlertType.ERROR,
                    "Appointment Deletion Error",
                    "No appointment selected.",
                    "Please select an appointment.");
            return;
        }

        int deleteAppointmentID = (int) selectedAppointment.getAppointmentID();
        String deleteAppointmentType = selectedAppointment.getType();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Appointment");
        alert.setHeaderText("Confirmation");
        alert.setContentText("Are you sure you want to delete this appointment?" +
                "\n\nAppointment ID: " + deleteAppointmentID +
                "\nAppointment Type: " + deleteAppointmentType);

        ButtonType deleteButton = new ButtonType("Delete", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(deleteButton, cancelButton);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == deleteButton) {
            boolean appointmentRemoved = AppointmentDAO.removeAppt(selectedAppointment);
            if (appointmentRemoved) {
                appointments.remove(selectedAppointment);

                if (monthSort.isSelected()) {
                    apptTable.setItems(sortApptsByThisMonth());
                } else if (weekSort.isSelected()) {
                    apptTable.setItems(sortApptsByThisWeek());
                }
            } else {
                ModalHelper.displayAlert(Alert.AlertType.ERROR,
                        "Appointment Deletion Error",
                        "Failed to delete the appointment.",
                        "Please try again.");
            }
        }
    }

    @FXML
    public void backAction(ActionEvent event) {
        try {
            // Code before navigating back to the home view
            System.out.println("Going back to home view...");

            NavigationHelper.home(event);

            // Code after navigating back to the home view
            System.out.println("Home view displayed.");
        } catch (IOException e) {
            ModalHelper.displayAlert(Alert.AlertType.ERROR, "Error Opening Appointment View.", "Please See Developer");
        }
    }


    /**
     * Sorts Appointments into observable list for month.
     * @return Observable List of Appointments
     */
    public ObservableList<Appointment> sortApptsByThisMonth() {
        final Month currentMonth = LocalDate.now().getMonth();
        return appointments.stream()
                .filter(appointment -> appointment.getStart().getMonth() == currentMonth)
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
    }

    private ObservableList<Appointment> sortApptsByThisWeek() {
        final int currentWeekOfYear = LocalDate.now().get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear());
        return appointments.stream()
                .filter(appointment -> {
                    final int appointmentWeekOfYear = appointment.getStart().toLocalDate().get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear());
                    return appointmentWeekOfYear == currentWeekOfYear;
                })
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
    }

}
