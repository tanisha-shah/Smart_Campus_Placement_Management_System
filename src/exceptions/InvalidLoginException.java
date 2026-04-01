package exceptions;

// Custom exception for invalid login attempts
public class InvalidLoginException extends Exception {

    public InvalidLoginException(String message) {
        super(message);
    }
}
