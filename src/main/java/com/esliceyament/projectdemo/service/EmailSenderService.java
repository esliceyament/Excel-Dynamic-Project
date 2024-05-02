package com.esliceyament.projectdemo.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class EmailSenderService {
    private final JavaMailSender javaMailSender;

    public void sendEmail(String toEmail, String subject, String body, MultipartFile file) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom("@gmail.com");
        helper.setTo(toEmail);
        helper.setText(body);
        helper.setSubject(subject);

        if (file != null) {
            helper.addAttachment(file.getOriginalFilename(), file);
        }

        javaMailSender.send(mimeMessage);
    }
}
