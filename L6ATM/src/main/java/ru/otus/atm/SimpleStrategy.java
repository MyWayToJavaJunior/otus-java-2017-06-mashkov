package ru.otus.atm;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Простая стратегия, выдача по убыванию номинала
 */
public class SimpleStrategy implements Strategy {
    @Override
    public AtmCell apply(ArrayList<AtmCell> atmCells, long summ) {
        List<AtmCell> sorted = atmCells.stream().sorted((o1, o2) -> Integer.compare(o2.getNominal(), o1.getNominal())).collect(Collectors.toList());
        for (int i = 0; i < sorted.size(); i++) {
            AtmCell next = null;
            if (i < sorted.size()-1){
                next = sorted.get(i+1);
            }
          sorted.get(i).setNext(next);
        }
        return sorted.get(0);
    }
}
