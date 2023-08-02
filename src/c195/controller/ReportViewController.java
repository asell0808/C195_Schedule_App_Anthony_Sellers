package c195.controller;

import c195.dao.AppointmentDAO;
import c195.model.Appointment;
import c195.model.Contact;
import c195.util.ModalHelper;
import c195.util.NavigationHelper;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author Anthony Sellers
 */
public class ReportViewController implements Initializable {

    @FXML public ComboBox<String> typeSectionMonthCombo;
    @FXML public ComboBox<String> typeSectionTypeCombo;
    @FXML public Label typeCountLoadingLabel;
    @FXML public Label contactLoadingLabel;
    @FXML public Label descriptionLoadingLabel;
    @FXML public TableColumn<Appointment, String> typeSectionMonthCol;
    @FXML public TableColumn<Appointment, String> typeSectionTypeCol;
    @FXML public TableColumn<Appointment, Long> typeSectionCountCol;
    @FXML public TableView<Appointment> typesTbl;
    @FXML public ComboBox<String> descriptionSectionMonthCombo;
    @FXML public ComboBox<String> descriptionSectionCombo;
    @FXML public TableColumn<Appointment, String> descriptionSectionMonthCol;
    @FXML public TableColumn<Appointment, String> descriptionSectionCol;
    @FXML public TableColumn<Appointment, Long> descriptionSectionCountCol;
    @FXML public TableView<Appointment> descriptionTbl;
    @FXML public TableView<Appointment> apptTbl;
    @FXML public TableColumn<Appointment, Long> apptIdCol;
    @FXML public TableColumn<Appointment, String> apptTitleCol;
    @FXML public TableColumn<Appointment, String> apptDescCol;
    @FXML public TableColumn<Appointment, String> apptLocationCol;
    @FXML public TableColumn<Appointment, Long> apptContactCol;
    @FXML public TableColumn<Appointment, String> apptTypeCol;
    @FXML public TableColumn<Appointment, LocalDateTime> apptStartCol;
    @FXML public TableColumn<Appointment, LocalDateTime> apptEndCol;
    @FXML public TableColumn<Appointment, Long> apptCustomerIdCol;
    @FXML public TableColumn<Appointment, Long> apptUserIdCol;
    @FXML public ComboBox<Contact> apptContactCombo;

