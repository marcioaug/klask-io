package io.klask.aop.mail;

public class DefaultMessageCounter implements MessageCounter {

    private Long counter = 0L;

    @Override
    public void incrementCounter() {
        this.counter++;
        System.out.println("=============================================================================================");
        System.out.println("CONTADOR DE MENSAGENS: " + this.counter);
        System.out.println("=============================================================================================");

    }
}
