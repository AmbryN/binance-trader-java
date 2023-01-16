package com.binance.trader.classes.facade;

import com.binance.connector.client.exceptions.BinanceClientException;
import com.binance.connector.client.exceptions.BinanceConnectorException;
import com.binance.connector.client.exceptions.BinanceServerException;
import com.binance.connector.client.impl.SpotClientImpl;
import com.binance.trader.enums.Symbol;
import com.binance.trader.exceptions.BinanceTraderException;
import com.binance.trader.services.AccountInfoService;
import com.binance.trader.services.KlineService;
import com.binance.trader.services.OrderService;
import com.binance.trader.services.TickerService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

public class BinanceFacadeTest {

    @Mock
    SpotClientImpl clientMock;
    @Mock
    AccountInfoService accountInfoServiceMock;
    @Mock
    TickerService tickerServiceMock;

    @Mock
    OrderService orderServiceMock;

    @Mock
    KlineService klineServiceMock;

    @InjectMocks BinanceFacade facade;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test(expected = BinanceTraderException.class)
    public void shouldThrowExceptionIfGetBalancesThrowsAConnectorException() {
        when(accountInfoServiceMock.getAccountInfo()).thenThrow(BinanceConnectorException.class);
        facade.getBaseAndQuoteBalances(Symbol.BTCUSDT);
    }

    @Test(expected = BinanceTraderException.class)
    public void shouldThrowExceptionIfGetBalancesThrowsAServerException() {
        when(accountInfoServiceMock.getAccountInfo()).thenThrow(BinanceServerException.class);
        facade.getBaseAndQuoteBalances(Symbol.BTCUSDT);
    }

    @Test(expected = BinanceClientException.class)
    public void shouldThrowExceptionIfGetBalancesThrowsAClientException() {
        when(accountInfoServiceMock.getAccountInfo()).thenThrow(BinanceClientException.class);
        facade.getBaseAndQuoteBalances(Symbol.BTCUSDT);
    }

    @Test(expected = BinanceTraderException.class)
    public void shouldThrowExceptionIfGetTickerThrowsConnectorException() {
        when(tickerServiceMock.getTicker(any(Symbol.class))).thenThrow(BinanceConnectorException.class);
        facade.getTicker(Symbol.BTCUSDT);
    }

    @Test(expected = BinanceTraderException.class)
    public void shouldThrowExceptionIfGetTickerThrowsServerException() {
        when(tickerServiceMock.getTicker(any(Symbol.class))).thenThrow(BinanceServerException.class);
        facade.getTicker(Symbol.BTCUSDT);
    }

    @Test(expected = BinanceClientException.class)
    public void shouldThrowExceptionIfGetTickerThrowsClientException() {
        when(tickerServiceMock.getTicker(any(Symbol.class))).thenThrow(BinanceClientException.class);
        facade.getTicker(Symbol.BTCUSDT);
    }

    @Test(expected = BinanceTraderException.class)
    public void shouldThrowExceptionIfGetClosePricesThrowsConnectorException() {
        when(klineServiceMock.fetchKlines(any(Symbol.class), any(String.class), any(Integer.class))).thenThrow(BinanceConnectorException.class);
        facade.getClosePrices(Symbol.BTCUSDT, "5m", 12);
    }

    @Test(expected = BinanceTraderException.class)
    public void shouldThrowExceptionIfGetClosePricesThrowsServerException() {
        when(klineServiceMock.fetchKlines(any(Symbol.class), any(String.class), any(Integer.class))).thenThrow(BinanceServerException.class);
        facade.getClosePrices(Symbol.BTCUSDT, "5m", 12);
    }

    @Test(expected = BinanceClientException.class)
    public void shouldThrowExceptionIfGetClosePricesThrowsClientException() {
        when(klineServiceMock.fetchKlines(any(Symbol.class), any(String.class), any(Integer.class))).thenThrow(BinanceClientException.class);
        facade.getClosePrices(Symbol.BTCUSDT, "5m", 12);
    }

    @Test(expected = BinanceClientException.class)
    public void shouldThrowExceptionIfBuyThrowsClientException() {
        doThrow(BinanceClientException.class).when(orderServiceMock).buy(any(Symbol.class), any(Double.class), any(Double.class));
        facade.buy(Symbol.BTCUSDT, 1500., 3000.);
    }

    @Test(expected = BinanceClientException.class)
    public void shouldThrowExceptionIfSellThrowsClientException() {
        doThrow(BinanceClientException.class).when(orderServiceMock).buy(any(Symbol.class), any(Double.class), any(Double.class));
        facade.buy(Symbol.BTCUSDT, 1500., 3000.);
    }
}
