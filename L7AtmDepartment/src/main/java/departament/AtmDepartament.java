package departament;

import com.sun.xml.internal.ws.server.UnsupportedMediaException;
import ru.otus.atm.OutOfExchangeException;
import ru.otus.atm.OutOfMoneyException;
import ru.otus.interfaces.ATMmashine;

import java.util.ArrayList;

public class AtmDepartament implements ATMmashine {
    private ArrayList<ATMmashine> atMmashines;

    public AtmDepartament(){
        atMmashines = new ArrayList<ATMmashine>();
    }

    public void addATM(ATMmashine atm){
        atMmashines.add(atm);
    }

    public long getBalance() {
        return atMmashines.stream().map(ATMmashine::getBalance).reduce((b1, b2)->b1+b2).get();
    }

    public void setDefaultMoney() {
        atMmashines.forEach(ATMmashine::setDefaultMoney);
    }

    public void getMoney(long summ) throws OutOfMoneyException, OutOfExchangeException {
        throw new UnsupportedMediaException("Метод не работает, обратитесь в банкомат");
    }
}
