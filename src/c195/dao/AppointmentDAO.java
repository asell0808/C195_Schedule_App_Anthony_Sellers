package c195.dao;

import c195.model.Appointment;
import c195.model.Contact;
import c195.model.Customer;
import c195.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
import java.time.LocalDateTime;

/**
 * @author Anthony Sellers
 */
public class AppointmentDAO {

    /**
     * Deletes Appt using the Appointment Object.
     * @param appointment Appointment
     * @return boolean is Appointment was removed.
     */
    public static boolean removeAppt(Appointment appointment) {
        String deletionQuery = "DELETE FROM appointments WHERE Appointment_ID = ?";
        try(PreparedStatement statement = SQLDBService.getConnection().prepareStatement(deletionQuery)) {
            statement.setLong(1, appointment.getAppointmentID());
            statement.executeUpdate();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }


    /**
     * Loads Appts from DB.
     * @return Observable List of Appointments.
     */
    public static ObservableList<Appointment> getAllAppts() {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        String getAllAppointmentsQuery = "SELECT * FROM appointments AS a " +
                "JOIN customers AS c " +
                "ON a.Customer_ID = c.Customer_ID " +
                "JOIN users AS u " +
                "ON a.User_ID = u.User_ID " +
                "JOIN contacts AS cont " +
                "ON a.Contact_ID = cont.Contact_ID";

        try (Statement statement = SQLDBService.getConnection().createStatement()) {
            System.out.println("Executing SQL query: " + getAllAppointmentsQuery);
            ResultSet resultSet = statement.executeQuery(getAllAppointmentsQuery);

            while (resultSet.next()) {
                Appointment appointment = pullApptResultSet(resultSet);
                appointments.add(appointment);
            }

            System.out.println("Retrieved " + appointments.size() + " appointments.");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return appointments;
    }


    /**
     * Finds Appointments that overlap.
     * @param as LocalDateTime
     * @param ae LocalDateTime
     * @param customerId Long
     * @return Returns Appointment ID and -1 is no overlapping appointment
     */
    public static long hasOverlappingAppts(LocalDateTime as, LocalDateTime ae, long customerId, long appointmentId) {
        ObservableList<Appointment> allAppointments = getAllAppts();
        long overlappingAppointmentId = -1;

        System.out.println("Total number of appointments: " + allAppointments.size());

        for (Appointment appointment : allAppointments) {
            if ((appointment.getCustomer().getCustomerID() == customerId) && (appointment.getAppointmentID() != appointmentId)) {
                LocalDateTime nas = appointment.getStart();
                LocalDateTime nae = appointment.getEnd();
                if (nas.isEqual(as) || nae.isEqual(ae)
                        || (nas.isBefore(as) && nae.isAfter(ae))
                        || (nas.isAfter(as) && nas.isBefore(ae))
                        || (nae.isAfter(as) && nae.isBefore(ae))
                        || (nas.isAfter(as) && nae.isBefore(ae))) {
                    overlappingAppointmentId = appointment.getAppointmentID();
                    break;
                }
            }
        }

        System.out.println("Overlapping appointment ID: " + overlappingAppointmentId);
        return overlappingAppointmentId;
    }


    /**
     * Adds Appts to the DB.
     * @param appointment Appointment
     */
    public static void addAppt(Appointment appointment) {
        final String createAppointmentSQLQuery = "INSERT INTO appointments(Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) VALUES (?, ?, ?, ?, ?, ?, NOW(), ?, NOW(), ?, ?, ?, ?)";

        try (PreparedStatement statement = SQLDBService.getConnection().prepareStatement(createAppointmentSQLQuery)) {

            User currentUser = UserDAO.getCurrentUser();
            Object[] values = {
                    appointment.getTitle(),
                    appointment.getDescription(),
                    appointment.getLocation(),
                    appointment.getType(),
                    Timestamp.valueOf(appointment.getStart()),
                    Timestamp.valueOf(appointment.getEnd()),
                    currentUser.getUsername(),
                    currentUser.getUsername(),
                    appointment.getCustomer().getCustomerID(),
                    currentUser.getUserID(),
                    appointment.getContact().getContactID()
            };

            for (int i = 0; i < values.length; i++) {
                statement.setObject(i + 1, values[i]);
            }

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Appointment added successfully.");
            } else {
                System.out.println("Failed to add appointment.");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }



    /**
     * Updates Appts in the DB.
     * @param appointment Appointment
     */
    public static void updateAppt(Appointment appointment) {
        final String updateAppointmentSQLQuery = "UPDATE appointments " +
                "SET Title = ?, " +
                "Description = ?, " +
                "Location = ?, " +
                "Type = ?, " +
                "Start = ?, " +
                "End = ?, " +
                "Last_Update = NOW(), " +
                "Last_Updated_By = ?, " +
                "Customer_ID = ?, " +
                "User_ID = ?, " +
                "Contact_ID = ? " +
                "WHERE Appointment_ID = ?";

        try (PreparedStatement statement = SQLDBService.getConnection().prepareStatement(updateAppointmentSQLQuery)) {

            User currentUser = UserDAO.getCurrentUser();
            Object[] values = {
                    appointment.getTitle(),
                    appointment.getDescription(),
                    appointment.getLocation(),
                    appointment.getType(),
                    Timestamp.valueOf(appointment.getStart()),
                    Timestamp.valueOf(appointment.getEnd()),
                    currentUser.getUsername(),
                    appointment.getCustomer().getCustomerID(),
                    appointment.getUser().getUserID(),
                    appointment.getContact().getContactID(),
                    appointment.getAppointmentID()
            };

            for (int i = 0; i < values.length; i++) {
                statement.setObject(i + 1, values[i]);
            }

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Appointment updated successfully.");
            } else {
                System.out.println("Failed to update appointment.");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    private static Appointment pullApptResultSet(ResultSet resultSet) throws SQLException {

        Appointment appointment = new Appointment();
        try {
            long appointmentID = resultSet.getLong("Appointment_ID");
            String title = resultSet.getString("Title");
            String description = resultSet.getString("Description");
            String location = resultSet.getString("Location");
            String type = resultSet.getString("Type");

            // Handle potential null values or invalid timestamps
            Timestamp startTimestamp = resultSet.getTimestamp("Start");
            LocalDateTime startDate = startTimestamp != null ? startTimestamp.toLocalDateTime() : null;

            Timestamp endTimestamp = resultSet.getTimestamp("End");
            LocalDateTime endDate = endTimestamp != null ? endTimestamp.toLocalDateTime() : null;

            Timestamp createTimestamp = resultSet.getTimestamp("Create_Date");
            LocalDateTime createDate = createTimestamp != null ? createTimestamp.toLocalDateTime() : null;

            String createdBy = resultSet.getString("Created_By");

            Timestamp lastUpdateTimestamp = resultSet.getTimestamp("Last_Update");
            LocalDateTime lastUpdateDate = lastUpdateTimestamp != null ? lastUpdateTimestamp.toLocalDateTime() : null;

            String lastUpdatedBy = resultSet.getString("Last_Updated_By");

            appointment.setAppointmentID(appointmentID);
            appointment.setTitle(title);
            appointment.setDescription(description);
            appointment.setLocation(location);
            appointment.setType(type);
            appointment.setStart(startDate);
            appointment.setEnd(endDate);
            appointment.setCreateDate(createDate);
            appointment.setCreatedBy(createdBy);
            appointment.setLastUpdate(lastUpdateDate);
            appointment.setLastUpdatedBy(lastUpdatedBy);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        System.out.println("Pulling customer data...");
        Customer customer = CustomerDAO.pullCustomerFromResultSet(resultSet);
        appointment.setCustomer(customer);

        System.out.println("Pulling user data...");
        User user = UserDAO.pullUserFromResultSet(resultSet);
        appointment.setUser(user);

        System.out.println("Pulling contact data...");
        Contact contact = ContactDAO.pullContactFromResultSet(resultSet);
        appointment.setContact(contact);

        System.out.println("Appointment data successfully pulled.");
        return appointment;
    }


    /**
     * Loads Apps By the Customer.
     * @param customer Customer
     * @return Observable List of Appointments.
     */
    public static ObservableList<Appointment> getApptByCustomer(Customer customer) {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        String getAllAppointmentsQuery = "SELECT * FROM appointments AS a JOIN customers AS c ON a.Customer_ID = c.Customer_ID JOIN users AS u ON a.User_ID = u.User_ID JOIN contacts AS cont ON a.Contact_ID = cont.Contact_ID WHERE a.Customer_ID = ?";

        try {
            PreparedStatement statement = SQLDBService.getConnection().prepareStatement(getAllAppointmentsQuery);
            statement.setLong(1, customer.getCustomerID());
            System.out.println("Executing query: " + getAllAppointmentsQuery + " with customer ID: " + customer.getCustomerID());
            ResultSet resultSet = statement.executeQuery();
            int count = 0;
            while (resultSet.next()) {
                Appointment appointment = pullApptResultSet(resultSet);
                appointments.add(appointment);
                count++;
            }
            System.out.println("Retrieved " + count + " appointments for customer ID: " + customer.getCustomerID());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return appointments;
    }
}