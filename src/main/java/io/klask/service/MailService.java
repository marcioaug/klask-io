package io.klask.service;

import io.klask.domain.User;
import io.klask.service.util.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import javax.inject.Inject;
import java.util.Locale;

/**
 * Service for sending e-mails.
 * <p>
 * We use the @Async annotation to send e-mails asynchronously.
 * </p>
 */
@Service
public class MailService {

    private static final String USER = "user";
    private static final String BASE_URL = "baseUrl";
    private final Logger log = LoggerFactory.getLogger(MailService.class);

    @Inject
    private MessageSource messageSource;

    @Inject
    private SpringTemplateEngine templateEngine;


    @Async
    public void sendActivationEmail(User user, String baseUrl) {
        log.debug("Sending activation e-mail to '{}'", user.getEmail());
    }

    @Async
    public Message createActivationMessage(User user, String baseUrl) {
        log.debug("Sending activation e-mail to '{}'", user.getEmail());

        System.out.println("Chamou!");
        String content = getContentTemplate(user, baseUrl,"activationEmail");
        String subject = getSubject(user, "email.activation.title");

        return new Message(user, subject, content);
    }

    @Async
    public Message createCreationMessage(User user, String baseUrl) {
        log.debug("Sending creation e-mail to '{}'", user.getEmail());

        String content = getContentTemplate(user, baseUrl, "creationEmail");
        String subject = getSubject(user, "email.activation.title");

        return new Message(user, subject, content);
    }

    @Async
    public Message createPasswordResetMessage(User user, String baseUrl) {
        log.debug("Sending password reset e-mail to '{}'", user.getEmail());

        String content = getContentTemplate(user, baseUrl, "passwordResetEmail");
        String subject = getSubject(user, "email.reset.title");

        return new Message(user, subject, content);
    }

    private String getContentTemplate(User user, String baseUrl, String templateName) {
        Locale locale = Locale.forLanguageTag(user.getLangKey());
        Context context = new Context(locale);
        context.setVariable(USER, user);
        context.setVariable(BASE_URL, baseUrl);
        return templateEngine.process(templateName, context);
    }

    private String getSubject(User user, String key) {
        Locale locale = Locale.forLanguageTag(user.getLangKey());
        return messageSource.getMessage(key, null, locale);
    }

}
