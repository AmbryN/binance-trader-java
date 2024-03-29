package org.crypto.bot.classes.selectors;

import org.crypto.bot.enums.IndicatorEnum;

public class IndicatorSelector extends ListSelector<IndicatorEnum> {

    public IndicatorSelector() {
        this.list = IndicatorEnum.values();
    }

    @Override
    protected void showSelector() {
        System.out.println("What rule do you want to use? ");
        int index = 0;
        for (IndicatorEnum rule : list) {
            System.out.println(index + ") " + rule);
            index++;
        }
    }
}
