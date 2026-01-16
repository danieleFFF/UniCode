package it.unical.unicode.service;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.security.SecureRandom;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class PasswordRecoverService {

    private final JavaMailSender mailSender;
    private final String senderEmail;
    private final PasswordEncoder passwordEncoder;
    private final SecureRandom random = new SecureRandom();
    private final Map<String, ResetCodeEntry> resetCodes = new ConcurrentHashMap<>();
    private static final long RESET_CODE_TTL_MS = 190 * 1000;

    public PasswordRecoverService(
        JavaMailSender mailSender,
        @Value("${app.mail.from}") String senderEmail,
        PasswordEncoder passwordEncoder
    ) {
        this.mailSender = mailSender;
        this.senderEmail = senderEmail;
        this.passwordEncoder = passwordEncoder;
    }

    public void sendPasswordRecoverEmail(String recipientEmail) {
        try {
            int verificationCode = 100000 + random.nextInt(900000);
            String codeHash = passwordEncoder.encode(String.valueOf(verificationCode));
            resetCodes.put(recipientEmail, new ResetCodeEntry(codeHash,
                System.currentTimeMillis() + RESET_CODE_TTL_MS));

            String subject = "Your UniCode Password Reset Code";
            String body = String.format("""
                Hello,
                
                We received a request to reset your UniCode account password.
                
                Your verification code is: %d
                
                If you did not request this, please ignore this message.
                
                Thank you,
                The UniCode Team""", verificationCode);

            MimeMessage message = mailSender.createMimeMessage();
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
            message.setSubject(subject, "UTF-8");
            message.setText(body, "UTF-8");
            mailSender.send(message);

            System.out.println("EmailService: Email sent successfully to " + recipientEmail);
        }
        catch (MessagingException e) {
            System.err.println("EmailService: Failed to send email: " + e.getMessage());
            throw new RuntimeException("Failed to send password recovery email", e);
        }
    }

    public boolean validateResetCode(String recipientEmail, String secretCode) {
        if (recipientEmail == null || secretCode == null || secretCode.isBlank()) {
            return false;
        }

        String normalizedCode = secretCode.trim();
        ResetCodeEntry entry = resetCodes.get(recipientEmail);
        if (entry == null) {
            return false;
        }

        if (System.currentTimeMillis() > entry.expiresAtMillis()) {
            resetCodes.remove(recipientEmail);
            return false;
        }

        if (!passwordEncoder.matches(normalizedCode, entry.codeHash())) {
            return false;
        }

        resetCodes.remove(recipientEmail);
        return true;
    }

    private record ResetCodeEntry(String codeHash, long expiresAtMillis) {}
}
