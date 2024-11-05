package hms.notification;

public class EmailNotificationService implements NotificationService {

public void sendNotification(String recipient, String message) {
   System.out.println("Sending an email to: " + recipient);
   System.out.println("Message: " + message);
   System.out.println("Email sent successfully.");
}
}
