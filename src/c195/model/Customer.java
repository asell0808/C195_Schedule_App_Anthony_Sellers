package c195.model;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author Anthony Sellers
 */
public class Customer {
    private long customerID;
    private String customerName;
    private String address;
    private String postalCode;
    private String phone;
    private LocalDateTime createDate;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdatedBy;
    private FirstLevelDivision division;


    /**
     *
     * Gets the customer identifier
     *
     * @return the customer identifier
     */
    public long getCustomerID() {

        return customerID;
    }


    /**
     *
     * Sets the customer identifier
     *
     * @param customerID  the customer identifier.
     */
    public void setCustomerID(long customerID) {

        this.customerID = customerID;
    }


    /**
     *
     * Gets the customer name
     *
     * @return the customer name
     */
    public String getCustomerName() {

        return customerName;
    }


    /**
     *
     * Sets the customer name
     *
     * @param customerName  the customer name.
     */
    public void setCustomerName(String customerName) {

        this.customerName = customerName;
    }


    /**
     *
     * Gets the address
     *
     * @return the address
     */
    public String getAddress() {

        return address;
    }


    /**
     *
     * Sets the address
     *
     * @param address  the address.
     */
    public void setAddress(String address) {

        this.address = address;
    }


    /**
     *
     * Gets the postal code
     *
     * @return the postal code
     */
    public String getPostalCode() {

        return postalCode;
    }


    /**
     *
     * Sets the postal code
     *
     * @param postalCode  the postal code.
     */
    public void setPostalCode(String postalCode) {

        this.postalCode = postalCode;
    }


    /**
     *
     * Gets the phone
     *
     * @return the phone
     */
    public String getPhone() {

        return phone;
    }


    /**
     *
     * Sets the phone
     *
     * @param phone  the phone.
     */
    public void setPhone(String phone) {

        this.phone = phone;
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
     * Gets the created by
     *
     * @return the created by
     */
    public String getCreatedBy() {

        return createdBy;
    }


    /**
     *
     * Sets the created by
     *
     * @param createdBy  the created by.
     */
    public void setCreatedBy(String createdBy) {

        this.createdBy = createdBy;
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
     * Gets the division
     *
     * @return the division
     */
    public FirstLevelDivision getDivision() {

        return division;
    }


    /**
     *
     * Sets the division
     *
     * @param division  the division.
     */
    public void setDivision(FirstLevelDivision division) {

        this.division = division;
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
        Customer customer = (Customer) o;
        return customerID == customer.customerID && Objects.equals(customerName, customer.customerName) && Objects.equals(address, customer.address) && Objects.equals(postalCode, customer.postalCode) && Objects.equals(phone, customer.phone) && Objects.equals(createDate, customer.createDate) && Objects.equals(createdBy, customer.createdBy) && Objects.equals(lastUpdate, customer.lastUpdate) && Objects.equals(lastUpdatedBy, customer.lastUpdatedBy) && Objects.equals(division, customer.division);
    }

    @Override

/**
 *
 * Hash code
 *
 * @return int
 */
    public int hashCode() {

        return Objects.hash(customerID, customerName, address, postalCode, phone, createDate, createdBy, lastUpdate, lastUpdatedBy, division);
    }

    @Override

/**
 *
 * To string
 *
 * @return String
 */
    public String toString() {

        return customerName;
    }
}
