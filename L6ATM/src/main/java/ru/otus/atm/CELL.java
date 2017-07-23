package ru.otus.atm;

public enum CELL {
    CELL_500(500),CELL_100(100), CELL_1000(1000);

    int nominal;
    int count;
    CELL next;
    Callback endCallback;
    //количество купюр к выдаче
    int neededCount;
    //максимальное количество купюр для выдачи
    int maxCountForGive;

    CELL(int nominal){
        this.nominal = nominal;
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
        if ((summ/nominal)>maxCountForGive){
            summ = summ-maxCountForGive*nominal;
            neededCount = maxCountForGive;

        } else {
            neededCount = (int) (summ/nominal);
            summ = summ-neededCount*nominal;
        }
        System.out.println("готовлю "+neededCount+" купюр, номиналом "+nominal+"к выдаче, осталось "+summ);
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

    public void setNext(CELL next){
        this.next = next;
    }

    private boolean hasNext(){
        return next!=null;
    }

    public int getNominal() {
        return nominal;
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
