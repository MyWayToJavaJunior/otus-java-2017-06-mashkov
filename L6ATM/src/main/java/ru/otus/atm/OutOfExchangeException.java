package ru.otus.atm;

public class OutOfExchangeException extends Exception {
    public OutOfExchangeException(){
        super("Мелочи нет! Попробуй округли, или поменяй стратегию выдачи");
    }
}
