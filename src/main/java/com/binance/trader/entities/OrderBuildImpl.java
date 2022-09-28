package com.binance.trader.entities;

import com.binance.trader.enums.OrderSide;
import com.binance.trader.enums.OrderType;
import com.binance.trader.enums.Symbol;
import com.binance.trader.enums.TimeInForce;
import com.binance.trader.intefaces.OrderBuilder;

public class OrderBuildImpl implements OrderBuilder {

    private Order order;

    @Override
    public void reset() {
        this.order = new Order();
    }

    @Override
    public void setQuantity(Double quantity) {
        this.order.setQuantity(quantity);
    }

    @Override
    public void setQuoteOrderQty(Double quoteOrderQty) {
        this.order.setQuoteOrderQty(quoteOrderQty);;
    }

    @Override
    public void setPrice(Double price) {
        this.order.setPrice(price);
    }

    @Override
    public void setSide(OrderSide side) {
        this.order.setSide(side);
    }

    @Override
    public void setSymbol(Symbol symbol) {
        this.order.setSymbol(symbol);
    }

    @Override
    public void setTimeInForce(TimeInForce timeInForce) {
        this.order.setTimeInForce(timeInForce);
    }

    @Override
    public void setType(OrderType type) {
        this.order.setType(type);
    }

    public Order getResult() {
        return this.order;
    }
    
}
