package exceptions;

// Custom exception when student already applied to a drive
public class AlreadyAppliedException extends Exception {

    public AlreadyAppliedException(String message) {
        super(message);
    }
}
