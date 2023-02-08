package org.crypto.bot.classes.indicators;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConstantIndicatorTest {

    ConstantIndicator constantIndicator;
    @BeforeEach
    void setup () {
        constantIndicator = new ConstantIndicator(100.25);
    }

    @Test
    void shouldReturnTheValueItWasCreatedWith() {
        double[] prices = new double[10];
        assertEquals(100.25, constantIndicator.getLastValue(prices));
    }

    @Test
    void getNbOfRecordsToFetch() {
        assertEquals(0, constantIndicator.getNbOfRecordsToFetch());
    }
}