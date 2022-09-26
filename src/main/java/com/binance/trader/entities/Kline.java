package com.binance.trader.entities;

import java.util.Date;

public class Kline {
    Long openTime;
    double openPrice;
    double highPrice;
    double lowPrice;
    double closePrice;
    double volume;
    Long closeTime;
    double quoteVolume;
    int nbOfTrades;
    double takerBuyBaseVolume;
    double takerBuyQuoteVolume;

    public Kline(
        Long openTime,
        double openPrice,
        double highPrice,
        double lowPrice,
        double closePrice,
        double volume,
        Long closeTime,
        double quoteVolume,
        int nbOfTrades,
        double takerBuyBaseVolume,
        double takerBuyQuoteVolume) {
            this.openTime = openTime;
            this.openPrice = openPrice;
            this.highPrice = highPrice;
            this.lowPrice = lowPrice;
            this.closePrice = closePrice;
            this.volume = volume;
            this.closeTime = closeTime;
            this.quoteVolume = quoteVolume;
            this.nbOfTrades = nbOfTrades;
            this.takerBuyBaseVolume = takerBuyBaseVolume;
            this.takerBuyQuoteVolume = takerBuyQuoteVolume;
    }

    public String toString() {
        return ("Kline from " + new Date(closeTime) + " / Closed at " + closePrice);
    }
}
