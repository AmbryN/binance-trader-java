package com.binance.trader.services;

import java.util.LinkedHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.binance.connector.client.exceptions.BinanceClientException;
import com.binance.connector.client.exceptions.BinanceConnectorException;
import com.binance.connector.client.impl.SpotClientImpl;
import com.binance.trader.PrivateConfig;
import com.binance.trader.classes.Order;

public class OrderService {
    private SpotClientImpl client;

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    public OrderService() {
        this.client =  new SpotClientImpl(PrivateConfig.TESTNET_API_KEY, PrivateConfig.TESTNET_SECRET_KEY, PrivateConfig.TESTNET_URL);
    }

    public void sendOrder(Order order) {
        LinkedHashMap<String, Object> parameters = order.asParams();
        try {
            String result = this.client.createTrade().newOrder(parameters);
            logger.info(result);
        } catch (BinanceConnectorException e) {
            logger.error("fullErrMessage: {}", e.getMessage(), e);
        } catch (BinanceClientException e) {
            logger.error("fullErrMessage: {} \nerrMessage: {} \nerrCode: {} \nHTTPStatusCode: {}", 
            e.getMessage(), e.getErrMsg(), e.getErrorCode(), e.getHttpStatusCode(), e);
        }
    }
}
