package com.automation.utils;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.util.Properties;

/**
 * Utility class for sending email reports
 */
public class EmailUtil {
    
    private static LoggerUtil logger = LoggerUtil.getInstance();
    private static ConfigReader configReader = ConfigReader.getInstance();

    /**
     * Send email with attachment
     */
    public static void sendEmailWithReport(String reportPath) {
        String to = configReader.getProperty("email.to");
        String from = configReader.getProperty("email.from");
        String host = configReader.getProperty("email.host");
        String port = configReader.getProperty("email.port");
        String username = configReader.getProperty("email.username");
        String password = configReader.getProperty("email.password");

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject("Automation Test Report");

            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText("Please find attached the test execution report.");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            if (reportPath != null && !reportPath.isEmpty()) {
                MimeBodyPart attachmentPart = new MimeBodyPart();
                attachmentPart.attachFile(new File(reportPath));
                multipart.addBodyPart(attachmentPart);
            }

            message.setContent(multipart);
            Transport.send(message);
            
            logger.info("Email sent successfully to: " + to);
        } catch (Exception e) {
            logger.error("Failed to send email: " + e.getMessage());
        }
    }

    /**
     * Send simple email without attachment
     */
    public static void sendSimpleEmail(String subject, String body) {
        String to = configReader.getProperty("email.to");
        String from = configReader.getProperty("email.from");
        String host = configReader.getProperty("email.host");

        Properties props = System.getProperties();
        props.setProperty("mail.smtp.host", host);

        Session session = Session.getDefaultInstance(props);

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);
            logger.info("Simple email sent successfully");
        } catch (MessagingException e) {
            logger.error("Failed to send simple email: " + e.getMessage());
        }
    }
}
