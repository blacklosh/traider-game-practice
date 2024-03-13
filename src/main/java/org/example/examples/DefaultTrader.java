package org.example.examples;

import org.example.api.TraderSolution;
import org.example.api.TraderStrategy;
import org.example.api.Direction;

/**
 * Пример стратегии трейдера
 */
public class DefaultTrader implements TraderStrategy {

    private int amountOfMoney;

    private int amountOfCurrency;

    private int numberOfIterations;

    @Override
    public void init(int yourBalance, int n) {
        amountOfMoney = yourBalance;
        amountOfCurrency = 0;
        numberOfIterations = n;
    }

    @Override
    public TraderSolution decide(int price, int iteration) {
        /*
         * На первой итерации закупаемся по полной
         */
        if(iteration == 0) {
            int n = amountOfMoney / price;
            amountOfMoney -= n * price;
            amountOfCurrency += n;
            return new TraderSolution(Direction.BUY, n);
        }
        /*
         * На последней итерации продаём всё, что есть
         */
        if(iteration == numberOfIterations - 1) {
            int resultPrice = amountOfCurrency * price;
            amountOfMoney += resultPrice;
            return new TraderSolution(Direction.SELL, amountOfCurrency);
        }
        /*
         * На всех остальных итерациях ничего не делаем
         */
        return new TraderSolution(Direction.HOLD, 0);
    }

    @Override
    public String getTraderName() {
        return "Трейдер для примера";
    }
}
