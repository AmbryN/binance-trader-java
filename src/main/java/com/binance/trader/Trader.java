package com.binance.trader;

import com.binance.connector.client.impl.SpotClientImpl;
import com.binance.trader.classes.data.AccountInfo;
import com.binance.trader.classes.selectors.*;
import com.binance.trader.classes.strategies.*;
import com.binance.trader.enums.AvailableStrategy;
import com.binance.trader.enums.StrategyResult;
import com.binance.trader.enums.Symbol;
import com.binance.trader.intefaces.Strategy;
import com.binance.trader.services.AccountInfoService;
import com.binance.trader.services.OrderService;
import com.binance.trader.services.TickerService;

import java.util.HashMap;

public class Trader {
    private static final String TESTNET_URL = "https://testnet.binance.vision";
    private static final String BINANCE_URL = "https://api.binance.com";
    private final SpotClientImpl client;
    boolean start = false;
    Strategy strategy;
    Symbol symbol;

    public Trader() {
        String url = TESTNET_URL;
        String apiKey = System.getenv("TESTNET_API_KEY");
        String secretKey = System.getenv("TESTNET_SECRET_KEY");
        if (System.getenv("BINANCE_TRADER_ENV").equals("PROD")) {
            url = BINANCE_URL;
            apiKey = System.getenv("BINANCE_API_KEY");
            secretKey = System.getenv("BINANCE_SECRET_KEY");
        }
        this.client = new SpotClientImpl(apiKey, secretKey, url);
    }

    public void start() {
        while(!start) {
            symbol = null;
            strategy = null;

            while (symbol == null) {
                symbol = symbolSelection();
            }

            while (strategy == null) {
                strategy = strategySelection();
            }
            strategy.init(this.client);

            System.out.println("=== SUMMARY === \nYou want to trade: \nSymbol: " +
                    symbol + "\nStrategy: " + strategy + "\n" + strategy.describe());
            start = shouldStart();
        }

        HashMap<String, Double> balances = this.getBalances(symbol);
        while(true) {
            double tickerPrice = this.getTickerPrice(symbol);
            StrategyResult result = strategy.execute(symbol, balances, tickerPrice);

            OrderService orderService = new OrderService(this.client);
            if (result == StrategyResult.BUY) {
                orderService.buy(symbol, tickerPrice, balances.get("quote"));
                balances = this.getBalances(symbol);
            } else if (result == StrategyResult.SELL) {
                orderService.sell(symbol, tickerPrice, balances.get("base"));
                balances = this.getBalances(symbol);
            }
        }
    }

    /**
     * The method is used to ask the user to
     * verify the trade parameters and prompt
     * him to accept or decline them
     * @return if the user wants to start or not
     */
    private boolean shouldStart() {
        return new YesNoSelector().startSelector();
    }

    /**
     * The method is used to print the available symbols
     * the user can trade and to prompt him for a selection
     * @return the selected Symbol the user wants to trade
     */
    private Symbol symbolSelection() {
        return new SymbolListSelector().startSelector();
    }

    /**
     * The method is used to print the available strategies
     * the user can use to trade and to prompt him for a selection
     * @return the select Strategy the user wants to use
     */
    private Strategy strategySelection() {
        return new StrategyListSelector().startSelector();
    }

    protected HashMap<String, Double> getBalances(Symbol symbol) {
        AccountInfoService accountInfoService = new AccountInfoService(client);
        AccountInfo accountInfo = accountInfoService.getAccountInfo();
        double baseBalance = accountInfo.getBalance(symbol.getBase()).getFreeBalance();
        double quoteBalance = accountInfo.getBalance(symbol.getQuote()).getFreeBalance();

        HashMap<String, Double> balances = new HashMap<>();
        balances.put("base", baseBalance);
        balances.put("quote", quoteBalance);
        return balances;
    }

    protected double getTickerPrice(Symbol symbol) {
        TickerService tickerService = new TickerService(client);
        return tickerService.getTicker(symbol).getPrice();
    }
}
