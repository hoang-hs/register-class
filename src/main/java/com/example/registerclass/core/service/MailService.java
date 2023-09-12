package com.example.registerclass.core.service;

import com.example.registerclass.core.domain.Registration;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender javaMailSender;

    public void sendNotify(Registration registration) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(registration.getStudent().getEmail());
        message.setSubject("confirm register class");
        String msg = String.format("student:%s, register class: %s success", registration.getStudent().getName(), registration.getCourse().getName());
        message.setText(msg);
        javaMailSender.send(message);
    }


}
