package com.binance.trader.classes.strategies;

import com.binance.connector.client.impl.SpotClientImpl;
import com.binance.trader.classes.AccountInfo;
import com.binance.trader.classes.Kline;
import com.binance.trader.classes.selectors.IntSelector;
import com.binance.trader.classes.selectors.PeriodListSelector;
import com.binance.trader.enums.MACDCrossingDirection;
import com.binance.trader.enums.Period;
import com.binance.trader.enums.Symbol;
import com.binance.trader.intefaces.Strategy;
import com.binance.trader.services.AccountInfoService;
import com.binance.trader.services.KlineService;
import com.binance.trader.services.OrderService;
import com.binance.trader.services.TickerService;
import com.binance.trader.utils.Calculus;

import java.util.ArrayList;
import java.util.List;

public class MACDStrategy implements Strategy {
    protected SpotClientImpl client;
    private Period period;
    private int shortNbOfPeriods;
    private int longNbOfPeriods;
    private int signalNbOfPeriods;
    private double lastSignal;
    private double lastMACDLine;

    public MACDStrategy() {
        this.period = null;
        this.shortNbOfPeriods = -1;
        this.longNbOfPeriods = -1;
        this.signalNbOfPeriods = -1;
        this.lastSignal = 0.;
        this.lastMACDLine = 0.;
    }
    @Override
    public void init(SpotClientImpl client) {
        this.client = client;
        while (this.period == null) {
            PeriodListSelector selector = new PeriodListSelector();
            this.period = selector.startSelector();
        }
        while (this.shortNbOfPeriods < 0) {
            IntSelector selector = new IntSelector();
            this.shortNbOfPeriods = selector.startSelector("Short EMA");
        }
        while (this.longNbOfPeriods < 0) {
            IntSelector selector = new IntSelector();
            this.longNbOfPeriods = selector.startSelector("Long EMA");
        }
        while (this.signalNbOfPeriods < 0) {
            IntSelector selector = new IntSelector();
            this.signalNbOfPeriods = selector.startSelector("Signal EMA");
        }
    }

    @Override
    public void execute(Symbol symbol) {
        AccountInfoService accountInfoService = new AccountInfoService(client);
        AccountInfo accountInfo = accountInfoService.getAccountInfo();
        double baseBalance = accountInfo.getBalance(symbol.getBase()).getFreeBalance();
        double quoteBalance = accountInfo.getBalance(symbol.getQuote()).getFreeBalance();
        boolean hasSendAnOrder = false;

        while(true) {
            if (hasSendAnOrder) {
                accountInfo = accountInfoService.getAccountInfo();
                baseBalance = accountInfo.getBalance(symbol.getBase()).getFreeBalance();
                quoteBalance = accountInfo.getBalance(symbol.getQuote()).getFreeBalance();
                hasSendAnOrder = false;
            }
            TickerService tickerService = new TickerService(client);
            double tickerPrice = tickerService.getTicker(symbol).getPrice();
            MACDCrossingDirection crossing = this.isCrossing(symbol, lastSignal, lastMACDLine);

            System.out.println("Base balance: " + baseBalance + " / Quote balance: " + quoteBalance + " / Ticker " + tickerPrice +
                    " / Crossing " + crossing + " / Signal " + this.lastSignal + " / MACD " + this.lastMACDLine);

            OrderService orderService = new OrderService(this.client);
            if ((crossing == MACDCrossingDirection.UP || crossing == MACDCrossingDirection.NONE_OVER) && quoteBalance > symbol.MIN_QUOTE_TRANSACTION) { // TODO : find better way to buy when already crossed over
                orderService.buy(symbol, tickerPrice, quoteBalance);
                hasSendAnOrder = true;
            } else if ((crossing == MACDCrossingDirection.DOWN || crossing == MACDCrossingDirection.NONE_UNDER) && baseBalance > symbol.MIN_BASE_TRANSACTION) {
                orderService.sell(symbol, tickerPrice, baseBalance);
                hasSendAnOrder = true;
            }
        }
    }

    @Override
    public String describe() {
        return "Time Period: " + this.period +
                "\nShort Number of Periods: " + this.shortNbOfPeriods +
                "\nLong Number of Periods: " + this.longNbOfPeriods +
                "\nSignal Number of Periods: " + this.signalNbOfPeriods;
    }

    private MACDCrossingDirection isCrossing(Symbol symbol, double lastSignal, double lastMACDLine) {
        // To compute the {size} EMA, you always need {size * 2 - 1} records
        int recordsToFetch = this.longNbOfPeriods * 2 - 1;

        // Fetch the last {recordsToFetch} klines
        KlineService klineService = new KlineService(this.client);
        ArrayList<Kline> klines = klineService.fetchKlines(symbol, this.period.asString(), recordsToFetch);

        // Filter the data to only keep closing prices
        ArrayList<Double> prices = new ArrayList<>();
        klines.forEach((kline) -> prices.add(kline.getClosePrice()));

        // Compute the short EMA (generally 12) and the long EMA (generally 26) used for the MACD line
        ArrayList<Double> shortEMAS = Calculus.expMovingAvgWithSize(prices, this.shortNbOfPeriods);
        ArrayList<Double> longEMAs = Calculus.expMovingAvgWithSize(prices, 26);

        // Compute the MACD Line which is the subtraction of the longEMA from the shortEMA
        ArrayList<Double> subtractions = new ArrayList<>();
        int recordsNeededForSignal = this.signalNbOfPeriods * 2 - 1;
        int lastIndexShortEMA = shortEMAS.size() - 1;
        int lastIndexLongEMA = longEMAs.size() - 1;
        for (int i=1; i <= recordsNeededForSignal; i++) {
            subtractions.add(shortEMAS.get(lastIndexShortEMA - recordsNeededForSignal + i) - longEMAs.get(lastIndexLongEMA - recordsNeededForSignal + i));
        }

        // Compute the signal line which is the EMA9 of the MACD line (subtractions)
        ArrayList<Double> signals = Calculus.expMovingAvgWithSize(subtractions, signalNbOfPeriods);

        // Trading logic
        double signal = signals.get(signals.size() - 1);
        double MACDLine = subtractions.get(subtractions.size() - 1);

        if (MACDLine >= signal && lastMACDLine < lastSignal) {
            this.lastSignal = signal;
            this.lastMACDLine = MACDLine;
            return MACDCrossingDirection.UP;
        } else if (MACDLine <= signal && lastMACDLine > lastSignal) {
            this.lastSignal = signal;
            this.lastMACDLine = MACDLine;
            return MACDCrossingDirection.DOWN;
        } else if (MACDLine <= signal) {
            this.lastSignal = signal;
            this.lastMACDLine = MACDLine;
            return MACDCrossingDirection.NONE_UNDER;
        } else {
            this.lastSignal = signal;
            this.lastMACDLine = MACDLine;
            return MACDCrossingDirection.NONE_OVER;
        }
    }

    @Override
    public String toString() {
        return "Moving Average Convergence Divergence";
    }
}
