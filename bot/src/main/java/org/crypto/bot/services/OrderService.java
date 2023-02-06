package org.crypto.bot.services;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;

import com.binance.connector.client.exceptions.BinanceServerException;
import org.crypto.bot.enums.OrderResponseType;
import org.crypto.bot.classes.data.OrderResult;
import org.crypto.bot.enums.OrderSide;
import org.crypto.bot.enums.OrderType;
import org.crypto.bot.enums.Symbol;
import org.crypto.bot.enums.TimeInForce;

import com.binance.connector.client.exceptions.BinanceClientException;
import com.binance.connector.client.exceptions.BinanceConnectorException;
import com.binance.connector.client.impl.SpotClientImpl;
import org.crypto.bot.classes.data.Order;
import org.crypto.bot.utils.Deserializer;

public class OrderService {
    private final SpotClientImpl client;

    public OrderService(SpotClientImpl client) {
        this.client =  client;
    }

    public void buy(Symbol symbol, double quoteBalance) throws BinanceConnectorException, BinanceClientException, BinanceServerException {
        //Order order = buildLimitBuyOrder(quoteBalance, symbol, tickerPrice);
        Order order = buildMarketBuyOrder(quoteBalance, symbol);
        this.sendOrder(order);
    }

    public void sell(Symbol symbol, double baseBalance) throws BinanceConnectorException, BinanceClientException, BinanceServerException {
        // Order order = buildLimitSellOrder(baseBalance, symbol, tickerPrice);
        Order order = buildMarkerSellOrder(baseBalance, symbol);
        this.sendOrder(order);
    }

    private Order buildLimitBuyOrder(double quoteBalance, Symbol symbol, double tickerPrice) {
        double baseQuantity = Math.floor(quoteBalance * symbol.MIN_BASE_MOVEMENT) / symbol.MIN_BASE_MOVEMENT;

        Order order = new Order();
        order.setSymbol(symbol);
        order.setSide(OrderSide.BUY);
        order.setType(OrderType.LIMIT);
        order.setTimeInForce(TimeInForce.IOC);
        order.setPrice(tickerPrice);
        order.setQuantity(baseQuantity);
        order.setNewOrderRespType(OrderResponseType.RESULT);
        return order;
    }

    private Order buildLimitSellOrder(double baseBalance, Symbol symbol, double tickerPrice) {
        Order order = new Order();
        order.setSymbol(symbol);
        order.setSide(OrderSide.SELL);
        order.setType(OrderType.LIMIT);
        order.setTimeInForce(TimeInForce.IOC);
        order.setPrice(tickerPrice);
        order.setQuantity(baseBalance);
        order.setNewOrderRespType(OrderResponseType.RESULT);
        return order;
    }

    private Order buildMarketBuyOrder(double quoteBalance, Symbol symbol) {
        Order order = new Order();
        order.setSymbol(symbol);
        order.setSide(OrderSide.BUY);
        order.setType(OrderType.MARKET);
        order.setQuoteOrderQty(quoteBalance);
        order.setNewOrderRespType(OrderResponseType.FULL);
        return order;
    }

    private Order buildMarkerSellOrder(double baseBalance, Symbol symbol) {
        Order order = new Order();
        order.setSymbol(symbol);
        order.setSide(OrderSide.SELL);
        order.setType(OrderType.MARKET);
        order.setQuantity(baseBalance);
        order.setNewOrderRespType(OrderResponseType.FULL);
        return order;
    }



    private void sendOrder(Order order) throws BinanceConnectorException, BinanceClientException, BinanceServerException{
        LinkedHashMap<String, Object> parameters = order.asParams();
        String result = this.client.createTrade().newOrder(parameters);
        OrderResult resultObject = Deserializer.deserialize(result, OrderResult.class);
        this.logToFile(resultObject.toLog());
    }

    private void logToFile(String data) {
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
