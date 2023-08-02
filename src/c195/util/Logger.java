package c195.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * @author Anthony Sellers
 */
public class Logger {
    private static final String FILENAME = "login_activity.txt";
    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");
    public Logger() {}

    public static void log(String username, Boolean success, String message) {

        final String date = LocalDateTime.ofInstant(Instant.from(Instant.now().atZone(ZoneOffset.UTC)), ZoneId.of("UTC"))
                .toString().replace("T", " ");
        try (FileWriter fileWriter = new FileWriter(FILENAME, true);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
             PrintWriter printWriter = new PrintWriter(bufferedWriter)) {
            printWriter.println(username + (success ? " Successful" : " Failed") + " " + message + " " + date) ;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}