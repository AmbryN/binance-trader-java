package com.binance.trader;

import com.binance.connector.client.impl.SpotClientImpl;
import com.binance.trader.classes.selectors.StrategyListSelector;
import com.binance.trader.classes.selectors.SymbolListSelector;
import com.binance.trader.classes.selectors.YesNoSelector;
import com.binance.trader.enums.Symbol;
import com.binance.trader.intefaces.Strategy;

public class Trader {
    private static final String TESTNET_URL = "https://testnet.binance.vision";
    private static final String BINANCE_URL = "https://api.binance.com";
    private final SpotClientImpl client;
    int start = 0;
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
        while(start == 0) {
            symbol = null;
            strategy = null;

            while (symbol == null) {
                symbol = symbolSelection();
            }

            while (strategy == null) {
                strategy = strategySelection();
            }
            strategy.init(this.client);

            start = -1;
            while (start == -1) {
                System.out.println("=== SUMMARY === \nYou want to trade: \nSymbol: " + symbol +
                        "\nStrategy: " + strategy + "\n" + strategy.describe());
                start = shouldStart();
            }
        }
        strategy.execute(symbol);
    }

    /**
     * The method is used to ask the user to
     * verify the trade parameters and prompt
     * him to accept or decline them
     * @return if the user wants to start or not
     */
    private int shouldStart() {
        YesNoSelector selector = new YesNoSelector();
        return selector.startSelector();
    }

    /**
     * The method is used to print the available symbols
     * the user can trade and to prompt him for a selection
     * @return the selected Symbol the user wants to trade
     */
    private Symbol symbolSelection() {
        SymbolListSelector selector = new SymbolListSelector();
        return selector.startSelector();
    }

    /**
     * The method is used to print the available strategies
     * the user can use to trade and to prompt him for a selection
     * @return the select Strategy the user wants to use
     */
    private Strategy strategySelection() {
        StrategyListSelector selector = new StrategyListSelector();
        return selector.startSelector();
    }
}
