package ru.otus.atm;

import java.util.ArrayList;

@FunctionalInterface
public interface Strategy {
    /**
     *
     * @param atmCells to apply strategy
     * @return first cell in chain
     */
    AtmCell apply(ArrayList<AtmCell> atmCells, long summ);
}
