package com.binance.trader.classes.strategies;

import com.binance.connector.client.impl.SpotClientImpl;
import com.binance.trader.classes.AccountInfo;
import com.binance.trader.classes.Kline;
import com.binance.trader.classes.selectors.IntSelector;
import com.binance.trader.classes.selectors.PeriodListSelector;
import com.binance.trader.enums.CrossingDirection;
import com.binance.trader.enums.OrderSide;
import com.binance.trader.enums.Period;
import com.binance.trader.enums.Symbol;
import com.binance.trader.intefaces.Strategy;
import com.binance.trader.services.AccountInfoService;
import com.binance.trader.services.KlineService;
import com.binance.trader.services.OrderService;
import com.binance.trader.services.TickerService;
import com.binance.trader.utils.Calculus;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;

public class MACDStrategy implements Strategy {
    protected SpotClientImpl client;
    protected Period period;
    protected int shortNbOfPeriods;
    protected int longNbOfPeriods;
    protected int signalNbOfPeriods;

    public MACDStrategy() {
        this.period = null;
        this.shortNbOfPeriods = -1;
        this.longNbOfPeriods = -1;
        this.signalNbOfPeriods = -1;
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
        while(true) {
            HashMap<String, Double> balances = this.getBalances(symbol);
            double tickerPrice = getTickerPrice(symbol);

            HashMap<String, ArrayList<Double>> lines = this.getLines(symbol);
            ArrayList<Double> MACDLine = lines.get("macd");
            ArrayList<Double> signalLine = lines.get("signal");
            double newMACD = MACDLine.get(MACDLine.size() - 1);
            double lastMACD = MACDLine.get(MACDLine.size() - 2);
            double newSignal = signalLine.get(signalLine.size() - 1);
            double lastSignal = signalLine.get(signalLine.size() - 2);

            CrossingDirection crossing = this.computeCrossingDirection(newSignal, newMACD, lastSignal, lastMACD);

            System.out.println("Base balance: " + balances.get("base") +
                    "\nQuote balance: " + balances.get("quote") +
                    "\nTicker " + tickerPrice +
                    "\nCrossing " + crossing +
                    "\nSignal " + newSignal +
                    "\nMACD " + newMACD);

            if (crossing == CrossingDirection.UP && balances.get("quote") > symbol.MIN_QUOTE_TRANSACTION) {
                this.generateOrder(symbol, OrderSide.BUY, balances, tickerPrice);
            } else if (crossing == CrossingDirection.DOWN && balances.get("base") > symbol.MIN_BASE_TRANSACTION) {
                this.generateOrder(symbol, OrderSide.SELL, balances, tickerPrice);
            }
        }
    }

    private HashMap<String, Double> getBalances(Symbol symbol) {
        AccountInfoService accountInfoService = new AccountInfoService(client);
        AccountInfo accountInfo = accountInfoService.getAccountInfo();
        double baseBalance = accountInfo.getBalance(symbol.getBase()).getFreeBalance();
        double quoteBalance = accountInfo.getBalance(symbol.getQuote()).getFreeBalance();

        HashMap<String, Double> balances = new HashMap<>();
        balances.put("base", baseBalance);
        balances.put("quote", quoteBalance);
        return balances;
    }

    private double getTickerPrice(Symbol symbol) {
        TickerService tickerService = new TickerService(client);
        return tickerService.getTicker(symbol).getPrice();
    }

    private HashMap<String, ArrayList<Double>> getLines(Symbol symbol) {
        // To compute the {size} EMA, you always need {size * 2 - 1} records
        int recordsToFetch = this.longNbOfPeriods + this.signalNbOfPeriods * 2 - 1;
        ArrayList<Double> prices = this.getClosePrices(symbol, recordsToFetch);

        // Compute the short EMA (generally 12) and the long EMA (generally 26) used for the MACD line
        ArrayList<Double> shortEMAS = Calculus.expMovingAvgWithSize(prices, this.shortNbOfPeriods);
        ArrayList<Double> longEMAs = Calculus.expMovingAvgWithSize(prices, this.longNbOfPeriods);

        // Compute the MACD Line which is the subtraction of the longEMA from the shortEMA
        ArrayList<Double> MACDLine = this.getMACDLine(shortEMAS, longEMAs);

        // Compute the signal line which is the EMA9 of the MACD line (subtractions)
        ArrayList<Double> signalLine = Calculus.expMovingAvgWithSize(MACDLine, signalNbOfPeriods);

        HashMap<String, ArrayList<Double>> lines = new HashMap<>();
        lines.put("macd", MACDLine);
        lines.put("signal", signalLine);
        return lines;
    }

    private CrossingDirection computeCrossingDirection(double newSignal, double newMACD, double lastSignal, double lastMACD) {
        if (newMACD >= newSignal && lastMACD < lastSignal) {
            return CrossingDirection.UP;
        } else if (newMACD <= newSignal && lastMACD > lastSignal) {
            return CrossingDirection.DOWN;
        } else {
            return CrossingDirection.NONE;
        }
    }

    private ArrayList<Double> getMACDLine(ArrayList<Double> shortEMAs, ArrayList<Double> longEMAs) {
        ArrayList<Double> MACDLine = new ArrayList<>();
        int recordsNeededForSignal = this.signalNbOfPeriods * 2 - 1;
        int lastIndexShortEMA = shortEMAs.size() - 1;
        int lastIndexLongEMA = longEMAs.size() - 1;
        for (int i=1; i <= recordsNeededForSignal; i++) {
            MACDLine.add(shortEMAs.get(lastIndexShortEMA - recordsNeededForSignal + i) - longEMAs.get(lastIndexLongEMA - recordsNeededForSignal + i));
        }
        return MACDLine;
    }

    private ArrayList<Double> getClosePrices(Symbol symbol, int recordsToFetch) {
        // Fetch the last {recordsToFetch} klines
        KlineService klineService = new KlineService(this.client);
        ArrayList<Kline> klines = klineService.fetchKlines(symbol, this.period.asString(), recordsToFetch);

        // Filter the data to only keep closing prices
        ArrayList<Double> prices = new ArrayList<>();
        klines.forEach((kline) -> prices.add(kline.getClosePrice()));
        return prices;
    }

    private void generateOrder(Symbol symbol, OrderSide side, HashMap<String, Double> balances, double tickerPrice) {
        OrderService orderService = new OrderService(this.client);
        if (side == OrderSide.BUY) orderService.buy(symbol, tickerPrice, balances.get("quote"));
        else orderService.sell(symbol, tickerPrice, balances.get("base"));
    }

    @Override
    public String describe() {
        return "Time Period: " + this.period +
                "\nShort Number of Periods: " + this.shortNbOfPeriods +
                "\nLong Number of Periods: " + this.longNbOfPeriods +
                "\nSignal Number of Periods: " + this.signalNbOfPeriods;
    }

    @Override
    public String toString() {
        return "Moving Average Convergence Divergence";
    }
}
