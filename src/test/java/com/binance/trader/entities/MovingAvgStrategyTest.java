package com.binance.trader.entities;

public class MovingAvgStrategyTest {
    
    MovingAvgStrategy strategy;

    public void setup() {
        strategy = new MovingAvgStrategy("1h", "20");
    }
}
