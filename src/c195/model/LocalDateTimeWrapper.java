package c195.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Anthony Sellers
 */
public class LocalDateTimeWrapper implements Comparable<LocalDateTime> {

    private final LocalDateTime localDateTime;

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a");


    /**
     *
     * Local date time wrapper
     *
     * @param localDateTime  the local date time.
     * @return public
     */
    public LocalDateTimeWrapper(LocalDateTime localDateTime) {

        this.localDateTime = localDateTime;
    }

    @Override

/**
 *
 * Compare to
 *
 * @param o  the o.
 * @return int
 */
    public int compareTo(LocalDateTime o) {

        return localDateTime.compareTo(o);
    }

    @Override

/**
 *
 * To string
 *
 * @return String
 */
    public String toString() {

        return localDateTime.format(dateTimeFormatter);
    }
}