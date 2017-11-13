package io.klask.aop.mail;

import io.klask.service.util.Message;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class SMSAspect {

    @Around("execution(* io.klask.service.MailService.create*(..))")
    public Object mainAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Message message = (Message) joinPoint.proceed();

        sendSMS(message.getPhone(), message.getText());

        return message;
    }

    @Async
    public void sendSMS(String to, String content) {
        System.out.println("======================================== ENVIANDO SMS ========================================");
        System.out.println(content);
        System.out.println("==============================================================================================");
    }
}
