package com.rauln.CarKet.services;

import com.rauln.CarKet.model.AdCreatedEvent;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public class NotificationService {
    @Autowired
    private JavaMailSender emailSender;
    @EventListener
    public void sendUponAdCreated(AdCreatedEvent event){
        try {
            MimeMessage mailMessage = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mailMessage, "utf-8");
            helper.setFrom("raulnicula2004@gmail.com", "CarKet No-Reply");
            helper.setTo(event.getUserEmail());
            helper.setSubject("Your ad was successfully published!");
            helper.setText("The ad for "+ event.getCarBrand()+ " " +event.getCarModel()+" was posted, for the price of "+ event.getPrice()+"$!");
            emailSender.send(mailMessage);
        } catch (Exception e) {
            System.out.println("Eroare la trimiterea emailului: " + e.getMessage());
        }
    }
}
