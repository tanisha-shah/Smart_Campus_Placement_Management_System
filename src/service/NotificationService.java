package service;

import model.Notification;
import utils.FileHelper;
import utils.UIHelper;

import java.util.ArrayList;

// NotificationService manages sending and reading notifications
public class NotificationService {

    // Send a notification to a user
    public void sendNotification(String userId, String message, String type) {
        String notifId = UIHelper.generateId("NOTIF");
        String today = UIHelper.getTodayDate();
        Notification notif = new Notification(notifId, userId, message, type, today);
        FileHelper.writeLineToFile(FileHelper.NOTIFICATIONS_FILE, notif.toFileString());
    }

    // Send notification to ALL students (e.g., new drive posted)
    public void sendNotificationToAllStudents(ArrayList<String> studentIds,
                                               String message, String type) {
        for (int i = 0; i < studentIds.size(); i++) {
            sendNotification(studentIds.get(i), message, type);
        }
    }

    // Get all notifications for a specific user
    public ArrayList<Notification> getNotificationsForUser(String userId) {
        ArrayList<Notification> result = new ArrayList<Notification>();
        ArrayList<String> lines = FileHelper.readAllLines(FileHelper.NOTIFICATIONS_FILE);

        for (int i = 0; i < lines.size(); i++) {
            Notification notif = parseNotificationFromLine(lines.get(i));
            if (notif != null && notif.getUserId().equals(userId)) {
                result.add(notif);
            }
        }
        return result;
    }

    // Count unread notifications for a user
    public int getUnreadCount(String userId) {
        ArrayList<Notification> notifs = getNotificationsForUser(userId);
        int count = 0;
        for (int i = 0; i < notifs.size(); i++) {
            if (!notifs.get(i).isRead()) {
                count++;
            }
        }
        return count;
    }

    // Mark all notifications as read for a user
    public void markAllAsRead(String userId) {
        ArrayList<String> lines = FileHelper.readAllLines(FileHelper.NOTIFICATIONS_FILE);
        ArrayList<String> updatedLines = new ArrayList<String>();

        for (int i = 0; i < lines.size(); i++) {
            Notification notif = parseNotificationFromLine(lines.get(i));
            if (notif != null && notif.getUserId().equals(userId) && !notif.isRead()) {
                notif.setRead(true);
                updatedLines.add(notif.toFileString());
            } else {
                updatedLines.add(lines.get(i));
            }
        }
        FileHelper.writeAllLines(FileHelper.NOTIFICATIONS_FILE, updatedLines);
    }

    // Parse a Notification from CSV line
    private Notification parseNotificationFromLine(String line) {
        try {
            String[] parts = line.split(",");
            // Format: notifId,userId,message,type,date,isRead
            if (parts.length < 6) return null;

            String message = parts[2].replace("|", ","); // Restore commas in message
            Notification notif = new Notification(parts[0], parts[1], message,
                    parts[3], parts[4]);
            notif.setRead(Boolean.parseBoolean(parts[5]));
            return notif;
        } catch (Exception e) {
            return null;
        }
    }
}