    private ObservableList<Appointment> appointments = AppointmentDAO.getAllAppts();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        handleTypeSection();
        handleTypeSectionCombos();
        handleApptByContact();
        handleApptByContactCombo();
        handleDescriptionSection();
        handleDescriptionSectionCombos();
    }

    /**
     * Navigates the user to the Preview View.
     * @param event
     */
    @FXML
    private void backBtn(ActionEvent event) {
        try {
            NavigationHelper.home(event);
        } catch (IOException e) {
            ModalHelper.displayAlert(Alert.AlertType.ERROR, "Error Navigating To Main View");
        }
    }

    private void handleTypeSection() {
        typeSectionMonthCol.setCellValueFactory(value -> {
            final Appointment appointment = value.getValue();
            return new ReadOnlyObjectWrapper<>(uppercaseFirstLetter(appointment.getStart().getMonth().toString()));
        });
        typeSectionTypeCol.setCellValueFactory(value -> {
            final Appointment appointment = value.getValue();
            return new ReadOnlyObjectWrapper<>(uppercaseFirstLetter(appointment.getType()));
        });
        typeSectionCountCol.setCellValueFactory(value -> {
            final Appointment appointment = value.getValue();
            final String type = appointment.getType();
            final String monthString = typeSectionMonthCombo.getSelectionModel().getSelectedItem();
            long count = countAppointmentsByTypeAndMonth(type, monthString);
            return new ReadOnlyObjectWrapper<>(count);
        });
    }

    private void handleTypeSectionCombos() {
        final ObservableList<String> months = Arrays.stream(Month.values())
                .map(uppercaseFirstLetter())
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        typeSectionMonthCombo.setItems(months);

        final ObservableList<String> types = getAppointmentsDistinctTypes();
        typeSectionTypeCombo.setItems(types);
    }

    private long countAppointmentsByTypeAndMonth(String type, String monthString) {
        if (monthString != null) {
            final Month month = Month.valueOf(monthString.toUpperCase());
            return appointments.stream()
                    .filter(a -> month.equals(a.getStart().getMonth()) && type.equalsIgnoreCase(a.getType()))
                    .count();
        } else {
            return appointments.stream()
                    .filter(a -> type.equalsIgnoreCase(a.getType()))
                    .count();
        }
    }

    private ObservableList<String> getAppointmentsDistinctTypes() {
        return appointments.stream()
                .map(Appointment::getType)
                .map(uppercaseFirstLetter())
                .distinct()
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
    }

    /**
     * Generates the Type By Fields.
     */
    @FXML
    public void typeSectionGenerate() {
        final String selectedMonth = typeSectionMonthCombo.getSelectionModel().getSelectedItem();
        final String selectedType = typeSectionTypeCombo.getSelectionModel().getSelectedItem();

        final Predicate<Appointment> filterByMonth = appointment -> {
            if (selectedMonth != null) {
                return appointment.getStart().getMonth().toString().equalsIgnoreCase(selectedMonth);
            }
            return true;
        };

        final Predicate<Appointment> filterByType = appointment -> {
            if (selectedType != null) {
                return appointment.getType().equalsIgnoreCase(selectedType);
            }
            return true;
        };

        final ObservableList<Appointment> filteredAppointments = appointments.stream()
                .filter(filterByMonth)
                .filter(filterByType)
                .distinct()
                .collect(Collectors.toCollection(FXCollections::observableArrayList));

        if (filteredAppointments.isEmpty()) {
            typeCountLoadingLabel.setText("No Results");
        } else {
            typeCountLoadingLabel.setText("");
        }

        typesTbl.setItems(filteredAppointments);
    }


    /**
     * Resets the Type Section.
     */
    @FXML
    public void typeClear() {
        typesTbl.setItems(FXCollections.emptyObservableList());
        typeCountLoadingLabel.setText("");
        resetComboBox(typeSectionMonthCombo, "Month");
        resetComboBox(typeSectionTypeCombo, "Type");
    }

    private <T> void resetComboBox(ComboBox<T> comboBox, String promptText) {
        comboBox.setPromptText(promptText);
        comboBox.getSelectionModel().clearSelection();
        comboBox.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(promptText);
                } else {
                    setText(item.toString());
                }
            }
        });
    }


    private void handleApptByContact() {
        apptIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        apptTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        apptDescCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        apptLocationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        apptTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        apptStartCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        apptEndCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        apptCustomerIdCol.setCellValueFactory(value -> new ReadOnlyObjectWrapper<>(value.getValue().getCustomer().getCustomerID()));
        apptUserIdCol.setCellValueFactory(value -> new ReadOnlyObjectWrapper<>(value.getValue().getUser().getUserID()));
        apptContactCol.setCellValueFactory(value -> new ReadOnlyObjectWrapper<>(value.getValue().getContact().getContactID()));
    }


    private void handleApptByContactCombo() {
        ObservableList<Contact> contacts = appointments.stream()
                .map(Appointment::getContact)
                .distinct()
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        apptContactCombo.setItems(contacts);
        apptContactCombo.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                final ObservableList<Appointment> filteredAppts = filterAppointmentsByContact(newValue);
                if (filteredAppts.isEmpty()) {
                    contactLoadingLabel.setText("No Results");
                } else {
                    contactLoadingLabel.setText("");
                }
                apptTbl.setItems(filteredAppts);
            }
        });
    }

    private ObservableList<Appointment> filterAppointmentsByContact(Contact contact) {
        return appointments.stream()
                .filter(appointment -> appointment.getContact().getContactID() == contact.getContactID())
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
    }

    /**
     * Clears the Customer Appt Section.
     */
    @FXML
    public void contactApptClear() {
        resetContactApptUI();
        apptTbl.setItems(FXCollections.emptyObservableList());
    }

    private void resetContactApptUI() {
        contactLoadingLabel.setText("");
        resetApptContactCombo();
    }

    private void resetApptContactCombo() {
        apptContactCombo.setPromptText("Contact");
        apptContactCombo.getSelectionModel().clearSelection();
        apptContactCombo.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Contact item, boolean empty) {
                super.updateItem(item, empty);
                setText("Contact");
            }
        });
    }



    /**
     * Generates Location Section By Fields.
     */
    @FXML
    public void descriptionSectionGenerate() {
        final String selectedMonth = descriptionSectionMonthCombo.getSelectionModel().getSelectedItem();
        final String selectedType = descriptionSectionCombo.getSelectionModel().getSelectedItem();

        final ObservableList<Appointment> filteredAppointments = appointments.stream()
                .filter(appointment -> selectedMonth == null || appointment.getStart().getMonth().toString().equalsIgnoreCase(selectedMonth))
                .filter(appointment -> selectedType == null || appointment.getDescription().equalsIgnoreCase(selectedType))
                .distinct()
                .collect(Collectors.toCollection(FXCollections::observableArrayList));

        if (filteredAppointments.isEmpty()) {
            descriptionLoadingLabel.setText("No Results");
        } else {
            descriptionLoadingLabel.setText("");
        }

        descriptionTbl.setItems(filteredAppointments);
    }

    private void handleDescriptionSection() {
        descriptionSectionMonthCol.setCellValueFactory(value -> {
            final Appointment appointment = value.getValue();
            return new ReadOnlyObjectWrapper<>(uppercaseFirstLetter(appointment.getStart().getMonth().toString()));
        });

        descriptionSectionCol.setCellValueFactory(value -> {
            final Appointment appointment = value.getValue();
            return new ReadOnlyObjectWrapper<>(uppercaseFirstLetter(appointment.getDescription()));
        });

        descriptionSectionCountCol.setCellValueFactory(value -> {
            final Appointment appointment = value.getValue();
            final String description = appointment.getDescription();
            final String monthString = descriptionSectionMonthCombo.getSelectionModel().getSelectedItem();
            long count = countAppointmentsByDescriptionAndMonth(description, monthString);
            return new ReadOnlyObjectWrapper<>(count);
        });
    }

    private void handleDescriptionSectionCombos() {
        final ObservableList<String> months = Arrays.stream(Month.values())
                .map(uppercaseFirstLetter())
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        descriptionSectionMonthCombo.setItems(months);
        descriptionSectionMonthCombo.getSelectionModel().clearSelection();

        final ObservableList<String> descriptions = getAppointmentsDistinctDescriptions();
        descriptionSectionCombo.setItems(descriptions);
    }

    private long countAppointmentsByDescriptionAndMonth(String description, String monthString) {
        if (monthString != null) {
            final Month month = Month.valueOf(monthString.toUpperCase());
            return appointments.stream()
                    .filter(a -> month.equals(a.getStart().getMonth()) && description.equalsIgnoreCase(a.getDescription()))
                    .count();
        } else {
            return appointments.stream()
                    .filter(a -> description.equalsIgnoreCase(a.getDescription()))
                    .count();
        }
    }

    private ObservableList<String> getAppointmentsDistinctDescriptions() {
        return appointments.stream()
                .map(appointment -> uppercaseFirstLetter(appointment.getDescription()))
                .distinct()
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
    }


    /**
     * Clears the Fields.
     */
    @FXML
    public void descriptionClear() {
        resetDescriptionUI();
        descriptionTbl.setItems(FXCollections.emptyObservableList());
    }

    private void resetDescriptionUI() {
        descriptionLoadingLabel.setText("");
        resetDescriptionSectionMonthCombo();
        resetDescriptionSectionCombo();
    }

    private void resetDescriptionSectionMonthCombo() {
        descriptionSectionMonthCombo.setPromptText("Month");
        descriptionSectionMonthCombo.getSelectionModel().clearSelection();
        descriptionSectionMonthCombo.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText("Month");
            }
        });
    }

    private void resetDescriptionSectionCombo() {
        descriptionSectionCombo.setPromptText("Location");
        descriptionSectionCombo.getSelectionModel().clearSelection();
        descriptionSectionCombo.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText("Location");
            }
        });
    }

    private String uppercaseFirstLetter(String s) {
        return s.substring(0, 1).toUpperCase() + s.toLowerCase().substring(1);
    }

    private <T> Function<T, String> uppercaseFirstLetter() {
        return t -> {
            final String s = t.toString();
            return s.substring(0, 1).toUpperCase() + s.toLowerCase().substring(1);
        };
    }

    public <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

}