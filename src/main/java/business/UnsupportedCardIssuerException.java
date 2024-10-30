package business;

public class UnsupportedCardIssuerException extends RuntimeException {
    public UnsupportedCardIssuerException(String message) {
        super(message);
    }
}
