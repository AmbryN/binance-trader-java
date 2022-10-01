package com.binance.trader.classes;

import com.binance.trader.intefaces.Strategy;

public class StrategyListSelector extends ListSelector<Strategy> {

    public StrategyListSelector() {
        this.list = new Strategy[]{
                new MovingAvgStrategy()
        };
    }

    @Override
    protected void showSelector() {
        System.out.println("What strategy do you want to use? ");

        for (int i = 0; i < list.length; i++) {
            System.out.println(i +") " + list[i]);
        }
    }
}
