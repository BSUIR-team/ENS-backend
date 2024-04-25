package by.bsuir.senderserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;

    public void send(String to, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@google.com");
        message.setTo(to);
        message.setSubject("EMERGENCY NOTIFICATION");
        message.setText(content);
        mailSender.send(message);
    }
}
