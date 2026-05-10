package cars.microservice.NotifiactionsMicroservice.services;

import cars.microservice.NotifiactionsMicroservice.configs.RabbitMqConfig;
import cars.microservice.NotifiactionsMicroservice.events.AdCreatedEvent;
import cars.microservice.NotifiactionsMicroservice.events.AdDeletedEvent;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final JavaMailSender emailSender;

    @RabbitListener(queues = RabbitMqConfig.AD_CREATED_QUEUE)
    public void sendUponAdCreated(AdCreatedEvent event) {
        try {
            MimeMessage mailMessage = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mailMessage, "utf-8");
            helper.setFrom("raulnicula2004@gmail.com", "CarKet No-Reply");
            helper.setTo(event.getUserEmail());
            helper.setSubject("Your ad was successfully published!");
            helper.setText("The ad for " + event.getBrand() + " " + event.getModel()
                    + " was posted, for the price of " + event.getPrice() + "$!");
            emailSender.send(mailMessage);
        } catch (Exception e) {
            System.out.println("Error sending email: " + e.getMessage());
        }
    }

    @RabbitListener(queues = RabbitMqConfig.AD_DELETED_QUEUE)
    public void sendUponAdDeleted(AdDeletedEvent event) {
        try {
            MimeMessage mailMessage = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mailMessage, "utf-8");
            helper.setFrom("raulnicula2004@gmail.com", "CarKet No-Reply");
            helper.setTo(event.getUserEmail());
            helper.setSubject("Your ad was successfully deleted!");
            helper.setText("The ad for " + event.getBrand() + " " + event.getModel()
                    + " has been deleted!");
            emailSender.send(mailMessage);
        } catch (Exception e) {
            System.out.println("Error sending email: " + e.getMessage());
        }
    }
}