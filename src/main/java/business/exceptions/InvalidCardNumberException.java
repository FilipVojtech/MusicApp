package business.exceptions;

/**
 * @author Filip Vojtěch
 */
public class InvalidCardNumberException extends RuntimeException {
    public InvalidCardNumberException(String message) {
        super(message);
    }
}
