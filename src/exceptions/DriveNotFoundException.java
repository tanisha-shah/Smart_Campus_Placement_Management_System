package exceptions;

// Custom exception when a drive is not found
public class DriveNotFoundException extends Exception {

    public DriveNotFoundException(String message) {
        super(message);
    }
}
