package io.klask.aop.mail;


import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.DeclareParents;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class MessageCounterAspect {

    @DeclareParents(value="io.klask.service.MailService+",
        defaultImpl=DefaultMessageCounter.class)
    public static MessageCounter mixin;

    @Before("execution(* io.klask.service.MailService.create*(..)) && this(messageCounter)")
    public void counter(MessageCounter messageCounter) throws Throwable {
        messageCounter.incrementCounter();
    }
}
