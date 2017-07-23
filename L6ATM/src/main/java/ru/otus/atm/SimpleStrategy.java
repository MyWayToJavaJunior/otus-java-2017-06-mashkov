package ru.otus.atm;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Простая стратегия, выдача по убыванию номинала
 */
public class SimpleStrategy implements Strategy {
    @Override
    public CELL apply(CELL[] cells, long summ) {
        List<CELL> sorted = Arrays.stream(cells).sorted((o1, o2) -> Integer.compare(o2.nominal, o1.nominal)).collect(Collectors.toList());
        for (int i = 0; i < sorted.size(); i++) {
            CELL next = null;
            if (i < sorted.size()-1){
                next = sorted.get(i+1);
            }
          sorted.get(i).setNext(next);
        }
        return sorted.get(0);
    }
}
