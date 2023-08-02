package c195.model;

import java.util.Objects;

/**
 * @author Anthony Sellers
 */
public class Contact {
    private long contactID;
    private String contactName;
    private String email;


    /**
     *
     * Gets the contact identifier
     *
     * @return the contact identifier
     */
    public long getContactID() {

        return contactID;
    }


    /**
     *
     * Sets the contact identifier
     *
     * @param contactID  the contact identifier.
     */
    public void setContactID(long contactID) {

        this.contactID = contactID;
    }


    /**
     *
     * Gets the contact name
     *
     * @return the contact name
     */
    public String getContactName() {

        return contactName;
    }


    /**
     *
     * Sets the contact name
     *
     * @param contactName  the contact name.
     */
    public void setContactName(String contactName) {

        this.contactName = contactName;
    }


    /**
     *
     * Gets the email
     *
     * @return the email
     */
    public String getEmail() {

        return email;
    }


    /**
     *
     * Sets the email
     *
     * @param email  the email.
     */
    public void setEmail(String email) {

        this.email = email;
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
        Contact contact = (Contact) o;
        return contactID == contact.contactID && Objects.equals(contactName, contact.contactName) && Objects.equals(email, contact.email);
    }

    @Override

/**
 *
 * Hash code
 *
 * @return int
 */
    public int hashCode() {

        return Objects.hash(contactID, contactName, email);
    }

    @Override

/**
 *
 * To string
 *
 * @return String
 */
    public String toString() {

        return contactName;
    }
}