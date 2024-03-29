package org.crypto.bot.classes.data;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class KlineTest {

    @Test
    public void identicalKlinesShouldBeEqual() {
        Kline kline = new Kline();
        assertEquals(kline, kline);
    }

    @Test
    public void shouldReturnFalseIfOfDifferentTypeOrNull() {
        Kline kline = new Kline();
        Object object = new Object();
        assertNotEquals(kline, object);
        assertNotEquals(null, kline);
    }

    @Test
    public void shouldReturnClosePrice() {
        Kline kline = new Kline(18.2856);
        assertEquals(18.2856, kline.getClosePrice(), 0.0001);
    }
}
