package io.klask.aop.mail;

import io.klask.config.JHipsterProperties;
import io.klask.service.util.Message;
import org.apache.commons.lang.CharEncoding;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.mail.internet.MimeMessage;

@Component
@Aspect
public class MailAspect {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Inject
    private JHipsterProperties jHipsterProperties;

    @Inject
    private JavaMailSenderImpl javaMailSender;

    @Around("execution(* io.klask.service.MailService.create*(..))")
    public Object mainAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Message message = (Message) joinPoint.proceed();

        sendEmail(message.getEmail(), message.getSubject(), message.getContent(), false, true);

        return message;
    }

    @Async
    public void sendEmail(String to, String subject, String content, boolean isMultipart, boolean isHtml) {
        log.debug("Send e-mail[multipart '{}' and html '{}'] to '{}' with subject '{}' and content={}",
            isMultipart, isHtml, to, subject, content);

        // Prepare message using a Spring helper
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, isMultipart, CharEncoding.UTF_8);
            message.setTo(to);
            message.setFrom(jHipsterProperties.getMail().getFrom());
            message.setSubject(subject);
            message.setText(content, isHtml);
            System.out.println("======================================= ENVIANDO EMAIL =======================================");
            System.out.println(content);
            System.out.println("==============================================================================================");
            javaMailSender.send(mimeMessage);
            log.debug("Sent e-mail to User '{}'", to);
        } catch (Exception e) {
            log.warn("E-mail could not be sent to user '{}', exception is: {}", to, e.getMessage());
        }
    }
}


