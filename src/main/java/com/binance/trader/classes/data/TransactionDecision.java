package com.binance.trader.classes.data;

import com.binance.trader.enums.StrategyResult;

import java.util.concurrent.atomic.AtomicReference;

import static com.binance.trader.enums.StrategyResult.*;

public class TransactionDecision {
    private static final AtomicReference<StrategyResult> decision = new AtomicReference<>(HOLD);

    public static StrategyResult getDecision() {
        return decision.get();
    }

    public static void setDecision(StrategyResult newDecision) {
        if (decision.get() == newDecision) return;
        if (decision.get() == BUY && newDecision == SELL
                || decision.get() == SELL && newDecision == BUY) decision.set(HOLD);
        else decision.set(newDecision);
    }
}
