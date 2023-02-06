package org.crypto.bot.classes.factory;

import org.crypto.bot.classes.indicators.EMAIndicator;
import org.crypto.bot.classes.indicators.MACDIndicator;
import org.crypto.bot.classes.indicators.SMAIndicator;
import org.crypto.bot.enums.IndicatorEnum;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IndicatorFactoryTest {

    @Test
    void shouldReturnCorrectIndicator() {
        assertInstanceOf(SMAIndicator.class, IndicatorFactory.getIndicatorBuilder(IndicatorEnum.SimpleMovingAverage));
        assertInstanceOf(EMAIndicator.class, IndicatorFactory.getIndicatorBuilder(IndicatorEnum.ExpMovingAverage));
        assertInstanceOf(MACDIndicator.class, IndicatorFactory.getIndicatorBuilder(IndicatorEnum.MACD));
    }
}