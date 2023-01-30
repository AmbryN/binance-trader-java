package org.crypto.bot.classes.data;

import java.util.LinkedHashMap;

import org.crypto.bot.enums.OrderSide;
import org.crypto.bot.enums.OrderType;
import org.crypto.bot.enums.Symbol;
import org.crypto.bot.enums.TimeInForce;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderTest {
    
    Order order;

    @BeforeEach
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

        assertEquals(parameters, order.asParams());
    }

    @Test
    public void shouldReturnEmptyLinkedHashMapIfNoPropertiesSet() {
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();

        assertEquals(parameters, order.asParams());
    }
}
