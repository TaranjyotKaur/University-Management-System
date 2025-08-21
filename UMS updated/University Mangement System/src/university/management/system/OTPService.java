package university.management.system;

import java.util.Properties;
import java.util.Random;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class OTPService {
    // TODO: Fill with real sender + app password
    private static final String SMTP_USER = "your-email@example.com";
    private static final String SMTP_PASS = "your-app-password"; // use app password, not your real password

    public static String generateOTP() {
        int code = new Random().nextInt(900_000) + 100_000;
        return String.valueOf(code);
    }

    public static void sendOTP(String toEmail, String otp) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(
            props,
            new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(SMTP_USER, SMTP_PASS);
                }
            }
        );

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(SMTP_USER));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
        message.setSubject("Your UMS Login OTP");
        message.setText("Your one-time login code is: " + otp + "\nThis code will expire soon.");

        Transport.send(message);
    }
}
