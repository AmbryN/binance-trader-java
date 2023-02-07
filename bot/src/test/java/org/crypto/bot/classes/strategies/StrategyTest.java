package org.crypto.bot.classes.strategies;

import org.crypto.bot.classes.rules.Rule;
import org.crypto.bot.enums.StrategyResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.when;

class StrategyTest {
    @Mock
    Rule entranceMock;

    @Mock
    Rule exitMock;

    Strategy strategy;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        strategy = new Strategy(entranceMock, exitMock);
    }

    @Test
    void executeReturnsBuySignalWhenEntranceRuleIsSatisfied() {
        when(entranceMock.isSatisfied(anyDouble(), any(double[].class))).thenReturn(true);
        when(exitMock.isSatisfied(anyDouble(), any(double[].class))).thenReturn(false);

        assertEquals(StrategyResult.BUY, strategy.execute(0, new double[0]));
    }

    @Test
    void executeReturnsSellSignalWhenExitRuleIsSatisfied() {
        when(entranceMock.isSatisfied(anyDouble(), any(double[].class))).thenReturn(false);
        when(exitMock.isSatisfied(anyDouble(), any(double[].class))).thenReturn(true);

        assertEquals(StrategyResult.SELL, strategy.execute(0, new double[0]));
    }


    @Test
    void executeReturnsHoldSignalWhenBothRuleAreSatisfied() {
        when(entranceMock.isSatisfied(anyDouble(), any(double[].class))).thenReturn(true);
        when(exitMock.isSatisfied(anyDouble(), any(double[].class))).thenReturn(true);

        assertEquals(StrategyResult.HOLD, strategy.execute(0, new double[0]));
    }


    @Test
    void executeReturnsHoldSignalWhenBothRuleAreNotSatisfied() {
        when(entranceMock.isSatisfied(anyDouble(), any(double[].class))).thenReturn(false);
        when(exitMock.isSatisfied(anyDouble(), any(double[].class))).thenReturn(false);

        assertEquals(StrategyResult.HOLD, strategy.execute(0, new double[0]));
    }
}