package com.binance.trader.entities;

import com.binance.trader.intefaces.Strategy;

public class MovingAvgStrategy implements Strategy {
    String name = "MovingAvg";

    public MovingAvgStrategy() {}

    @Override
    public void execute() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void log() {
        // TODO Auto-generated method stub
        
    }

    public String toString() {
        return this.name;
    }
}
