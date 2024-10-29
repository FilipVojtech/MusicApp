package persistence;

public class EmailAddressAlreadyUsed extends RuntimeException {
    public EmailAddressAlreadyUsed(String message) {
        super(message);
    }
}
