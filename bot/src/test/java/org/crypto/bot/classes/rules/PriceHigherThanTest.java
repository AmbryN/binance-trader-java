package org.crypto.bot.classes.rules;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class PriceHigherThanTest {
    @Mock
    Rule ruleMock;
    PriceHigherThan rule;
    @BeforeEach
    void setup() {
        rule = new PriceHigherThan(1000);
    }
    @Test
    void isSatisfiedWhenGivenHigherPriceThanValue() {
        assertTrue(rule.isSatisfied(1001, new double[0]));
    }

    @Test
    void isNotSatisfedWhenGivenLowerPriceThanValue() {
        assertFalse(rule.isSatisfied(999, new double[0]));
    }

    @Test
    void shouldReturnZeroRecordsToFetch() {
        assertEquals(0, rule.getNbOfRecordsToFetch());
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