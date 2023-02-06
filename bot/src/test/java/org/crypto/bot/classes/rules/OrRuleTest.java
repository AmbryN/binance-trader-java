package org.crypto.bot.classes.rules;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.when;

class OrRuleTest {

    @Mock Rule first;
    @Mock Rule second;
    OrRule rule;
    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        rule = new OrRule(first, second);
    }
    @Test
    void isSatisfiedWhenBothRulesAreSatisfied() {
        when(first.isSatisfied(anyDouble(), any())).thenReturn(true);
        when(second.isSatisfied(anyDouble(), any())).thenReturn(true);
        assertTrue(rule.isSatisfied(anyDouble(), any()));
    }

    @Test
    void isSatisfedWhenOneRuleIsSatisfied() {
        when(first.isSatisfied(anyDouble(), any())).thenReturn(true);
        when(second.isSatisfied(anyDouble(), any())).thenReturn(false);
        assertTrue(rule.isSatisfied(anyDouble(), any()));
    }

    @Test
    void isNotSatisfedWhenBothRulesNotSatisfied() {
        when(first.isSatisfied(anyDouble(), any())).thenReturn(false);
        when(second.isSatisfied(anyDouble(), any())).thenReturn(false);
        assertFalse(rule.isSatisfied(anyDouble(), any()));
    }

    @Test
    void shouldReturnZeroRecordsToFetch() {
        when(first.getNbOfRecordsToFetch()).thenReturn(26);
        when(second.getNbOfRecordsToFetch()).thenReturn(12);
        assertEquals(26, rule.getNbOfRecordsToFetch());
    }
}