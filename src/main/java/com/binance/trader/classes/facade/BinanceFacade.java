package com.binance.trader.classes.facade;

import com.binance.connector.client.impl.SpotClientImpl;
import com.binance.trader.classes.data.AccountInfo;
import com.binance.trader.classes.data.Kline;
import com.binance.trader.enums.Symbol;
import com.binance.trader.intefaces.Exchange;
import com.binance.trader.services.binance.AccountInfoService;
import com.binance.trader.services.binance.KlineService;
import com.binance.trader.services.binance.OrderService;
import com.binance.trader.services.binance.TickerService;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Facade class for Binance exchange which handles
 * all the connections and work for client the application
 */
class BinanceFacade implements Exchange {
    private final static String TESTNET_URL = "https://testnet.binance.vision";
    private final static String BINANCE_URL = "https://api.binance.com";
    private SpotClientImpl client;
    private String url;
    private String apiKey;
    private String secretKey;

    public BinanceFacade() {
        url = TESTNET_URL;
        apiKey = System.getenv("TESTNET_API_KEY");
        secretKey = System.getenv("TESTNET_SECRET_KEY");
        if (System.getenv("BINANCE_TRADER_ENV") == "PROD") {
            url = BINANCE_URL;
            apiKey = System.getenv("BINANCE_API_KEY");
            secretKey = System.getenv("BINANCE_SECRET_KEY");
        }
        client = new SpotClientImpl(apiKey, secretKey, url);
    }

    public HashMap<String, Double> getBalances(Symbol symbol) {
        AccountInfo accountInfo = new AccountInfoService(client).getAccountInfo();
        Double baseBalance = accountInfo.getBalance(symbol.getBase()).getFreeBalance();
        Double quoteBalance = accountInfo.getBalance(symbol.getQuote()).getFreeBalance();

        HashMap<String, Double> balances = new HashMap<>();
        balances.put("base", baseBalance);
        balances.put("quote", quoteBalance);
        return balances;
    }

    public Double getTickerPrice(Symbol symbol) {
        return new TickerService(client).getTicker(symbol).getPrice();
    }

    public ArrayList<Double> getClosePrices(Symbol symbol, String period, int nbOfRecordsToFetch) {
        ArrayList<Kline> klines = new KlineService(client).fetchKlines(symbol, period, nbOfRecordsToFetch);
        ArrayList<Double> closePrices = new ArrayList<>();
        klines.forEach(kline -> closePrices.add(kline.getClosePrice()));
        return closePrices;
    }

    public void buy(Symbol symbol, double tickerPrice, double quoteBalance) {
        new OrderService(client).buy(symbol,tickerPrice, quoteBalance);
    }

    public void sell(Symbol symbol, double tickerPrice, double baseBalance) {
        new OrderService(client).sell(symbol,tickerPrice, baseBalance);
    }
}