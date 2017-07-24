package ru.otus.atm;

import java.util.ArrayList;

public class ATM {
    private Strategy strategy;
    private ArrayList<AtmCell> atmCells;

    public ATM(){
        atmCells = new ArrayList<>();
    }

    Callback callback = endSumm -> {
        //Проверяем всё ли мы сможем выдать, если да то просим ячейки отдать деньги
        //тут можно поменять стратегию динамически
        if (endSumm>0) throw new OutOfExchangeException();
        else giveMoney();
    };


    public void addMoney(CELL_TYPE cellTYPE, int count){
        AtmCell atmCell = atmCells.stream()
                .filter(cell1 -> cell1.getType().equals(cellTYPE))
                .findFirst().orElseGet(() -> {
            AtmCell c = new AtmCell(cellTYPE);
            c.setEndCallback(callback);
            atmCells.add(c);
            return c;
        });
        atmCell.addMoney(count);
    }

    public void setStrategy(Strategy strategy){
        this.strategy = strategy;
    }


    public long getBalance(){
        return atmCells.stream().map(v->v.getCount()*v.getNominal()).reduce((v1, v2)->v1+v2).get();
    }

    public void getMoney(long summ) throws OutOfMoneyException, OutOfExchangeException {
        if (summ>getBalance()) {
            throw new OutOfMoneyException();
        }
        AtmCell firstAtmCell = strategy.apply(atmCells, summ);
        firstAtmCell.getMoney(summ);
    }

    private void giveMoney(){
        atmCells.forEach(c->{
            c.giveMoney();
            if (c.getNeededCount()==0) return;
            System.out.println("Выдал "+c.getNeededCount()+" купюр, номиналом "+c.getNominal());

        });
    }


}
