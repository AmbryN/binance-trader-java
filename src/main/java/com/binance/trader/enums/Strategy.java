package com.binance.trader.enums;

public enum Strategy {
    MovingAvg(1),
    None(0);

    int position;

    Strategy() {}

    Strategy(int position) {
        this.position = position;
    }

    public int getPosition() {
        return this.position;
    }

    public static Strategy getStrategy(int userStrategyChoice) {
        for (Strategy strategy : Strategy.values()) {
            if (userStrategyChoice == strategy.position) {
                return strategy;
            }
        };
        return Strategy.None;
    }
}
