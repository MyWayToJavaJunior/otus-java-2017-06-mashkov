package ru.otus.app;

import ru.otus.atm.*;

import java.io.InputStreamReader;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ATM atm = new ATM();
        atm.addMoney(CELL.CELL_100, 20);
        atm.addMoney(CELL.CELL_500, 10);
        atm.addMoney(CELL.CELL_1000, 10);

        atm.setStrategy(new ExchangeStrategy());
        System.out.println("Баланс "+atm.getBalance());
        Scanner scanner = new Scanner(new InputStreamReader(System.in));
        while (true){
            System.out.println("Хотите немного денег? Введите сумму или exit");
            String msg = scanner.nextLine();
            if (msg.equals("exit")) return;
            long summ;
            try {
                summ = Integer.parseInt(msg);
            } catch (NumberFormatException e){
                System.out.println("Желательно цифры!");
                continue;
            }

            try {
                atm.getMoney(summ);
            } catch (OutOfMoneyException | OutOfExchangeException e) {
                System.out.println(e.getMessage());
            }
            System.out.println("Баланс "+atm.getBalance());
        }

    }


}
