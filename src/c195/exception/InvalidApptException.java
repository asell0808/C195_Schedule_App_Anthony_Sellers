package c195.exception;

/**
 * @author Anthony Sellers
 */
public class InvalidApptException extends Exception {
    public InvalidApptException(String prompt) {
        super(prompt);
    }
}
