package com.binance.trader.entities;

import java.util.Date;

public class Kline {
    Long t;
    Long T;
    String s;
    String i;
    int f;
    int L;
    Float o;
    Float c;
    Float h;
    Float l;
    Float v;
    int n;
    boolean x;
    Float q;
    Float V;
    Float Q;

    Kline() {}

    public String toString() {
        return ("Open time: " + new Date(t) + " / Close time: " + new Date(T) + " / Interval: " + i + " / Open price: " + o + " / Close price: " + c);
    }
}
