package ru.otus.atm;

@FunctionalInterface
public interface Strategy {
    /**
     *
     * @param cells to apply strategy
     * @return first cell in chain
     */
    CELL apply(CELL[] cells, long summ);
}
