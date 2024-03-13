package org.example.api;

public interface TraderStrategy {

    /**
     * Инициализация трейдера перед началом торговли
     *
     * @param yourBalance изначальный баланс трейдера в рублях
     * @param numberOfIterations количество торговых дней
     */
    void init(int yourBalance, int numberOfIterations);

    /**
     * Решение трейдера в очередной день
     *
     * @param price цена доллара в рублях
     * @param iteration номер торгового дня (начинается с 0)
     * @return решение трейдера (продать/купить/воздержаться)
     */
    TraderSolution decide(int price, int iteration);

    /**
     * Получение имени трейдера
     *
     * @return Имя трейдера - имя студента и номер группы
     */
    String getTraderName();

}
