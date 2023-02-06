package org.crypto.bot.classes.selectors;

import org.crypto.bot.enums.IndicatorEnum;

public class IndicatorSelector extends ListSelector<IndicatorEnum> {

    public IndicatorSelector() {
        this.list = IndicatorEnum.values();
    }

    @Override
    protected void showSelector() {
        System.out.println("What indicator do you want to use? ");
        int index = 0;
        for (IndicatorEnum value : list) {
            System.out.println(index + ") " + value);
            index++;
        }
    }
}
