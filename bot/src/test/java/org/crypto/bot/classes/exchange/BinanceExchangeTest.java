package org.crypto.bot.classes.exchange;

import com.binance.connector.client.exceptions.BinanceClientException;
import com.binance.connector.client.exceptions.BinanceConnectorException;
import com.binance.connector.client.exceptions.BinanceServerException;
import org.crypto.bot.enums.Period;
import org.crypto.bot.enums.Symbol;
import org.crypto.bot.exceptions.BinanceTraderException;
import org.crypto.bot.services.AccountInfoService;
import org.crypto.bot.services.KlineService;
import org.crypto.bot.services.OrderService;
import org.crypto.bot.services.TickerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

public class BinanceExchangeTest {

    @Mock
    AccountInfoService accountInfoServiceMock;
    @Mock
    TickerService tickerServiceMock;

    @Mock
    OrderService orderServiceMock;

    @Mock
    KlineService klineServiceMock;

    @InjectMocks
    BinanceExchange facade;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldThrowExceptionIfGetBalancesThrowsAConnectorException() {
        when(accountInfoServiceMock.getAccountInfo()).thenThrow(BinanceConnectorException.class);
        assertThrows(BinanceTraderException.class,
                () -> facade.getBaseAndQuoteBalances(Symbol.BTCUSDT));
    }

    @Test
    public void shouldThrowExceptionIfGetBalancesThrowsAServerException() {
        when(accountInfoServiceMock.getAccountInfo()).thenThrow(BinanceServerException.class);
        assertThrows(BinanceTraderException.class,
                () -> facade.getBaseAndQuoteBalances(Symbol.BTCUSDT));
    }

    @Test
    public void shouldThrowExceptionIfGetBalancesThrowsAClientException() {
        when(accountInfoServiceMock.getAccountInfo()).thenThrow(BinanceClientException.class);
        assertThrows(BinanceClientException.class,
                () -> facade.getBaseAndQuoteBalances(Symbol.BTCUSDT));
    }

    @Test
    public void shouldThrowExceptionIfGetTickerThrowsConnectorException() {
        when(tickerServiceMock.getTicker(any(Symbol.class))).thenThrow(BinanceConnectorException.class);
        assertThrows(BinanceTraderException.class,
                () -> facade.getTicker(Symbol.BTCUSDT));
    }

    @Test
    public void shouldThrowExceptionIfGetTickerThrowsServerException() {
        when(tickerServiceMock.getTicker(any(Symbol.class))).thenThrow(BinanceServerException.class);
        assertThrows(BinanceTraderException.class,
                () -> facade.getTicker(Symbol.BTCUSDT));
    }

    @Test
    public void shouldThrowExceptionIfGetTickerThrowsClientException() {
        when(tickerServiceMock.getTicker(any(Symbol.class))).thenThrow(BinanceClientException.class);
        assertThrows(BinanceClientException.class,
                () -> facade.getTicker(Symbol.BTCUSDT));
    }

    @Test
    public void shouldThrowExceptionIfGetClosePricesThrowsConnectorException() {
        when(klineServiceMock.fetchKlines(any(Symbol.class), any(String.class), any(Integer.class))).thenThrow(BinanceConnectorException.class);
        assertThrows(BinanceTraderException.class,
                () -> facade.getClosePrices(Symbol.BTCUSDT, Period.FiveMinutes, 12));
    }

    @Test
    public void shouldThrowExceptionIfGetClosePricesThrowsServerException() {
        when(klineServiceMock.fetchKlines(any(Symbol.class), any(String.class), any(Integer.class))).thenThrow(BinanceServerException.class);
        assertThrows(BinanceTraderException.class,
                () -> facade.getClosePrices(Symbol.BTCUSDT, Period.FiveMinutes, 12));
    }

    @Test
    public void shouldThrowExceptionIfGetClosePricesThrowsClientException() {
        when(klineServiceMock.fetchKlines(any(Symbol.class), any(String.class), any(Integer.class))).thenThrow(BinanceClientException.class);
        assertThrows(BinanceClientException.class,
                () -> facade.getClosePrices(Symbol.BTCUSDT, Period.FiveMinutes, 12));
    }

    @Test
    public void shouldThrowExceptionIfBuyThrowsClientException() {
        doThrow(BinanceClientException.class).when(orderServiceMock).buy(any(Symbol.class), any(Double.class));
        assertThrows(BinanceClientException.class,
                () -> facade.buy(Symbol.BTCUSDT,  3000.));
    }

    @Test
    public void shouldThrowExceptionIfSellThrowsClientException() {
        doThrow(BinanceClientException.class).when(orderServiceMock).buy(any(Symbol.class), any(Double.class));
        assertThrows(BinanceClientException.class,
                () -> facade.buy(Symbol.BTCUSDT, 3000.));
    }
}
