package ru.otus.atm;

import java.util.Arrays;

public class ATM {
    private Strategy strategy;

    public ATM(){
        Arrays.asList(CELL.values()).forEach(c->c.setEndCallback(endSumm -> {
            //Проверяем всё ли мы сможем выдать, если да то просим ячейки отдать деньги
            //тут можно поменять стратегию динамически
            if (endSumm>0) throw new OutOfExchangeException();
            else giveMoney();
        }));
    }

    public void addMoney(CELL cell, int count){
        cell.addMoney(count);
    }

    public void setStrategy(Strategy strategy){
        this.strategy = strategy;
    }


    public long getBalance(){
        return Arrays.stream(CELL.values()).map(v->v.getCount()*v.getNominal()).reduce((v1, v2)->v1+v2).get();
    }

    public void getMoney(long summ) throws OutOfMoneyException, OutOfExchangeException {
        if (summ>getBalance()) {
            throw new OutOfMoneyException();
        }
        CELL firstCell = strategy.apply(CELL.values(), summ);
        firstCell.getMoney(summ);
    }

    private void giveMoney(){
        Arrays.asList(CELL.values()).forEach(c->{
            c.giveMoney();
            if (c.neededCount==0) return;
            System.out.println("Выдал "+c.neededCount+" купюр, номиналом "+c.nominal);

        });
    }

}
