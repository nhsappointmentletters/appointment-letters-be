package uk.co.nhs.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import uk.co.nhs.api.model.Email;

import javax.mail.internet.MimeMessage;

@Service
@Slf4j
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;


    public void sendEmail(Email email) {
        try {
            MimeMessage mail = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mail, true);
            helper.setSubject(email.getSubject());
            helper.setText(email.getBody());
//            helper.setText(email.getBody(), email.getBody());
            helper.setTo(email.getTo());
            mailSender.send(mail);
        } catch (Exception e) {
            log.error("E-mail could not be sent to user " + email.getTo() + ", exception : " + e.getMessage());
        }
    }
}
