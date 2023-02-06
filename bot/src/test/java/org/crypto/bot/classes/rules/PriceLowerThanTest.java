package org.crypto.bot.classes.rules;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PriceLowerThanTest {

    PriceLowerThan rule;
    @BeforeEach
    void setup() {
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
}