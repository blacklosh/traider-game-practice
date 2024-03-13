package org.example;

import org.example.api.TraderStrategy;
import org.example.cmd.TraderExecutor;
import org.example.examples.DefaultTrader;
import org.example.examples.RandomTrader;

import java.util.List;

/**
 * Для любого трейдера случайным образом выбирается изначальное кол-во
 * Рублей, а долларов изначально 0.
 * Нужно торговать валютами в меняющемся курсе, а в конце периода остаться
 * только с рублями (доллары на руках в зачёт не идут).
 * <p>
 * Можно остаться ни с чем, можно уйти в минус. Задача - выйти в плюс,
 * при том как можно больший.
 * <p>
 * Метод battle() позволяет прогнать несколько различных стратегий
 * на одном и том же рынке (одинаковое изменение цен) с одинаковым изначальным
 * кол-вом рублей (честное соревнование)
 */
public class Main {
    public static void main(String[] args) {
        test1();
        // battle(List.of(new DefaultTrader(), new RandomTrader()));
    }

    /**
     С сидами 0, 3 - проигрыш
     С сидами 1, 2 - ноль
     С сидами 5, 7 - выигрыш
     */
    private static void test1() {
        TraderStrategy trader = new DefaultTrader();
        TraderExecutor executor = new TraderExecutor(5000, 200);
        executor.processTrader(0, trader, true);
    }

    private static void battle(List<TraderStrategy> strategies) {
        TraderExecutor executor = new TraderExecutor(5000, 200);
        long seed = 0;
        for (TraderStrategy strategy : strategies) {
            int result = executor.processTrader(seed, strategy, false);
            System.out.println("Игрок " + strategy.getTraderName() + " получает результат " + result);
        }
    }
}