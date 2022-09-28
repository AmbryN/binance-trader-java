package com.binance.trader.entities;

import com.binance.trader.enums.OrderSide;
import com.binance.trader.enums.OrderType;
import com.binance.trader.enums.Symbol;
import com.binance.trader.enums.TimeInForce;
import com.binance.trader.intefaces.OrderBuilder;

public class BuyOrderBuilder implements OrderBuilder {

    Order buyOrder;

    BuyOrderBuilder() {
        this.buyOrder = new Order();
    }

    @Override
    public void setQuantity(Double quantity) {
        this.buyOrder.setQuantity(quantity);
    }

    @Override
    public void setQuoteOrderQty(Double quoteOrderQty) {
        this.buyOrder.setQuoteOrderQty(quoteOrderQty);;
    }

    @Override
    public void setPrice(Double price) {
        this.buyOrder.setPrice(price);
    }

    @Override
    public void setSide() {
        this.buyOrder.setSide(OrderSide.BUY);
    }

    @Override
    public void setSymbol(Symbol symbol) {
        this.buyOrder.setSymbol(symbol);
    }

    @Override
    public void setTimeInForce(TimeInForce timeInForce) {
        this.buyOrder.setTimeInForce(timeInForce);
    }

    @Override
    public void setType(OrderType type) {
        this.buyOrder.setType(type);
    }

    public Order getResult() {
        return this.buyOrder;
    }
    
}
