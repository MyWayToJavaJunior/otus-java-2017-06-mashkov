package ru.otus.atm;

import ru.otus.interfaces.Strategy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Стратегия для выдачи с частичным разменом
 * Ограничим выдачу по крупным, остальное выдадим мелкими
 */
public class ExchangeStrategy implements Strategy {

    @Override
    public AtmCell apply(ArrayList<AtmCell> atmCells, long summ) {
        List<AtmCell> sorted = atmCells.stream().sorted((o1, o2) -> Integer.compare(o2.getNominal(), o1.getNominal())).collect(Collectors.toList());
        atmCells.stream().max(Comparator.comparingInt(AtmCell::getNominal)).ifPresent(cell -> cell.setMaxCountForGive((int) (summ/(2* cell.getNominal()))));
        for (int i = 0; i < sorted.size(); i++) {
            AtmCell next = null;
            AtmCell current = sorted.get(i);
            if (i < sorted.size()-1){
                next = sorted.get(i+1);
            }
            current.setNext(next);
        }
        return sorted.get(0);

    }
}
