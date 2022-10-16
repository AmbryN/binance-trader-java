package com.binance.trader.classes.facade;

import com.binance.connector.client.impl.SpotClientImpl;
import com.binance.trader.classes.data.AccountInfo;
import com.binance.trader.classes.data.Kline;
import com.binance.trader.classes.data.Ticker;
import com.binance.trader.classes.handlers.Try;
import com.binance.trader.enums.Symbol;
import com.binance.trader.exceptions.BinanceTraderException;
import com.binance.trader.interfaces.Exchange;
import com.binance.trader.services.AccountInfoService;
import com.binance.trader.services.KlineService;
import com.binance.trader.services.OrderService;
import com.binance.trader.services.TickerService;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

/**
 * Facade class for Binance exchange which handles
 * all the connections and work for client the application
 */
// TODO : Test the Facade !
public class BinanceFacade implements Exchange {
    private final static String TESTNET_URL = "https://testnet.binance.vision";
    private final static String BINANCE_URL = "https://api.binance.com";

    private SpotClientImpl client;
    private AccountInfoService accountInfoService;
    private TickerService tickerService;
    private OrderService orderService;
    private KlineService klineService;
    private String url;
    private String apiKey;
    private String secretKey;

    public BinanceFacade() {
        url = TESTNET_URL;
        apiKey = System.getenv("TESTNET_API_KEY");
        secretKey = System.getenv("TESTNET_SECRET_KEY");
        if (System.getenv("BINANCE_TRADER_ENV").equals("PROD")) {
            url = BINANCE_URL;
            apiKey = System.getenv("BINANCE_API_KEY");
            secretKey = System.getenv("BINANCE_SECRET_KEY");
        }
        client = new SpotClientImpl(apiKey, secretKey, url);
        accountInfoService = new AccountInfoService(client);
        tickerService = new TickerService(client);
        orderService = new OrderService(client);
        klineService = new KlineService(client);
    }

    /**
     * Gets the balances for the symbol from the exchange.
     */
    public HashMap<String, Double> getBaseAndQuoteBalances(Symbol symbol) throws BinanceTraderException {
        Optional<AccountInfo> optionalAccountInfo =
                Try.toGet(() -> accountInfoService.getAccountInfo());
        if (optionalAccountInfo.isPresent()) {
            AccountInfo accountInfo = optionalAccountInfo.get();
            return accountInfo.getBaseAndQuoteBalancesFor(symbol);
        }
        throw new BinanceTraderException("Could not get Balances from the exchange");
    }

    /**
     * Gets the ticker price from the exchange.
     * @param symbol symbol to exchange
     * @return current price for given symbol
     * @throws BinanceTraderException if price could not be gotten from the exchange
     */
    public Double getTickerPrice(Symbol symbol) {
        Optional<Ticker> optionalTicker =
                Try.toGet(() -> tickerService.getTicker(symbol));
        if (optionalTicker.isPresent()) {
            return optionalTicker.get().getPrice();
        } else {
            throw new BinanceTraderException("Could not get Ticker from the exchange");
        }

    }

    /**
     * Gets the close prices from the exchange.
     * @param symbol symbol to exchange
     * @param period chosen period of information
     * @param nbOfRecordsToFetch number of records to fetch
     * @return closePrice list of prices
     * @throws BinanceTraderException if prices could not be gotten from the exchange
     */
    public ArrayList<Double> getClosePrices(Symbol symbol, String period, int nbOfRecordsToFetch) {
        Optional<ArrayList<Kline>> optionalKlines = Try.toGet(() -> klineService.fetchKlines(symbol, period, nbOfRecordsToFetch));
        if (optionalKlines.isPresent()) {
            ArrayList<Kline> klines = optionalKlines.get();
            return getClosesPricesFrom(klines);
        }
        throw new BinanceTraderException("Could not get Close Prices from the exchange");
    }

    @NotNull
    private static ArrayList<Double> getClosesPricesFrom(ArrayList<Kline> klines) {
        ArrayList<Double> closePrices = new ArrayList<>();
        klines.forEach(kline -> closePrices.add(kline.getClosePrice()));
        return closePrices;
    }

    /**
     * Buy amount {baseBalance} of {symbol} at the {tickerPrice}.
     * If the method fails, it logs it via the Try class and doesn't retry
     * @param symbol symbol to exchange
     * @param tickerPrice price at which to buy
     * @param quoteBalance amount to buy
     */
    public void buy(Symbol symbol, double tickerPrice, double quoteBalance) {
        Try.toRun(()
                -> orderService.buy(symbol,tickerPrice, quoteBalance));
    }

    /**
     * Sell amount {baseBalance} of {symbol} at the {tickerPrice}.
     * If the method fails, it logs it via the Try class and doesn't retry
     * @param symbol symbol to exchange
     * @param tickerPrice price at which to sell
     * @param baseBalance amount to sell
     */
    public void sell(Symbol symbol, double tickerPrice, double baseBalance) {
        Try.toRun(()
                -> orderService.sell(symbol,tickerPrice, baseBalance));
    }
}