package org.example.api;

/**
 * Решение трейдера для очередного дня.
 * Direction - формат (продажа/покупка/пропуск)
 * AmountOfCurrency - сколько долларов участвует в сделке
 */
public class TraderSolution {

    private final Direction direction;

    private final int amountOfCurrency;

    public TraderSolution(Direction d, int a) {
        direction = d;
        amountOfCurrency = a;
    }

    public int getAmountOfCurrency() {
        return amountOfCurrency;
    }

    public Direction getDirection() {
        return direction;
    }
}
