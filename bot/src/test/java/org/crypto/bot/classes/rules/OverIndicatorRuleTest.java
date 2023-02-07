package org.crypto.bot.classes.rules;

import org.crypto.bot.classes.indicators.Indicator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class OverIndicatorRuleTest {
    @Mock
    Indicator first;

    @Mock
    Indicator second;

    @Mock
    Rule ruleMock;

    OverIndicatorRule rule;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        rule = new OverIndicatorRule(first, second);
    }

    @Test
    void isSatisfiedWhenFirstIndicatorIsHigherThanSecond() {
        when(first.getValue(any())).thenReturn(1000.);
        when(second.getValue(any())).thenReturn(999.);
        boolean result = rule.isSatisfied(0., new double[0]);
        assertTrue(result);
    }

    @Test
    void isNotSatisfiedWhenFirstIndicatorIsLowerThanSecond() {
        when(first.getValue(any())).thenReturn(999.);
        when(second.getValue(any())).thenReturn(1005.);
        assertFalse(rule.isSatisfied(0., new double[0]));
    }

    @Test
    void getNbOfRecordsToFetch() {
        when(first.getNbOfRecordsToFetch()).thenReturn(26);
        when(second.getNbOfRecordsToFetch()).thenReturn(12);
        assertEquals(26, rule.getNbOfRecordsToFetch());
    }

    @Test
    void andShouldReturnAnAndRule() {
        assertInstanceOf(AndRule.class, rule.and(ruleMock));
    }

    @Test
    void orShouldReturnAnOrRule() {
        assertInstanceOf(OrRule.class, rule.or(ruleMock));
    }
}