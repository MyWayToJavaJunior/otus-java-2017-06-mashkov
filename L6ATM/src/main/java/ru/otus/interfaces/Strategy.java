package ru.otus.interfaces;

import ru.otus.atm.AtmCell;

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
