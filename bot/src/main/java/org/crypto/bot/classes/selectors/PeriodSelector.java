package org.crypto.bot.classes.selectors;

import org.crypto.bot.enums.Period;

public class PeriodSelector extends ListSelector<Period> {

    public PeriodSelector() {
        this.list = Period.values();
    }

    @Override
    protected void showSelector() {
        System.out.println("What rule do you want to use? ");
        int index = 0;
        for (Period rule : list) {
            System.out.println(index + ") " + rule);
            index++;
        }
    }
}
