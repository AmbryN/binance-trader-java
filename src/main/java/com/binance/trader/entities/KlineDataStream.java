package com.binance.trader.entities;

import java.util.Date;

public class KlineDataStream {
    private Long t;
    private Long T;
    private String s;
    private String i;
    private int f;
    private int L;
    private Float o;
    private Float c;
    private Float h;
    private Float l;
    private Float v;
    private int n;
    private boolean x;
    private Float q;
    private Float V;
    private Float Q;

    KlineDataStream() {}

    public String toString() {
        return ("Open time: " + new Date(t) + " / Close time: " + new Date(T) + " / Interval: " + i + " / Open price: " + o + " / Close price: " + c);
    }
}
