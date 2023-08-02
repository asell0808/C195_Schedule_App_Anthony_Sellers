package c195.dao;

import c195.model.Customer;
import c195.model.FirstLevelDivision;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Anthony Sellers
 */
public class CustomerDAO {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Adds Customer To the DB.
     * @param customer
     * @return boolean on success.
     */
    public static boolean addCustomer(Customer customer) {
        String customerCreateQuery = " INSERT INTO customers ( Customer_Name,Address,Postal_Code,Phone,Create_Date, Created_By,Last_Update,Last_Updated_By,Division_ID) VALUES ( ?,?,?,?,now(),?,now(),?, ?);";

        try(PreparedStatement statement = SQLDBService.getConnection().prepareStatement(customerCreateQuery)) {
            statement.setString(1, customer.getCustomerName());
            statement.setString(2, customer.getAddress());
            statement.setString(3, customer.getPostalCode());
            statement.setString(4, customer.getPhone());
            statement.setString(5, customer.getLastUpdatedBy());
            statement.setString(6, UserDAO.getCurrentUser().getUsername());
            statement.setLong(7, customer.getDivision().getDivisionID());
            statement.executeUpdate();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    /**
     * Updates Customer Using New Customer.
     * @param customer Customer
     * @return boolean on success.
     */
    public static boolean updateCustomer(Customer customer) {
        String customerUpdateQuery = " UPDATE customers SET Customer_Name=?, Address=?, Postal_Code=?, Phone=?, Last_Update=now(), Last_Updated_By=?, Division_ID=? WHERE Customer_ID=?";

        try {
            PreparedStatement statement = SQLDBService.getConnection().prepareStatement(customerUpdateQuery);
            statement.setString(1, customer.getCustomerName());
            statement.setString(2, customer.getAddress());
            statement.setString(3, customer.getPostalCode());
            statement.setString(4, customer.getPhone());
            statement.setString(5, customer.getLastUpdatedBy());
            statement.setLong(6, customer.getDivision().getDivisionID());
            statement.setLong(7, customer.getCustomerID());
            int executionResults = statement.executeUpdate();
            return executionResults == 1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    /**
     * Gets Customer Using Customer ID.
     * @param customerID long
     * @return Customer
     */
    public static Customer getCustomer(long customerID) {
        String getCustomerQuery = "SELECT * FROM customers WHERE Customer_ID = ?";
        Customer fetchedCustomer = new Customer();

        try {
            PreparedStatement statement = SQLDBService.getConnection().prepareStatement(getCustomerQuery);
            statement.setLong(1, customerID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                fetchedCustomer.setCustomerID(customerID);
                fetchedCustomer.setCustomerName(resultSet.getString("Customer_Name"));
                fetchedCustomer.setAddress(resultSet.getString("Address"));
                fetchedCustomer.setPostalCode(resultSet.getString("Postal_Code"));
                fetchedCustomer.setPhone(resultSet.getString("Phone"));
                String createDateString = resultSet.getString("Create_Date");
                LocalDateTime createDate = LocalDateTime.parse(createDateString, formatter);
                fetchedCustomer.setCreateDate(createDate);
                fetchedCustomer.setCreatedBy(resultSet.getString("Created_By"));
                String lastUpdateString = resultSet.getString("Last_Update");
                LocalDateTime lastUpdate = LocalDateTime.parse(lastUpdateString, formatter);
                fetchedCustomer.setLastUpdate(lastUpdate);
                fetchedCustomer.setLastUpdatedBy(resultSet.getString("Last_Updated_By"));
            } else {
                return null;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return fetchedCustomer;
    }

    /**
     * Removes Customer Using Customer Object.
     * @param customer Customer
     * @return boolean on success.
     */
    public static boolean removeCustomer(Customer customer) {
        String deletionQuery = "DELETE FROM customers WHERE Customer_ID = ?";
        try {
            PreparedStatement statement = SQLDBService.getConnection().prepareStatement(deletionQuery);
            statement.setLong(1, customer.getCustomerID());
            statement.executeUpdate();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    /**
     * Gets All Customers from teh DB.
     * @return Observable List of Customers.
     */
    public static ObservableList<Customer> getAllCustomers() {
        ObservableList<Customer> customers = FXCollections.observableArrayList();
        String getAllUsersQuery = "SELECT * FROM customers AS cus JOIN first_level_divisions AS fld ON cus.Division_ID = fld.Division_ID JOIN countries AS ctry ON ctry.Country_ID = fld.Country_ID";
        try (Statement statement = SQLDBService.getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery(getAllUsersQuery);
            while (resultSet.next()) {
                Customer customer = pullCustomerFromResultSet(resultSet);
                customers.add(customer);

                FirstLevelDivision firstLevelDivision = FirstLevelDivisionDAO.pullDivisionFromResultSet(resultSet);
                customer.setDivision(firstLevelDivision);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return customers;
    }

    public static Customer pullCustomerFromResultSet(ResultSet resultSet) throws SQLException {
        Customer customer = new Customer();
        long customerID = resultSet.getLong("Customer_ID");
        String name = resultSet.getString("Customer_Name");
        String address = resultSet.getString("Address");
        String postalCode = resultSet.getString("Postal_Code");
        String phone = resultSet.getString("Phone");
        String createDateString = resultSet.getString("Create_Date");
        String createdBy = resultSet.getString("Created_By");
        LocalDateTime createDate = LocalDateTime.parse(createDateString, formatter);
        String lastUpdateString = resultSet.getString("Last_Update");
        String lastUpdatedBy = resultSet.getString("Last_Updated_By");
        LocalDateTime lateUpdate = LocalDateTime.parse(lastUpdateString, formatter);

        customer.setCustomerID(customerID);
        customer.setCustomerName(name);
        customer.setAddress(address);
        customer.setPostalCode(postalCode);
        customer.setPhone(phone);
        customer.setCreateDate(createDate);
        customer.setCreatedBy(createdBy);
        customer.setLastUpdate(lateUpdate);
        customer.setCreatedBy(lastUpdatedBy);

        return customer;
    }
}