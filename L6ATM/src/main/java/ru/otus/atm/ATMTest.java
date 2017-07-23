package ru.otus.atm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ATMTest {
    private ATM atm;

    @BeforeEach
    void setup(){
        atm = new ATM();
        atm.addMoney(CELL.CELL_100, 20);
        atm.addMoney(CELL.CELL_500, 10);
        atm.addMoney(CELL.CELL_1000, 10);
        atm.setStrategy(new ExchangeStrategy());
    }

    @Test
    void getBalance() {
        assertEquals(17000, atm.getBalance());
        try {
            atm.getMoney(7000);
        } catch (OutOfMoneyException | OutOfExchangeException e) {
            e.printStackTrace();
        }
        assertEquals(10000, atm.getBalance());
    }

    @Test
    void outOfMoneyTest() {
        Throwable throwable = assertThrows(OutOfMoneyException.class, ()->{
            atm.getMoney(atm.getBalance()+100);
        });
        assertEquals("слишком много! желай меньшего!", throwable.getMessage());
    }

    @Test
    void outOfExchangeTest() {
        Throwable throwable = assertThrows(OutOfExchangeException.class, ()->{
            atm.getMoney(125);
        });
        assertEquals("Мелочи нет! Попробуй округли, или поменяй стратегию выдачи", throwable.getMessage());
    }

}