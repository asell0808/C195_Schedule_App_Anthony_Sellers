package c195.util;

import javafx.geometry.Rectangle2D;
import javafx.scene.control.Alert;
import javafx.stage.Screen;

/**
 * @author Anthony Sellers
 */
public class ModalHelper {

    /**
     *
     * @param alertType Type of Alert.
     * @param text Alert Header
     * @return Alert object for further use.
     */
    public static Alert displayAlert(Alert.AlertType alertType, String text) {
        if (alertType != null && text != null) {
            Alert alert = new Alert(alertType);
            alert.setHeaderText(text);
            alert.showAndWait();
            return alert;
        } else {
            // Handle the case where alertType or text is null
            // display an error message or perform alternative actions
            System.out.println("Invalid input for displaying alert.");
            return null;
        }
    }

    public static Alert displayAlert(Alert.AlertType alertType, String header, String content) {
        if (alertType != null && header != null && content != null) {
            Alert alert = new Alert(alertType);
            alert.setHeaderText(header);
            alert.setContentText(content);
            alert.showAndWait();
            return alert;
        } else {
            // Handle the case where alertType, header, or content is null
            // display an error message or perform alternative actions
            System.out.println("Invalid input for displaying alert.");
            return null;
        }
    }

    /**
     *
     * @param alertType Type of Alert.
     * @param title Alert Title
     * @param header Alert Header
     * @param content Alert Content
     * @return Alert object for further use.
     */
    public static Alert displayAlert(Alert.AlertType alertType, String title, String header, String content) {
        if (alertType != null && title != null && header != null && content != null) {
            Alert alert = new Alert(alertType);
            alert.setTitle(title);
            alert.setHeaderText(header);
            alert.setContentText(content);
            alert.showAndWait();
            return alert;
        } else {
            // Handle the case where alertType, title, header, or content is null
            // display an error message or perform alternative actions
            System.out.println("Invalid input for displaying alert.");
            return null;
        }
    }
}
