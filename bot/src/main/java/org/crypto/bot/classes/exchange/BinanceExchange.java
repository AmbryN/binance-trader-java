package org.crypto.bot.classes.exchange;

import com.binance.connector.client.impl.SpotClientImpl;
import org.crypto.bot.classes.data.AccountInfo;
import org.crypto.bot.classes.data.Kline;
import org.crypto.bot.classes.data.Ticker;
import org.crypto.bot.classes.handlers.Try;
import org.crypto.bot.enums.Period;
import org.crypto.bot.enums.Symbol;
import org.crypto.bot.exceptions.BinanceTraderException;
import org.crypto.bot.services.AccountInfoService;
import org.crypto.bot.services.KlineService;
import org.crypto.bot.services.OrderService;
import org.crypto.bot.services.TickerService;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;

/**
 * Represents the Binance exchange API, which handles
 * all the connections and work for the client application.
 */
public class BinanceExchange implements Exchange {
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

    public BinanceExchange() {
        if (System.getenv("BINANCE_TRADER_ENV").equals("PROD")) {
            url = BINANCE_URL;
            apiKey = System.getenv("BINANCE_API_KEY");
            secretKey = System.getenv("BINANCE_SECRET_KEY");
        }
        url = TESTNET_URL;
        apiKey = System.getenv("TESTNET_API_KEY");
        secretKey = System.getenv("TESTNET_SECRET_KEY");
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
    public double getTicker(Symbol symbol) {
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
     * @return array of closing prices
     * @throws BinanceTraderException if prices could not be gotten from the exchange
     */
    public double[] getClosePrices(Symbol symbol, Period period, int nbOfRecordsToFetch) {
        String periodAsString = period.toString();
        Optional<Kline[]> optionalKlines = Try.toGet(() -> klineService.fetchKlines(symbol, periodAsString, nbOfRecordsToFetch));
        if (optionalKlines.isPresent()) {
            Kline[] klines = optionalKlines.get();
            return getClosesPricesFrom(klines);
        }
        throw new BinanceTraderException("Could not get Close Prices from the exchange");
    }

    private double[] getClosesPricesFrom(Kline[] klines) {
        return Arrays.stream(klines).map(Kline::getClosePrice).mapToDouble(Double::doubleValue).toArray();
    }

    /**
     * Buy amount {baseBalance} of {symbol} at the {tickerPrice}.
     * If the method fails, it logs it via the Try class and doesn't retry
     * @param symbol symbol to exchange
     * @param quoteBalance amount to buy
     */
    public void buy(Symbol symbol, double quoteBalance) {
        Try.toRun(()
                -> orderService.buy(symbol, quoteBalance));
    }

    /**
     * Sell amount {baseBalance} of {symbol} at the {tickerPrice}.
     * If the method fails, it logs it via the Try class and doesn't retry
     * @param symbol symbol to exchange
     * @param baseBalance amount to sell
     */
    public void sell(Symbol symbol, double baseBalance) {
        Try.toRun(()
                -> orderService.sell(symbol, baseBalance));
    }

    @Override
    public String toString() {
        return "Binance";
    }
}