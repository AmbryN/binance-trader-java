package com.binance.trader.services;

import java.util.LinkedHashMap;

import com.binance.trader.classes.OrderBuildImpl;
import com.binance.trader.enums.OrderSide;
import com.binance.trader.enums.OrderType;
import com.binance.trader.enums.Symbol;
import com.binance.trader.enums.TimeInForce;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.binance.connector.client.exceptions.BinanceClientException;
import com.binance.connector.client.exceptions.BinanceConnectorException;
import com.binance.connector.client.impl.SpotClientImpl;
import com.binance.trader.PrivateConfig;
import com.binance.trader.classes.Order;

public class OrderService {
    private final SpotClientImpl client;
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

    public void buy(Symbol symbol, double tickerPrice, double quoteBalance) {
        double baseQuantity = Math.floor(quoteBalance / tickerPrice * symbol.MIN_BASE_MOVEMENT) / symbol.MIN_BASE_MOVEMENT;

        OrderBuildImpl orderBuilder = new OrderBuildImpl();
        orderBuilder.reset();
        orderBuilder.setSymbol(symbol);
        orderBuilder.setSide(OrderSide.BUY);
        orderBuilder.setType(OrderType.LIMIT);
        orderBuilder.setTimeInForce(TimeInForce.IOC);
        orderBuilder.setPrice(tickerPrice);
        orderBuilder.setQuantity(baseQuantity);
        Order order = orderBuilder.getResult();

        this.sendOrder(order);
    }

    public void sell(Symbol symbol, double tickerPrice, double baseBalance) {
        double baseQuantity = Math.floor(baseBalance * symbol.MIN_BASE_MOVEMENT) / symbol.MIN_BASE_MOVEMENT;

        OrderBuildImpl orderBuilder = new OrderBuildImpl();
        orderBuilder.reset();
        orderBuilder.setSymbol(symbol);
        orderBuilder.setSide(OrderSide.SELL);
        orderBuilder.setType(OrderType.LIMIT);
        orderBuilder.setTimeInForce(TimeInForce.IOC);
        orderBuilder.setPrice(tickerPrice);
        orderBuilder.setQuantity(baseQuantity);
        Order order = orderBuilder.getResult();

        this.sendOrder(order);
    }
}
