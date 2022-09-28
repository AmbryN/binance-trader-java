package com.binance.trader.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.LinkedHashMap;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.binance.connector.client.exceptions.BinanceClientException;
import com.binance.connector.client.exceptions.BinanceConnectorException;
import com.binance.connector.client.impl.SpotClientImpl;
import com.binance.connector.client.impl.spot.Trade;
import com.binance.trader.entities.AccountInfo;
import com.binance.trader.entities.Balance;
import com.binance.trader.exceptions.BinanceTraderException;

public class AccountInfoServiceTest {
    @Mock SpotClientImpl clientMock;
    @Mock Trade tradeMock;

    @InjectMocks AccountInfoService accountInfoService;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldReturnTheAccountInfo() {
        String message = """
            {
            \"makerCommission\": 15,
            \"takerCommission\": 15,
            \"buyerCommission\": 0,
            \"sellerCommission\": 0,
            \"canTrade\": true,
            \"canWithdraw\": true,
            \"canDeposit\": true,
            \"brokered\": false,
            \"updateTime\": 123456789,
            \"accountType\": \"SPOT\",
            \"balances\": [
              {
                \"asset\": \"BTC\",
                \"free\": \"4723846.89208129\",
                \"locked\": \"0.00000000\"
              },
              {
                \"asset\": \"LTC\",
                \"free\": \"4763368.68006011\",
                \"locked\": \"0.00000000\"
              }
            ],
            \"permissions\": [
              \"SPOT\"
            ]
          }
          """;
        when(clientMock.createTrade()).thenReturn(tradeMock);
        when(tradeMock.account(any(LinkedHashMap.class))).thenReturn(message);
        
        AccountInfo accountInfo = accountInfoService.getAccountInfo();

            assertEquals(15, accountInfo.getMakerCommission());
            assertEquals(15, accountInfo.getTakerCommission());
            assertEquals(0, accountInfo.getBuyerCommission());
            assertEquals(0, accountInfo.getSellerCommission());
            assertEquals(true, accountInfo.isCanTrade());
            assertEquals(true, accountInfo.isCanWithdraw());
            assertEquals(true, accountInfo.isCanDeposit());
            assertEquals(false, accountInfo.isBrokered());
            assertTrue(accountInfo.getUpdateTime().equals(123456789L));
            assertTrue("SPOT".equals(accountInfo.getAccountType()));
            assertTrue(Arrays.deepEquals(new Balance[]{
                new Balance("BTC", 4723846.89208129, 0.00000000), 
                new Balance("LTC", 4763368.68006011, 0.00000000)
            }, (accountInfo.getBalances())));
            assertTrue(Arrays.deepEquals(new String[]{"SPOT"}, accountInfo.getPermissions()));      
    }

    @Test(expected = BinanceTraderException.class)
    public void shouldThrowExceptionIfConnectorException() {
        when(clientMock.createTrade()).thenReturn(tradeMock);
        when(tradeMock.account(any(LinkedHashMap.class))).thenThrow(BinanceConnectorException.class);
        accountInfoService.getAccountInfo();
    }

    @Test(expected = BinanceTraderException.class)
    public void shouldThrowExceptionIfClintException() {
        when(clientMock.createTrade()).thenReturn(tradeMock);
        when(tradeMock.account(any(LinkedHashMap.class))).thenThrow(BinanceClientException.class);
        accountInfoService.getAccountInfo();
    }

}