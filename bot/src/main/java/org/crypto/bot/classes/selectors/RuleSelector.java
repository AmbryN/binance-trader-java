package org.crypto.bot.classes.selectors;

import org.crypto.bot.enums.RuleEnum;

public class RuleSelector extends ListSelector<RuleEnum> {

    public RuleSelector() {
        this.list = RuleEnum.values();
    }

    @Override
    protected void showSelector() {
        System.out.println("What rule do you want to use? ");
        int index = 0;
        for (RuleEnum rule : list) {
            System.out.println(index + ") " + rule);
            index++;
        }
    }
}
