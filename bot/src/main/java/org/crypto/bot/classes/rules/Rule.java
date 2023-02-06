package org.crypto.bot.classes.rules;

import org.crypto.bot.classes.indicators.Indicator;

public interface Rule {
    void setFirstIndicator(Indicator indicator);
    void setSecondIndicator(Indicator indicator);
    boolean isSatisfied(double[] prices);

}
