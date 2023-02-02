package org.crypto.bot.classes.selectors;

import org.crypto.bot.enums.StrategyName;

public class StrategyListSelector extends ListSelector<StrategyName> {

    public StrategyListSelector() {
        this.list = StrategyName.values();
    }

    @Override
    protected void showSelector() {
        System.out.println("What strategy do you want to use? ");
        int index = 0;
        for (StrategyName strategy : list) {
            System.out.println(index + ") " + strategy.toString());
            index++;
        }
    }
}
