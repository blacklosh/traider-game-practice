package org.example.cmd;

import org.example.api.Direction;
import org.example.api.TraderSolution;
import org.example.api.TraderStrategy;

import java.util.Random;

public class TraderExecutor {

    private final int MAX_VALUE;
    private final int MAX_CHANGE;
    private final int NUMBER_OF_ITERATIONS;

    /**
     * Инициализация тестировщика
     *
     * @param maxValue ограничение курса сверху (снизу 0)
     * @param numberOfIterations кол-во торговых итераций
     */
    public TraderExecutor(int maxValue, int numberOfIterations) {
        MAX_VALUE = maxValue;
        MAX_CHANGE = MAX_VALUE / 100;
        NUMBER_OF_ITERATIONS = numberOfIterations;
    }

    /**
     * Прогон конкретного трейдера по рынку
     *
     * @param randomSeed сид генерации, позволяет несколько раз получить одинаковый рынок
     * @param trader испытуемый
     * @param log включение/отключение подробного логирования
     * @return результат трейдера - его выигрыш (или проигрыш) (т.е. изменение его баланса относительно изначального кол-ва рублей)
     */
    public int processTrader(long randomSeed, TraderStrategy trader, boolean log) {
        Random random = new Random(randomSeed);

        int initialMoneys = random.nextInt(MAX_VALUE) + MAX_CHANGE;
        int moneys = initialMoneys;
        int price = random.nextInt(MAX_VALUE) + MAX_CHANGE;
        int currency = 0;
        if(log) System.out.println("Игра началась для " + trader.getTraderName() + ". " +
                "Изначально у игрока " + moneys + " рублей, стартовая цена доллара равна " + price);

        trader.init(moneys, NUMBER_OF_ITERATIONS);

        for (int i = 0; i < NUMBER_OF_ITERATIONS; i++) {
            int change = random.nextInt(MAX_CHANGE);
            if(change >= price) {
                price += change;
            } else {
                int direction = random.nextInt(2);
                if(direction == 0) {
                    price -= change;
                } else {
                    price += change;
                }
            }
            if(log) System.out.println("Итерация " + i + ". Изменение цены " + change + ", теперь стоимость = " + price);

            TraderSolution solution = trader.decide(price, i);

            Direction direction = solution.getDirection();
            int amount = solution.getAmountOfCurrency();

            if(Direction.HOLD.equals(direction)) {
                if(amount != 0) {
                    System.err.println("Игрок выбрал HOLD с ненулевым amount. Правила нарушены, игра завершена.");
                    return 0;
                }
                if(log) System.out.println("Игрок воздержался. Его баланс не изменился: " +
                        moneys + " рублей и " + currency + " долларов.");
            } else if (Direction.BUY.equals(direction)) {
                if(amount * price > moneys) {
                    System.err.println("Игрок выбрал BUY " + amount + " долларов по цене " +price + ", " +
                            "но у него только " + moneys + " рублей. Правила нарушены, игра завершена.");
                    return 0;
                } else {
                    moneys -= amount * price;
                    currency += amount;
                    if(log) System.out.println("Игрок выбрал BUY " + amount + " долларов по цене " +price + ", " +
                            "теперь у него " + moneys + " рублей и " + currency + " долларов.");
                }
            } else if (Direction.SELL.equals(direction)) {
                if(amount > currency) {
                    System.err.println("Игрок выбрал SELL " + amount + " долларов, " +
                            "но у него только " + currency + " долларов. Правила нарушены, игра завершена.");
                    return 0;
                } else {
                    moneys += amount * price;
                    currency -= amount;
                    if(log) System.out.println("Игрок выбрал SELL " + amount + " долларов по цене " +price + ", " +
                            "теперь у него " + moneys + " рублей и " + currency + " долларов.");
                }
            }
        }
        if(log) System.out.println("Итерации завершены, у игрока " + moneys + " рублей и " + currency + " долларов. " +
                "Выигрыш составил " + (moneys - initialMoneys) + ".");
        return moneys - initialMoneys;
    }

}
