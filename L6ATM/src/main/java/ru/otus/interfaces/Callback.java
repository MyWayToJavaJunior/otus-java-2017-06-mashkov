package ru.otus.interfaces;

import ru.otus.atm.OutOfExchangeException;

@FunctionalInterface
public interface Callback {
    void call(long endSumm) throws OutOfExchangeException;
}
