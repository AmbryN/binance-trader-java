package com.binance.trader.classes.data;

import com.binance.trader.classes.facade.BinanceFacade;
import com.binance.trader.classes.selectors.PeriodListSelector;
import com.binance.trader.classes.selectors.StrategyListSelector;
import com.binance.trader.classes.selectors.SymbolListSelector;
import com.binance.trader.enums.Period;
import com.binance.trader.enums.Symbol;
import com.binance.trader.interfaces.Exchange;
import com.binance.trader.interfaces.Strategy;

public class TraderConfig {
    private final Exchange exchange;
    private final Symbol symbol;
    private final Strategy strategy;
    private final Period period;

    public TraderConfig() {
        this.exchange = new BinanceFacade();
        this.symbol = new SymbolListSelector().startSelector();
        this.strategy = new StrategyListSelector().startSelector();
        this.period = new PeriodListSelector().startSelector();
        strategy.init(exchange, period);
    }

    public Exchange getExchange() {
        return exchange;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public Strategy getStrategy() {
        return strategy;
    }

    public Period getPeriod() {
        return period;
    }

    public void describe() {
        System.out.println("=== SUMMARY === " +
                "\nYou want to trade: " +
                "\nSymbol: " + symbol +
                "\nStrategy: " + strategy +
                "\nPeriod: " + period +
                "\n" + strategy.describe());
    }
}
