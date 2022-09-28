package com.binance.trader.entities;

import com.binance.trader.enums.OrderSide;
import com.binance.trader.enums.OrderType;
import com.binance.trader.enums.Symbol;
import com.binance.trader.enums.TimeInForce;
import com.binance.trader.intefaces.OrderBuilder;

public class SellOrderBuilder implements OrderBuilder {

    Order sellOrder;

    SellOrderBuilder() {
        this.sellOrder = new Order();
    }

    @Override
    public void setQuantity(Double quantity) {
        this.sellOrder.setQuantity(quantity);
    }

    @Override
    public void setQuoteOrderQty(Double quoteOrderQty) {
        this.sellOrder.setQuoteOrderQty(quoteOrderQty);
    }

    @Override
    public void setPrice(Double price) {
        this.sellOrder.setPrice(price);
    }

    @Override
    public void setSide() {
        this.sellOrder.setSide(OrderSide.SELL);
    }

    @Override
    public void setSymbol(Symbol symbol) {
        this.sellOrder.setSymbol(symbol);
    }

    @Override
    public void setTimeInForce(TimeInForce timeInForce) {
        this.sellOrder.setTimeInForce(timeInForce);
    }

    @Override
    public void setType(OrderType type) {
        this.sellOrder.setType(type);
    }

    public Order getResult() {
        return this.sellOrder;
    }
    
}
