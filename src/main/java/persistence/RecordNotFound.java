package persistence;

public class RecordNotFound extends RuntimeException {
    public RecordNotFound(String message) {
        super(message);
    }
}
