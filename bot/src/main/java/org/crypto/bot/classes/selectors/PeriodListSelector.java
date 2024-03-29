package org.crypto.bot.classes.selectors;

import org.crypto.bot.enums.Period;

public class PeriodListSelector extends ListSelector<Period> {

    public PeriodListSelector() {
        this.list = Period.values();
    }
    @Override
    protected void showSelector() {
        System.out.println("What should be the base period used for computation? ");
        int index = 0;
        for (Period period : list) {
            System.out.println(index + ") " + period.name() + ": " + period.toString());
            index++;
        }
    }
}
