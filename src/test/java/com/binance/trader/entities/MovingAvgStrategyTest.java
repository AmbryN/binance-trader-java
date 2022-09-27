package com.binance.trader.entities;

import org.junit.Before;
import org.junit.Test;
public class MovingAvgStrategyTest {
    
    MovingAvgStrategy strategy;

    @Before
    public void setup() {
        strategy = new MovingAvgStrategy("1h", "20");
    }

    @Test
    public void shouldBuyIfPriceHigherThanMovingAvg() {
        
    }
}
