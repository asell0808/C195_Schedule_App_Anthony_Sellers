package c195.dao;

import c195.model.User;
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
public class UserDAO {
    private static User currentUser;

    public static User getCurrentUser() {
        return currentUser;
    }

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Gets the User Using User ID.
     * @param userID long
     * @return User
     */
    public static User getUser(long userID) {
        String getUserQuery = "SELECT * FROM users WHERE User_ID = ?";
        User user = null;

        try {
            PreparedStatement statement = SQLDBService.getConnection().prepareStatement(getUserQuery);
            statement.setLong(1, userID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                user = new User();
                user.setUserID(userID);
                user.setUsername(resultSet.getString("User_Name"));
                user.setPassword(resultSet.getString("Password"));

                String createDateString = resultSet.getString("Create_Date");
                if (createDateString != null) {
                    LocalDateTime createDate = LocalDateTime.parse(createDateString, formatter);
                    user.setCreateDate(createDate);
                }

                user.setCreatedBy(resultSet.getString("Created_By"));

                String lastUpdateString = resultSet.getString("Last_Update");
                if (lastUpdateString != null) {
                    LocalDateTime lastUpdate = LocalDateTime.parse(lastUpdateString, formatter);
                    user.setLastUpdated(lastUpdate);
                }

                user.setLastUpdatedBy(resultSet.getString("Last_Updated_By"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return user;
    }


    /**
     * Gets All Users from the DB.
     * @return Observable List Of Users.
     */
    public static ObservableList<User> getAllUsers() {
        ObservableList<User> users = FXCollections.observableArrayList();
        String getAllUsersQuery = "SELECT * FROM users";
        try (Statement statement = SQLDBService.getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery(getAllUsersQuery);
            while (resultSet.next()) {
                User user = pullUserFromResultSet(resultSet);
                users.add(user);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return users;
    }


    /**
     *
     * It is a constructor.
     *
     * @param resultSet  the result set.
     * @throws   SQLException
     */
    public static User pullUserFromResultSet(ResultSet resultSet) throws SQLException {

        User user = new User();
        user.setUserID(resultSet.getLong("User_ID"));
        user.setUsername(resultSet.getString("User_Name"));
        user.setPassword(resultSet.getString("Password"));

        String createDateString = resultSet.getString("Create_Date");
        if (createDateString != null) {
            LocalDateTime createDate = LocalDateTime.parse(createDateString, formatter);
            user.setCreateDate(createDate);
        }

        user.setCreatedBy(resultSet.getString("Created_By"));

        String lastUpdateString = resultSet.getString("Last_Update");
        if (lastUpdateString != null) {
            LocalDateTime lastUpdate = LocalDateTime.parse(lastUpdateString, formatter);
            user.setLastUpdated(lastUpdate);
        }

        user.setLastUpdatedBy(resultSet.getString("Last_Updated_By"));

        return user;
    }



    /**
     * Login Using Username and Password.
     * @param username String
     * @param password String
     * @return Successful
     */
    public static Boolean login(String username, String password) {
        try {
            Statement statement = SQLDBService.getConnection().createStatement();
            String loginQuery = "SELECT * FROM users WHERE User_Name='" + username + "' AND Password='" + password + "'";
            ResultSet resultSet = statement.executeQuery(loginQuery);
            if (resultSet.next()) {
                currentUser = pullUserFromResultSet(resultSet);
                statement.close();
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("An error occurred during login: " + e.getMessage());
            return false;
        }
    }
}