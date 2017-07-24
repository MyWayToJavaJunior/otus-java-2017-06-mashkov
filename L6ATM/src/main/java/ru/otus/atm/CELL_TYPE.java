package ru.otus.atm;

public enum CELL_TYPE {
    CELL_500(500),CELL_100(100), CELL_1000(1000);

    int nominal;


    CELL_TYPE(int nominal){
        this.nominal = nominal;
    }

    public int getNominal() {
        return nominal;
    }
}
