package io.klask.service.util;

import io.klask.domain.User;

public class Message {

    private String subject;
    private String content;
    private User recipient;

    public Message(User recipient, String subject, String content) {
        this.subject = subject;
        this.content = content;
        this.recipient = recipient;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSubject() {
        return this.subject;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return this.content;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }

    public User getRecipient() {
        return this.recipient;
    }

    public String getEmail() {
        return this.recipient.getEmail();
    }

    public String getPhone() {
        return "+5582999999999";
    }

    public String getText() {
        return "Dear " + this.recipient.getFirstName() + ", " +
            "Your klask account has been created, please use this URL to activate it: " +
            "http://localhost:8080/#/activate?key=" + this.recipient.getActivationKey() + ". " +
            ">Regards, klask Team.";
    }

}
