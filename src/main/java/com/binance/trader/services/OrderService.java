package com.binance.trader.services;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;

import ch.qos.logback.classic.Logger;
import com.binance.trader.enums.OrderResponseType;
import com.binance.trader.classes.data.OrderResult;
import com.binance.trader.classes.singleton.Logging;
import com.binance.trader.enums.OrderSide;
import com.binance.trader.enums.OrderType;
import com.binance.trader.enums.Symbol;
import com.binance.trader.enums.TimeInForce;

import com.binance.connector.client.exceptions.BinanceClientException;
import com.binance.connector.client.exceptions.BinanceConnectorException;
import com.binance.connector.client.impl.SpotClientImpl;
import com.binance.trader.classes.data.Order;
import com.binance.trader.utils.Deserializer;

public class OrderService {
    private final SpotClientImpl client;
    private static final Logger logger = Logging.getInstance();

    public OrderService(SpotClientImpl client) {
        this.client =  client;
    }

    public void sendOrder(Order order) {
        LinkedHashMap<String, Object> parameters = order.asParams();
        try {
            String result = this.client.createTrade().newOrder(parameters);
            logger.info(result);
            OrderResult resultObject = Deserializer.deserialize(result, OrderResult.class);
            this.logToFile(resultObject.toLog());
        } catch (BinanceConnectorException e) {
            logger.error("fullErrMessage: {}", e.getMessage(), e);
        } catch (BinanceClientException e) {
            logger.error("fullErrMessage: {} \nerrMessage: {} \nerrCode: {} \nHTTPStatusCode: {}", 
            e.getMessage(), e.getErrMsg(), e.getErrorCode(), e.getHttpStatusCode(), e);
        }
    }

    public void buy(Symbol symbol, double tickerPrice, double quoteBalance) {
        double baseQuantity = Math.floor(quoteBalance / tickerPrice * symbol.MIN_BASE_MOVEMENT) / symbol.MIN_BASE_MOVEMENT;

        Order order = new Order();
        order.setSymbol(symbol);
        order.setSide(OrderSide.BUY);
        order.setType(OrderType.LIMIT);
        order.setTimeInForce(TimeInForce.IOC);
        order.setPrice(tickerPrice);
        order.setQuantity(baseQuantity);
        order.setNewOrderRespType(OrderResponseType.RESULT);

        this.sendOrder(order);
    }

    public void sell(Symbol symbol, double tickerPrice, double baseBalance) {
        double baseQuantity = Math.floor(baseBalance * symbol.MIN_BASE_MOVEMENT) / symbol.MIN_BASE_MOVEMENT;

        Order order = new Order();
        order.setSymbol(symbol);
        order.setSide(OrderSide.SELL);
        order.setType(OrderType.LIMIT);
        order.setTimeInForce(TimeInForce.IOC);
        order.setPrice(tickerPrice);
        order.setQuantity(baseQuantity);
        order.setNewOrderRespType(OrderResponseType.RESULT);

        this.sendOrder(order);
    }

    public void logToFile(String data) {
        File file = new File("orderLog.txt");
        try {
            file.createNewFile();
        } catch (IOException e) {
            System.out.println("Could not create file");
        }
        FileWriter fr = null;
        BufferedWriter br = null;
        try{
            fr = new FileWriter(file, true);
            br = new BufferedWriter(fr);
            br.write(data);
            br.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                br.close();
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
