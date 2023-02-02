package org.crypto.bot.classes.builder;

import org.crypto.bot.enums.Period;

public interface StrategyBuilder {
    void reset();
    void setPeriod(Period period);
}
