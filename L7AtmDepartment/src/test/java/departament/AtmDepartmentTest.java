package departament;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.atm.ATM;
import ru.otus.atm.ExchangeStrategy;
import ru.otus.atm.OutOfExchangeException;
import ru.otus.atm.OutOfMoneyException;

import static org.junit.jupiter.api.Assertions.*;

class AtmDepartmentTest {
    AtmDepartment department;

    @BeforeEach
    void setUp(){
        department = new AtmDepartment();
        for (int i = 0; i < 3; i++) {
            department.addATM(new ATM());
        }

        department.setDefaultMoney();
        department.setStrategy(new ExchangeStrategy());
    }

    @Test
    void getBalance() {
        assertEquals(96000, department.getBalance());
    }

    @Test
    void setDefaultMoney() throws OutOfMoneyException, OutOfExchangeException {
        long b1 = department.getBalance();
        long summ = 1700;
        department.getMoney(summ, 2);
        assertEquals(b1-summ, department.getBalance());
    }

    @Test
    void getBalanceFromOneAtm() {
        assertEquals((long) 32000, department.getBalance(1).get());
    }

}