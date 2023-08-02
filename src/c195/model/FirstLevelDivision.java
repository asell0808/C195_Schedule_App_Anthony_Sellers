package c195.model;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author Anthony Sellers
 */
public class FirstLevelDivision {
    private long divisionID;
    private String division;
    private LocalDateTime createDate;
    private String createdByDate;
    private LocalDateTime lastUpdate;
    private String lastUpdatedBy;
    private Country country;


    /**
     *
     * Gets the division identifier
     *
     * @return the division identifier
     */
    public long getDivisionID() {

        return divisionID;
    }


    /**
     *
     * Sets the division identifier
     *
     * @param divisionID  the division identifier.
     */
    public void setDivisionID(long divisionID) {

        this.divisionID = divisionID;
    }


    /**
     *
     * Gets the division
     *
     * @return the division
     */
    public String getDivision() {

        return division;
    }


    /**
     *
     * Sets the division
     *
     * @param division  the division.
     */
    public void setDivision(String division) {

        this.division = division;
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
     * Gets the created by date
     *
     * @return the created by date
     */
    public String getCreatedByDate() {

        return createdByDate;
    }


    /**
     *
     * Sets the created by date
     *
     * @param createdByDate  the created by date.
     */
    public void setCreatedByDate(String createdByDate) {

        this.createdByDate = createdByDate;
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


    /**
     *
     * Gets the country
     *
     * @return the country
     */
    public Country getCountry() {

        return country;
    }


    /**
     *
     * Sets the country
     *
     * @param country  the country.
     */
    public void setCountry(Country country) {

        this.country = country;
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
        FirstLevelDivision that = (FirstLevelDivision) o;
        return divisionID == that.divisionID && Objects.equals(division, that.division) && Objects.equals(createDate, that.createDate) && Objects.equals(createdByDate, that.createdByDate) && Objects.equals(lastUpdate, that.lastUpdate) && Objects.equals(lastUpdatedBy, that.lastUpdatedBy) && Objects.equals(country, that.country);
    }

    @Override

/**
 *
 * Hash code
 *
 * @return int
 */
    public int hashCode() {

        return Objects.hash(divisionID, division, createDate, createdByDate, lastUpdate, lastUpdatedBy, country);
    }

    @Override

/**
 *
 * To string
 *
 * @return String
 */
    public String toString() {

        return division;
    }
}
