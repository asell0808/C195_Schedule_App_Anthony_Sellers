package c195.model;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author Anthony Sellers
 */
public class Country {
    private long countryID;
    private String country;
    private LocalDateTime createDate;
    private String createBy;
    private LocalDateTime lastUpdate;
    private String lastUpdatedBy;


    /**
     *
     * Gets the country identifier
     *
     * @return the country identifier
     */
    public long getCountryID() {

        return countryID;
    }


    /**
     *
     * Sets the country identifier
     *
     * @param countryID  the country identifier.
     */
    public void setCountryID(long countryID) {

        this.countryID = countryID;
    }


    /**
     *
     * Gets the country
     *
     * @return the country
     */
    public String getCountry() {

        return country;
    }


    /**
     *
     * Sets the country
     *
     * @param country  the country.
     */
    public void setCountry(String country) {

        this.country = country;
    }


    /**
     *
     * Gets the create date
     *
     * @return the create date
     */
    public LocalDateTime getCreateDate() {

        return createDate;
    }


    /**
     *
     * Sets the create date
     *
     * @param createDate  the create date.
     */
    public void setCreateDate(LocalDateTime createDate) {

        this.createDate = createDate;
    }


    /**
     *
     * Gets the create by
     *
     * @return the create by
     */
    public String getCreateBy() {

        return createBy;
    }


    /**
     *
     * Sets the create by
     *
     * @param createBy  the create by.
     */
    public void setCreateBy(String createBy) {

        this.createBy = createBy;
    }


    /**
     *
     * Gets the last update
     *
     * @return the last update
     */
    public LocalDateTime getLastUpdate() {

        return lastUpdate;
    }


    /**
     *
     * Sets the last update
     *
     * @param lastUpdate  the last update.
     */
    public void setLastUpdate(LocalDateTime lastUpdate) {

        this.lastUpdate = lastUpdate;
    }


    /**
     *
     * Gets the last updated by
     *
     * @return the last updated by
     */
    public String getLastUpdatedBy() {

        return lastUpdatedBy;
    }


    /**
     *
     * Sets the last updated by
     *
     * @param lastUpdatedBy  the last updated by.
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {

        this.lastUpdatedBy = lastUpdatedBy;
    }

    @Override

/**
 *
 * Equals
 *
 * @param o  the o.
 * @return boolean
 */
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Country country1 = (Country) o;
        return countryID == country1.countryID && Objects.equals(country, country1.country) && Objects.equals(createDate, country1.createDate) && Objects.equals(createBy, country1.createBy) && Objects.equals(lastUpdate, country1.lastUpdate) && Objects.equals(lastUpdatedBy, country1.lastUpdatedBy);
    }

    @Override

/**
 *
 * Hash code
 *
 * @return int
 */
    public int hashCode() {

        return Objects.hash(countryID, country, createDate, createBy, lastUpdate, lastUpdatedBy);
    }

    @Override

/**
 *
 * To string
 *
 * @return String
 */
    public String toString() {

        return country;
    }
}
