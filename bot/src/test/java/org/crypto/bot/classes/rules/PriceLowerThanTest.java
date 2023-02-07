package org.crypto.bot.classes.rules;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.Matchers.array;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;

class PriceLowerThanTest {
    @Mock Rule ruleMock;
    PriceLowerThan rule;
    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        rule = new PriceLowerThan(1000);
    }
    @Test
    void isNotSatisfiedWhenGivenHigherPriceThanValue() {
        assertTrue(rule.isSatisfied(999, new double[0]));
    }

    @Test
    void isSatisfedWhenGivenLowerPriceThanValue() {
        assertFalse(rule.isSatisfied(1001, new double[0]));
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