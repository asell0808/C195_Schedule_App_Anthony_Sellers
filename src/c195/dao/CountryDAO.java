package c195.dao;

import c195.model.Country;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Anthony Sellers
 */
public class CountryDAO {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Loads Customers by their Country ID from the DB.
     * @param countryID long
     * @return Country
     */
    public static Country getCustomerByID(long countryID) {
        String countryFetchQuery = "SELECT * FROM countries WHERE CountryID = ?";
        Country country = null;

        try (Connection connection = SQLDBService.getConnection();
             PreparedStatement statement = connection.prepareStatement(countryFetchQuery)) {

            statement.setLong(1, countryID);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    country = new Country();

                    country.setCountryID(resultSet.getLong("Country_ID"));
                    country.setCountry(resultSet.getString("Country"));

                    String createDateString = resultSet.getString("Create_Date");
                    LocalDateTime createDate = null;
                    if (createDateString != null) {
                        createDate = LocalDateTime.parse(createDateString, formatter);
                    }
                    country.setCreateDate(createDate);
                    country.setCreateBy(resultSet.getString("Created_By"));

                    String lastUpdatedString = resultSet.getString("Last_Update");
                    LocalDateTime lastUpdate = null;
                    if (lastUpdatedString != null) {
                        lastUpdate = LocalDateTime.parse(lastUpdatedString, formatter);
                    }
                    country.setLastUpdate(lastUpdate);
                    country.setLastUpdatedBy(resultSet.getString("Last_Updated_By"));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return country;
    }


    /**
     * Gets All Countries from the DB.
     * @return Observable List of Countries.
     */
    public static ObservableList<Country> getAllCountries() {
        String getAllCountriesQuery = "SELECT * FROM countries";
        ObservableList<Country> countries = FXCollections.observableArrayList();
        try (Statement statement = SQLDBService.getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery(getAllCountriesQuery);
            while (resultSet.next()) {
                Country country = new Country();
                country.setCountryID(resultSet.getLong("Country_ID"));
                String countryName = resultSet.getString("Country");
                if (!resultSet.wasNull()) {
                    country.setCountry(countryName);
                }

                String createDateString = resultSet.getString("Create_Date");
                LocalDateTime createDate = null;
                if (createDateString != null) {
                    createDate = LocalDateTime.parse(createDateString, formatter);
                }
                country.setCreateDate(createDate);
                country.setCreateBy(resultSet.getString("Created_By"));

                String lastUpdatedString = resultSet.getString("Last_Update");
                LocalDateTime lastUpdate = null;
                if (lastUpdatedString != null) {
                    lastUpdate = LocalDateTime.parse(lastUpdatedString, formatter);
                }
                country.setLastUpdate(lastUpdate);
                country.setLastUpdatedBy(resultSet.getString("Last_Updated_By"));

                countries.add(country);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return countries;
    }


    public static Country pullCustomerFromResultSet(ResultSet resultSet) throws SQLException {
        Country country = new Country();
        country.setCountryID(resultSet.getLong("Country_ID"));
        String countryName = resultSet.getString("Country");
        if (!resultSet.wasNull()) {
            country.setCountry(countryName);
        }

        String createDateString = resultSet.getString("Create_Date");
        LocalDateTime createDate = null;
        if (createDateString != null) {
            createDate = LocalDateTime.parse(createDateString, formatter);
        }
        country.setCreateDate(createDate);
        country.setCreateBy(resultSet.getString("Created_By"));

        String lastUpdatedString = resultSet.getString("Last_Update");
        LocalDateTime lastUpdate = null;
        if (lastUpdatedString != null) {
            lastUpdate = LocalDateTime.parse(lastUpdatedString, formatter);
        }
        country.setLastUpdate(lastUpdate);
        country.setLastUpdatedBy(resultSet.getString("Last_Updated_By"));

        return country;
    }

}