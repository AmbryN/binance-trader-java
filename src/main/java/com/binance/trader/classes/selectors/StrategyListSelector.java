package com.binance.trader.classes.selectors;

import com.binance.trader.classes.strategies.EMAStrategy;
import com.binance.trader.classes.strategies.MACDStrategy;
import com.binance.trader.classes.strategies.SMAStrategy;
import com.binance.trader.intefaces.Strategy;

public class StrategyListSelector extends ListSelector<Strategy> {

    public StrategyListSelector() {
        this.list = new Strategy[]{
                new SMAStrategy(),
                new EMAStrategy(),
                new MACDStrategy(),
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