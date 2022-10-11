package com.binance.trader.classes.selectors;

import com.binance.trader.classes.strategies.EMAStrategy;
import com.binance.trader.classes.strategies.MACDStrategy;
import com.binance.trader.classes.strategies.MACDr1Strategy;
import com.binance.trader.classes.strategies.SMAStrategy;
import com.binance.trader.intefaces.Strategy;

public class StrategyListSelector extends ListSelector<Strategy> {

    public StrategyListSelector() {
        this.list = new Strategy[]{
                new SMAStrategy(),
                new EMAStrategy(),
                new MACDStrategy(),
                new MACDr1Strategy(),
        };
    }

    @Override
    protected void showSelector() {
        System.out.println("What strategy do you want to use? ");
        int index = 0;
        for (Strategy strategy : list) {
            System.out.println(index + ") " + strategy.toString());
            index++;
        }
    }
}
