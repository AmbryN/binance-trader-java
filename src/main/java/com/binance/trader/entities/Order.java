package com.binance.trader.entities;

import com.binance.trader.enums.ORDER_SIDE;
import com.binance.trader.enums.ORDER_TYPE;

public class Order {
    String symbol; 	
    ORDER_SIDE side; 	
    ORDER_TYPE type;
    Float quantity;
    Float quoteOrderQty;

    Order(String symbol, ORDER_SIDE side, ORDER_TYPE type, Float quantity, Float quoteOrderQty) {
        this.symbol = symbol;
        this.side = side;
        this.type = type;
        this.quantity = quantity;
        this.quoteOrderQty = quoteOrderQty;
    }
}
