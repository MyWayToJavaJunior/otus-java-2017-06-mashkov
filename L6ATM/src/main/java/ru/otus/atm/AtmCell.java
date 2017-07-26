package ru.otus.atm;

import ru.otus.interfaces.Callback;

public class AtmCell {
    private CELL_TYPE cell_type;
    private int count;
    private AtmCell next;
    private Callback endCallback;
    //количество купюр к выдаче
    private int neededCount;
    //максимальное количество купюр для выдачи
    private int maxCountForGive;

    public AtmCell(CELL_TYPE type){
        this.cell_type = type;
        this.count = 0;
        next = null;
    }

    public int getCount() {
        return count;
    }

    public void addMoney(int count) {
        this.count += count;
        maxCountForGive = this.count;
    }

    /**
     *
     * @param summ сумма для выдачи
     * @throws OutOfExchangeException
     */
    public void getMoney(long summ) throws OutOfExchangeException {
        if ((summ/cell_type.getNominal())>maxCountForGive){
            summ = summ-maxCountForGive*cell_type.getNominal();
            neededCount = maxCountForGive;

        } else {
            neededCount = (int) (summ/cell_type.getNominal());
            summ = summ-neededCount*cell_type.getNominal();
        }
        System.out.println("готовлю "+neededCount+" купюр, номиналом "+cell_type.getNominal()+"к выдаче, осталось "+summ);
        if (hasNext() && summ>0) next.getMoney(summ);
        else endCallback.call(summ);
    }

    /**
     * Метод выдаёт деньги
     */
    public void giveMoney(){
        count = count-neededCount;
        neededCount = 0;
        maxCountForGive = count;
    }

    public void setNext(AtmCell next){
        this.next = next;
    }

    private boolean hasNext(){
        return next!=null;
    }

    public int getNominal() {
        return cell_type.getNominal();
    }

    public int getNeededCount() {
        return neededCount;
    }

    public CELL_TYPE getType() {
        return cell_type;
    }

    public void setEndCallback(Callback endCallback){
        this.endCallback = endCallback;
    }

    public int getMaxCountForGive() {
        return maxCountForGive;
    }

    public void setMaxCountForGive(int maxCountForGive) {
        this.maxCountForGive = maxCountForGive;
    }
}
