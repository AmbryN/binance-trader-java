package com.binance.trader.classes.data;

import com.binance.trader.enums.StrategyResult;

import static com.binance.trader.enums.StrategyResult.*;

public class TransactionDecision {
    private static StrategyResult decision = HOLD;

    public static StrategyResult getDecision() {
        return decision;
    }

    public static void setDecision(StrategyResult newDecision) {
        if (decision == newDecision) return;
        if (decision == BUY && newDecision == SELL
                || decision == SELL && newDecision == BUY) decision = HOLD;
        else decision = newDecision;
    }
}
