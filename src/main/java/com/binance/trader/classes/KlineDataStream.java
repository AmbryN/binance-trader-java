package com.binance.trader.classes;

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

    public Long gett() {
        return t;
    }

    public Long getT() {
        return T;
    }

    public String getS() {
        return s;
    }

    public String getI() {
        return i;
    }

    public int getF() {
        return f;
    }

    public int getL() {
        return L;
    }

    public Float getO() {
        return o;
    }

    public Float getC() {
        return c;
    }

    public Float getH() {
        return h;
    }

    public Float getl() {
        return l;
    }

    public Float getv() {
        return v;
    }

    public int getN() {
        return n;
    }

    public boolean isX() {
        return x;
    }

    public Float getq() {
        return q;
    }

    public Float getV() {
        return V;
    }

    public Float getQ() {
        return Q;
    }
}
