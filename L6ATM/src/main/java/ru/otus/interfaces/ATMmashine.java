package ru.otus.interfaces;

import ru.otus.atm.OutOfExchangeException;
import ru.otus.atm.OutOfMoneyException;

public interface ATMmashine {
    long getBalance();
    void setDefaultMoney();
    void getMoney(long summ) throws OutOfMoneyException, OutOfExchangeException;
}
