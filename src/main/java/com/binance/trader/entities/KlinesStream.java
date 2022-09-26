package com.binance.trader.entities;

import java.util.Date;

public class KlinesStream {
    String e;
    Long E;
    String s;
    Kline k;

    KlinesStream() {}

    public String toString() {
        return (e + " from " + new Date(E) + " for pair " + s + ": " + k );
    }
}
