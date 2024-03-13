package org.example.examples;

import org.example.api.Direction;
import org.example.api.TraderSolution;
import org.example.api.TraderStrategy;

/**
 * Пример стратегии трейдера #2
 */
public class RandomTrader implements TraderStrategy {

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
        int intDecision = (int)(Math.random() * 3);
        if (intDecision == 0) {
            // HOLD
            return new TraderSolution(Direction.HOLD, 0);
        } else if (intDecision == 1) {
            // BUY
            int maximumToBuy = amountOfMoney / price;
            int buyAmount = (int)(Math.random() * maximumToBuy) + 1;
            if(maximumToBuy == 0 || buyAmount == 0) {
                return new TraderSolution(Direction.HOLD, 0);
            } else {
                amountOfCurrency += buyAmount;
                amountOfMoney -= buyAmount * price;
                return new TraderSolution(Direction.BUY, buyAmount);
            }
        } else {
            // SELL
            int sellAmount = (int)(Math.random() * amountOfCurrency) + 1;
            if(sellAmount == 0 || amountOfCurrency == 0) {
                return new TraderSolution(Direction.HOLD, 0);
            } else {
                amountOfCurrency -= sellAmount;
                amountOfMoney += sellAmount * price;
                return new TraderSolution(Direction.SELL, sellAmount);
            }
        }
    }

    @Override
    public String getTraderName() {
        return "Рандомный трейдер";
    }
}
