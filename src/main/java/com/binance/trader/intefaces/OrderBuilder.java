package com.binance.trader.intefaces;

import com.binance.trader.classes.Order;
import com.binance.trader.enums.OrderSide;
import com.binance.trader.enums.OrderType;
import com.binance.trader.enums.Symbol;
import com.binance.trader.enums.TimeInForce;

public interface OrderBuilder {
    public void reset();
    public void setSymbol(Symbol symbol);
    public void setSide(OrderSide side);
    public void setType(OrderType type);
    public void setTimeInForce(TimeInForce timeInForce);
    public void setPrice(Double price);
    public void setQuantity(Double quantity);
    public void setQuoteOrderQty(Double quoteOrderQty);
    public Order getResult();
}
