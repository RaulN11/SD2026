package com.rauln.CarKet.services;

import com.rauln.CarKet.model.AdCreatedEvent;
import com.rauln.CarKet.model.AdDeletedEvent;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


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
            helper.setText("The ad for "+ event.getBrand()+ " " +event.getModel()+" was posted, for the price of "+ event.getPrice()+"$!");
            emailSender.send(mailMessage);
        } catch (Exception e) {
            System.out.println("Error sending the email: " + e.getMessage());
        }
    }

    @EventListener
    public void sendUponAdDeleted(AdDeletedEvent event){
        try {
            MimeMessage mailMessage = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mailMessage,"utf-8");
            helper.setFrom("raulnicula2004@gmail.com",  "CarKet No-Reply");
            helper.setTo(event.getUserEmail());
            helper.setSubject("Your ad was successfully deleted!");
            helper.setText("The ad for "+event.getBrand()+" "+event.getModel()+" has been deleted!");
            emailSender.send(mailMessage);
        }catch (Exception e){
            System.out.println("Error sending the email: " + e.getMessage());
        }

    }
}
