package com.binance.trader.entities;

import java.util.LinkedHashMap;

import com.binance.trader.enums.OrderSide;
import com.binance.trader.enums.OrderType;
import com.binance.trader.enums.Symbol;
import com.binance.trader.enums.TimeInForce;

public class Order {
    Symbol symbol; 	
    OrderSide side; 	
    OrderType type;
    TimeInForce timeInForce;
    Double price;
    Double quantity;
    Double quoteOrderQty;

    public Order() {
        this.symbol = null;
        this.side = null;
        this.type = null;
        this.timeInForce = null;
        this.price = null;
        this.quantity = null;
        this.quoteOrderQty = null;
    }

    public LinkedHashMap<String, Object> asParams() {
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
        parameters.put("symbol", this.symbol.getPair());
        parameters.put("side", this.side.toString());
        parameters.put("type", this.type.toString());
        parameters.put("timeInForce", this.timeInForce.toString());
        parameters.put("price", this.price.toString());
        if (this.quantity != null) {
            parameters.put("quantity", this.quantity.toString());
        }
        if (this.quoteOrderQty != null) {
            parameters.put("quoteOrderQty", this.quoteOrderQty.toString());
        }

        return parameters;
    } 

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public void setQuoteOrderQty(Double quoteOrderQty) {
        this.quoteOrderQty = quoteOrderQty;        
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setSide(OrderSide side) {
        this.side = side;
    }

    public void setSymbol(Symbol symbol) {
        this.symbol = symbol;
    }

    public void setTimeInForce(TimeInForce timeInForce) {
        this.timeInForce = timeInForce;
    }

    public void setType(OrderType type) {
        this.type = type;
    }
}
