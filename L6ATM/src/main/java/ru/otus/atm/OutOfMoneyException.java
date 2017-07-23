package ru.otus.atm;

public class OutOfMoneyException extends Exception {
    public OutOfMoneyException(){
        super("слишком много! желай меньшего!");
    }
}
