package ru.otus.atm;

@FunctionalInterface
public interface Callback {
    void call(long endSumm) throws OutOfExchangeException;
}
