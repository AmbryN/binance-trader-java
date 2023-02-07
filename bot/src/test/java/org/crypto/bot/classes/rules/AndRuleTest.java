package org.crypto.bot.classes.rules;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class AndRuleTest {

    @Mock Rule first;
    @Mock Rule second;
    AndRule rule;
    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        rule = new AndRule(first, second);
    }
    @Test
    void isSatisfiedWhenBothRulesAreSatisfied() {
        when(first.isSatisfied(anyDouble(), any())).thenReturn(true);
        when(second.isSatisfied(anyDouble(), any())).thenReturn(true);
        assertTrue(rule.isSatisfied(0, new double[0]));
    }

    @Test
    void isNotSatisfedWhenOneRuleNotSatisfied() {
        when(first.isSatisfied(anyDouble(), any())).thenReturn(true);
        when(second.isSatisfied(anyDouble(), any())).thenReturn(false);
        assertFalse(rule.isSatisfied(0, new double[0]));
    }

    @Test
    void isNotSatisfedWhenBothRulesNotSatisfied() {
        when(first.isSatisfied(anyDouble(), any())).thenReturn(false);
        when(second.isSatisfied(anyDouble(), any())).thenReturn(false);
        assertFalse(rule.isSatisfied(0, new double[0]));
    }

    @Test
    void shouldReturnZeroRecordsToFetch() {
        when(first.getNbOfRecordsToFetch()).thenReturn(26);
        when(second.getNbOfRecordsToFetch()).thenReturn(12);
        assertEquals(26, rule.getNbOfRecordsToFetch());
    }

    @Test
    void andShouldReturnAnAndRule() {
        assertInstanceOf(AndRule.class, rule.and(first));
    }

    @Test
    void orShouldReturnAnOrRule() {
        assertInstanceOf(OrRule.class, rule.or(first));
    }
}