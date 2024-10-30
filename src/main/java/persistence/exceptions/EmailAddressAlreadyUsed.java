package persistence.exceptions;

/**
 * @author Filip Vojtěch
 */
public class EmailAddressAlreadyUsed extends RuntimeException {
    public EmailAddressAlreadyUsed(String message) {
        super(message);
    }
}
