import departament.AtmDepartment;
import ru.otus.atm.ATM;

public class Main {
    public static void main(String[] args) {
        AtmDepartment departament = new AtmDepartment();
        for (int i = 0; i < 3; i++) {
            departament.addATM(new ATM());
        }
        departament.setDefaultMoney();
        System.out.println(departament.getBalance(3).orElse(null));
    }
}
