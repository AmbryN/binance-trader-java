package org.crypto.bot.classes.data;

import org.crypto.bot.enums.OrderSide;
import org.crypto.bot.enums.OrderType;
import org.crypto.bot.enums.Symbol;
import org.crypto.bot.enums.TimeInForce;

import java.time.Instant;
import java.util.Arrays;
import java.util.Date;

/**
 * Represents the result of an order sent to Binance
 */
public class OrderResult {
        private Symbol symbol;
        private Long orderId;
        private int orderListId;
        private String clientOrderId;
        private Long transactTime;
        private double price;
        private double origQty;
        private double executedQty;
        private double cummulativeQuoteQty;
        private String status;
        private TimeInForce timeInForce;
        private OrderType type;
        private OrderSide side;
        private Fill[] fills;

        public Symbol getSymbol() {
                return symbol;
        }

        public Long getTransactTime() {
                return transactTime;
        }

        public double getPrice() {
                return price;
        }

        public double getOrigQty() {
                return origQty;
        }

        public double getExecutedQty() {
                return executedQty;
        }

        public String getStatus() {
                return status;
        }

        public TimeInForce getTimeInForce() {
                return timeInForce;
        }

        public OrderType getType() {
                return type;
        }

        public OrderSide getSide() {
                return side;
        }

        @Override
        public String toString() {
                return "OrderResult{" +
                        "symbol=" + symbol +
                        ", transactTime=" + Date.from(Instant.ofEpochMilli(transactTime)) +
                        ", price=" + price +
                        ", origQty=" + origQty +
                        ", executedQty=" + executedQty +
                        ", status='" + status + '\'' +
                        ", timeInForce=" + timeInForce +
                        ", type=" + type +
                        ", side=" + side +
                        ", fills=" + Arrays.toString(fills) +
                        '}';
        }

        public String toLog() {
                return  Date.from(Instant.ofEpochMilli(transactTime)) +
                        ", " + symbol +
                        ", side=" + side +
                        ", type=" + type +
                        ", price=" + price +
                        ", orignQty=" + origQty +
                        ", executedQty=" + executedQty +
                        ", status='" + status + '\'' +
                        ", timeInForce=" + timeInForce +
                        ", fills=" + Arrays.toString(fills);

        }
}
