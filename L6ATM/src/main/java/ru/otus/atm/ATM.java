package ru.otus.atm;

import java.util.Arrays;
import java.util.Optional;

public class ATM {

    public void addMoney(int count100, int count500, int count1000){
        VALUES.VALUE_100.addBanknote(count100);
        VALUES.VALUE_500.addBanknote(count500);
        VALUES.VALUE_1000.addBanknote(count1000);
    }

    public long getBalance(){
        return Arrays.asList(VALUES.values()).stream().map(v->v.getCount()*v.getValue()).reduce((v1, v2)->v1+v2).get();

    }

    public int[] getMoney(int summ){
        int count1000 = summ%1000;
        int count500 = (summ-(count1000*1000))%500;
        int count100 = (summ - (count500*500+count1000*1000));

        return new int[]{count100, count500, count1000};
    }

    private enum VALUES{
        VALUE_100(100),VALUE_500(500), VALUE_1000(1000);

        int value;
        int count;

        VALUES(int value){
            this.value = value;
            this.count = 0;
        }

        public int getCount() {
            return count;
        }

        public void addBanknote(int count) {
            this.count += count;
        }

        public void getBanknote(int count) throws Exception {
            if (count > getCount()) throw new BanknoteExeption("You want to much!");
            this.count -=count;
        }

        public int getValue() {
            return value;
        }

        private static class BanknoteExeption extends Exception{
            BanknoteExeption(String msg){
                super(msg);
            }
        }
    }


    public static void main(String[] args) {
        ATM atm = new ATM();
        atm.addMoney(10, 10, 10);
        //System.out.println(atm.getBalance());
        //System.out.println(Arrays.toString(atm.getMoney(3500)));
        System.out.println(atm.getBalance());
    }




}
