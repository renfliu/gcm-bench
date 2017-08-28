package exceptions;

public class ArgumentException extends Exception {
    public ArgumentException() {
    }

    public ArgumentException(String message) {
        super(message);
    }
}
