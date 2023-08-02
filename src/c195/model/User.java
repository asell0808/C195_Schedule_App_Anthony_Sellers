package c195.model;

import java.time.LocalDateTime;

/**
 * @author Anthony Sellers
 */
public class User {
    private long userID;
    private String username;
    private String password;
    private LocalDateTime createDate;
    private String createdBy;
    private LocalDateTime lastUpdated;
    private String lastUpdatedBy;

    /**
     * Getters and Setters
     */
    public long getUserID() {

        return userID;
    }


    /**
     *
     * Sets the user identifier
     *
     * @param userID  the user identifier.
     */
    public void setUserID(long userID) {

        this.userID = userID;
    }


    /**
     *
     * Gets the username
     *
     * @return the username
     */
    public String getUsername() {

        return username;
    }


    /**
     *
     * Sets the username
     *
     * @param username  the username.
     */
    public void setUsername(String username) {

        this.username = username;
    }


    /**
     *
     * Gets the password
     *
     * @return the password
     */
    public String getPassword() {

        return password;
    }


    /**
     *
     * Sets the password
     *
     * @param password  the password.
     */
    public void setPassword(String password) {

        this.password = password;
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
     * Gets the last updated
     *
     * @return the last updated
     */
    public LocalDateTime getLastUpdated() {

        return lastUpdated;
    }


    /**
     *
     * Sets the last updated
     *
     * @param lastUpdated  the last updated.
     */
    public void setLastUpdated(LocalDateTime lastUpdated) {

        this.lastUpdated = lastUpdated;
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
 * To string
 *
 * @return String
 */
    public String toString() {

        return "User{" +
                "userID=" + userID +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", createDate=" + createDate +
                ", createdBy='" + createdBy + '\'' +
                ", lastUpdated=" + lastUpdated +
                ", lastUpdatedBy='" + lastUpdatedBy + '\'' +
                '}';
    }
}
