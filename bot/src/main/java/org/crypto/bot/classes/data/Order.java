package org.crypto.bot.classes.data;

import java.util.LinkedHashMap;

import org.crypto.bot.enums.OrderResponseType;
import org.crypto.bot.enums.Symbol;
import org.crypto.bot.enums.*;

/**
 * Represents an order on Binance
 */
public class Order {
    private Symbol symbol;
    private OrderSide side;
    private OrderType type;
    private TimeInForce timeInForce;
    private Double price;
    private Double quantity;
    private Double quoteOrderQty;

    private OrderResponseType newOrderRespType;

    public Order() {}

    public LinkedHashMap<String, Object> asParams() {
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
        if (this.symbol != null) {
            parameters.put("symbol", this.symbol.toString());
        }
        if (this.side != null) {
            parameters.put("side", this.side.toString());
        }
        if (this.type != null) {
            parameters.put("type", this.type.toString());
        }
        if (this.timeInForce != null) {
            parameters.put("timeInForce", this.timeInForce.toString());
        }
        if (this.price != null) {
            parameters.put("price", this.price.toString());
        }
        if (this.quantity != null) {
            parameters.put("quantity", this.quantity.toString());
        }
        if (this.quoteOrderQty != null) {
            parameters.put("quoteOrderQty", this.quoteOrderQty.toString());
        }
        if (this.newOrderRespType != null) {
            parameters.put("newOrderRespType", this.newOrderRespType.toString());
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

    public void setNewOrderRespType(OrderResponseType newOrderRespType) {
        this.newOrderRespType = newOrderRespType;
    }
}
