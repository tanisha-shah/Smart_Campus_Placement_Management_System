package model;

// Notification class to send messages to students
public class Notification {

    private String notificationId;
    private String userId;           // Who receives the notification
    private String message;
    private String type;             // NEW_DRIVE, SHORTLISTED, REJECTED, GENERAL
    private String date;
    private boolean isRead;

    // Constructor
    public Notification(String notificationId, String userId, String message,
                        String type, String date) {
        this.notificationId = notificationId;
        this.userId = userId;
        this.message = message;
        this.type = type;
        this.date = date;
        this.isRead = false;
    }

    // Getters and Setters
    public String getNotificationId() {
        return notificationId;
    }

    public String getUserId() {
        return userId;
    }

    public String getMessage() {
        return message;
    }

    public String getType() {
        return type;
    }

    public String getDate() {
        return date;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    // Print notification
    public void printDetails() {
        String readStatus = isRead ? "[READ]" : "[NEW]";
        System.out.println("  " + readStatus + " [" + type + "] " + date + " - " + message);
    }

    // Convert to file-safe string
    public String toFileString() {
        return notificationId + "," + userId + "," + message.replace(",", "|")
                + "," + type + "," + date + "," + isRead;
    }
}
