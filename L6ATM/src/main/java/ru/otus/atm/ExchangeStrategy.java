package ru.otus.atm;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Стратегия для выдачи с частичным разменом
 * Ограничим выдачу по крупным, остальное выдадим мелкими
 */
public class ExchangeStrategy implements Strategy {

    @Override
    public CELL apply(CELL[] cells, long summ) {
        List<CELL> sorted = Arrays.stream(cells).sorted((o1, o2) -> Integer.compare(o2.nominal, o1.nominal)).collect(Collectors.toList());
        CELL.CELL_1000.setMaxCountForGive((int) (summ/(2*CELL.CELL_1000.getNominal())));
        for (int i = 0; i < sorted.size(); i++) {
            CELL next = null;
            CELL current = sorted.get(i);
            if (i < sorted.size()-1){
                next = sorted.get(i+1);
            }
            current.setNext(next);
        }
        return sorted.get(0);

    }
}
