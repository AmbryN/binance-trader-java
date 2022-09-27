package com.binance.trader.entities;

import java.util.Date;

public class Kline {
    Long openTime;
    Double openPrice;
    Double highPrice;
    Double lowPrice;
    Double closePrice;
    Double volume;
    Long closeTime;
    Double quoteVolume;
    int nbOfTrades;
    Double takerBuyBaseVolume;
    Double takerBuyQuoteVolume;

    public Kline(
        Long openTime,
        Double openPrice,
        Double highPrice,
        Double lowPrice,
        Double closePrice,
        Double volume,
        Long closeTime,
        Double quoteVolume,
        int nbOfTrades,
        Double takerBuyBaseVolume,
        Double takerBuyQuoteVolume) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (this.getClass() != o.getClass()) {
            return false;
        }

        Kline kline = (Kline) o;
        boolean a = this.openTime.equals(kline.openTime);
        boolean b = this.openPrice.equals(kline.openPrice);
        boolean c = this.highPrice.equals(kline.highPrice);
        boolean d = this.lowPrice.equals(kline.lowPrice);
        boolean e = this.closePrice.equals(kline.closePrice);
        boolean f = this.volume.equals(kline.volume);
        boolean g = this.closeTime.equals(kline.closeTime);
        boolean h = this.quoteVolume.equals(kline.quoteVolume);
        boolean i = this.nbOfTrades == kline.nbOfTrades;
        boolean j = this.takerBuyBaseVolume.equals(kline.takerBuyBaseVolume);
        boolean k = this.takerBuyQuoteVolume.equals(kline.takerBuyQuoteVolume);

        return a && b && c && d && e && f && g && h && i && j && k;
    }
}
