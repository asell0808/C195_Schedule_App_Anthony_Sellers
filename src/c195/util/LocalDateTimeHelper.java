package c195.util;

import c195.model.Appointment;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Anthony Sellers
 */
public class LocalDateTimeHelper {
    public static DateTimeFormatter formatter12Hr = DateTimeFormatter.ofPattern("hh");
    public static DateTimeFormatter formatter12Min = DateTimeFormatter.ofPattern("mm");
    public static DateTimeFormatter formatter12AMPM = DateTimeFormatter.ofPattern("a");
    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy hh:mm a z");

    /**
     * Gets Hour from LocalDateTime using 12 Hour.
     * @param localDateTime LocalDateTime
     * @return Hour String
     */
    public static String get12Hr(LocalDateTime localDateTime) {
        final ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());
        return zonedDateTime.format(formatter12Hr);
    }

    /**
     * Gets Minute from LocalDateTime using 12 Hour.
     * @param localDateTime LocalDateTime
     * @return Minute String
     */
    public static String get12Min(LocalDateTime localDateTime) {
        final ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());
        return zonedDateTime.format(formatter12Min);
    }

    /**
     * Gets AMPM from LocalDateTime using 12 Hour.
     * @param localDateTime LocalDateTime
     * @return AMPM String
     */
    public static String get12AMPM(LocalDateTime localDateTime) {
        final ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());
        return zonedDateTime.format(formatter12AMPM);
    }

    public static TableCell<Appointment, LocalDateTime> dateTimeCell(TableColumn<Appointment, LocalDateTime> column) {
        return new TableCell<>() {
            @Override
            protected void updateItem(LocalDateTime localDateTime, boolean b) {
                super.updateItem(localDateTime, b);
                if (b) {
                    setText(null);
                } else {
                    final ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());
                    setText(LocalDateTimeHelper.formatter.format(zonedDateTime));
                }
            }
        };
    }
}