package com.binance.trader.entities;

import com.binance.trader.enums.OrderSide;
import com.binance.trader.enums.OrderType;

public class Order {
    String symbol; 	
    OrderSide side; 	
    OrderType type;
    Float quantity;
    Float quoteOrderQty;

    Order(String symbol, OrderSide side, OrderType type, Float quantity, Float quoteOrderQty) {
        this.symbol = symbol;
        this.side = side;
        this.type = type;
        this.quantity = quantity;
        this.quoteOrderQty = quoteOrderQty;
    }
}
