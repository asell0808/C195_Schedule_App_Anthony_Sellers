package c195.model;

import c195.dao.AppointmentDAO;
import c195.exception.InvalidApptException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

/**
 * @author Anthony Sellers
 */
public class Appointment {
    private long appointmentID;
    private String title;
    private String description;
    private String location;
    private String type;
    private LocalDateTime start;
    private LocalDateTime end;
    private LocalDateTime createDate;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdatedBy;
    private Customer customer;
    private User user;
    private Contact contact;


    /**
     *
     * Gets the appointment identifier
     *
     * @return the appointment identifier
     */
    public long getAppointmentID() {

        return appointmentID;
    }


    /**
     *
     * Sets the appointment identifier
     *
     * @param appointmentID  the appointment identifier.
     */
    public void setAppointmentID(long appointmentID) {

        this.appointmentID = appointmentID;
    }


    /**
     *
     * Gets the title
     *
     * @return the title
     */
    public String getTitle() {

        return title;
    }


    /**
     *
     * Sets the title
     *
     * @param title  the title.
     */
    public void setTitle(String title) {

        this.title = title;
    }


    /**
     *
     * Gets the description
     *
     * @return the description
     */
    public String getDescription() {

        return description;
    }


    /**
     *
     * Sets the description
     *
     * @param description  the description.
     */
    public void setDescription(String description) {

        this.description = description;
    }


    /**
     *
     * Gets the location
     *
     * @return the location
     */
    public String getLocation() {

        return location;
    }


    /**
     *
     * Sets the location
     *
     * @param location  the location.
     */
    public void setLocation(String location) {

        this.location = location;
    }


    /**
     *
     * Gets the type
     *
     * @return the type
     */
    public String getType() {

        return type;
    }


    /**
     *
     * Sets the type
     *
     * @param type  the type.
     */
    public void setType(String type) {

        this.type = type;
    }


    /**
     *
     * Gets the start
     *
     * @return the start
     */
    public LocalDateTime getStart() {

        return start;
    }


    /**
     *
     * Sets the start
     *
     * @param start  the start.
     */
    public void setStart(LocalDateTime start) {

        this.start = start;
    }


    /**
     *
     * Gets the end
     *
     * @return the end
     */
    public LocalDateTime getEnd() {

        return end;
    }


