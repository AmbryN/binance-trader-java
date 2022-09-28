package com.binance.trader.entities;

import static org.junit.Assert.assertTrue;

import java.util.LinkedHashMap;

import org.junit.Before;
import org.junit.Test;

import com.binance.trader.enums.OrderSide;
import com.binance.trader.enums.OrderType;
import com.binance.trader.enums.Symbol;
import com.binance.trader.enums.TimeInForce;

public class OrderTest {
    
    Order order;

    @Before
    public void setup() {
        order = new Order();
    }

    @Test
    public void shoudReturnValidParams() {
        order.setSymbol(Symbol.BTCUSDT);
        order.setSide(OrderSide.BUY);
        order.setType(OrderType.LIMIT);
        order.setTimeInForce(TimeInForce.IOC);
        order.setPrice(2.0);

        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
        parameters.put("symbol", "BTCUSDT");
        parameters.put("side", "BUY");
        parameters.put("type", "LIMIT");
        parameters.put("timeInForce", "IOC");
        parameters.put("symbol", "BTCUSDT");
        parameters.put("price", "2.0");

        assertTrue(parameters.equals(order.asParams()));
    }

    @Test
    public void shouldReturnEmptyLinkedHashMapIfNoPropertiesSet() {
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();

        assertTrue(parameters.equals(order.asParams()));
    }
}
