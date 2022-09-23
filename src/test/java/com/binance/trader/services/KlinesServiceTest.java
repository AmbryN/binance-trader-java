package com.binance.trader.services;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class KlinesServiceTest {
    KlinesService service = new KlinesService();

    @Test
    public void printKlines() {
        service.getKlines();
        assertEquals(-1, service.klines);
    }
}