    /**
     *
     * Sets the end
     *
     * @param end  the end.
     */
    public void setEnd(LocalDateTime end) {

        this.end = end;
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
     * Gets the customer
     *
     * @return the customer
     */
    public Customer getCustomer() {

        return customer;
    }


    /**
     *
     * Sets the customer
     *
     * @param customer  the customer.
     */
    public void setCustomer(Customer customer) {

        this.customer = customer;
    }


    /**
     *
     * Gets the user
     *
     * @return the user
     */
    public User getUser() {

        return user;
    }


    /**
     *
     * Sets the user
     *
     * @param user  the user.
     */
    public void setUser(User user) {

        this.user = user;
    }


    /**
     *
     * Gets the contact
     *
     * @return the contact
     */
    public Contact getContact() {

        return contact;
    }


    /**
     *
     * Sets the contact
     *
     * @param contact  the contact.
     */
    public void setContact(Contact contact) {

        this.contact = contact;
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
        Appointment that = (Appointment) o;
        return appointmentID == that.appointmentID && Objects.equals(title, that.title) && Objects.equals(description, that.description) && Objects.equals(location, that.location) && Objects.equals(type, that.type) && Objects.equals(start, that.start) && Objects.equals(end, that.end) && Objects.equals(createDate, that.createDate) && Objects.equals(createdBy, that.createdBy) && Objects.equals(lastUpdate, that.lastUpdate) && Objects.equals(lastUpdatedBy, that.lastUpdatedBy) && Objects.equals(customer, that.customer) && Objects.equals(user, that.user) && Objects.equals(contact, that.contact);
    }

    @Override

/**
 *
 * Hash code
 *
 * @return int
 */
    public int hashCode() {

        return Objects.hash(appointmentID, title, description, location, type, start, end, createDate, createdBy, lastUpdate, lastUpdatedBy, customer, user, contact);
    }


    /**
     *
     * Validate
     *
     * @param {  the {.  It is throws
     * @return boolean
     * @throws   InvalidApptException
     */
    public boolean validate() throws InvalidApptException {


        if (title == null || title.isEmpty()) {
            throw new InvalidApptException("This Appoinment is Missing a Title");
        }

        if (description == null || description.isEmpty()) {
            throw new InvalidApptException("This Appointment is Missing a Description");
        }

        if (location == null || location.isEmpty()) {
            throw new InvalidApptException("This Appointment is Missing a Location");
        }

        if (type == null || type.isEmpty()) {
            throw new InvalidApptException("This Appointment is a Type");
        }

        if (customer == null) {
            throw new InvalidApptException("This Appointment is Missing a Customer");
        }

        if (contact == null) {
            throw new InvalidApptException("This Appointment is Missing a Contact");
        }

        if (start == null) {
            throw new InvalidApptException("This Appointment is Missing a Start Time");
        } else if (dayOutsideWorkHours(start)) {
            throw new InvalidApptException("Start Day not within working days(Mon-Fri)");
        } else if (invalidTime(start)) {
            throw new InvalidApptException("Start Time not within working hours");
        }

        if (end == null) {
            throw new InvalidApptException("Missing End Time");
        } else if (dayOutsideWorkHours(end)) {
            throw new InvalidApptException("End Day not within working business days(Mon-Fri)");
        } else if (invalidTime(end)) {
            throw new InvalidApptException("End Time not within working business hours");
        }

        if (end.isBefore(start)) {
            throw new InvalidApptException("End Date is before the Start Date");
        }

        final LocalDateTime now = LocalDateTime.now();

        if (start.isBefore(now)) {
            throw new InvalidApptException("Start Date has already passed, please select a future date.");
        }

        if (end.isBefore(now)) {
            throw new InvalidApptException("End Date has already passed, please select a future date.");
        }

        final long hasOverlappingAppointments = AppointmentDAO.hasOverlappingAppts(start, end, customer.getCustomerID(), getAppointmentID());
        if (hasOverlappingAppointments != -1) {
            throw new InvalidApptException("Appointment " + hasOverlappingAppointments + " is overlapping with another appointment.");
        }
        return true;
    }

    /**
     * Time Boolean check for EST business hours
     * @param targetLocalDateTime
     * @return true
     */
    private boolean invalidTime(LocalDateTime targetLocalDateTime) {

        int year = targetLocalDateTime.getYear();
        int month = targetLocalDateTime.getMonth().getValue();
        int dayOfMonth = targetLocalDateTime.getDayOfMonth();
        SimpleDateFormat estDateFormatter = new SimpleDateFormat("yyyy-M-d h:m a z");
        estDateFormatter.setTimeZone(TimeZone.getTimeZone("EST"));
        Date targetDate = Date.from(targetLocalDateTime.atZone(ZoneId.systemDefault()).toInstant());
        try {
            Date openDate = estDateFormatter.parse(year + "-" + month + "-" + dayOfMonth + " 08:00 AM EST");
            Date closeDate = estDateFormatter.parse(year + "-" + month + "-" + dayOfMonth + " 10:00 PM EST");
            return (targetDate.compareTo(openDate) < 0) || (targetDate.compareTo(closeDate) > 0);
        } catch (ParseException e) {
            return true;
        }

    }

    /**
     * Time Boolean check for Saturday and Sunday
     * @param localDateTime
     */
    private boolean dayOutsideWorkHours(LocalDateTime localDateTime)  {

        final ZonedDateTime currentZonedDateTimeEST = localDateTime.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("America/New_York"));
        return currentZonedDateTimeEST.getDayOfWeek() == DayOfWeek.SATURDAY
                || currentZonedDateTimeEST.getDayOfWeek() == DayOfWeek.SUNDAY;
    }
}
