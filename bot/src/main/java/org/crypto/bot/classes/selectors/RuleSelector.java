package org.crypto.bot.classes.selectors;

public class RuleSelector extends ListSelector<String> {

    public RuleSelector() {
        this.list = new String[] {
                "OverIndicator",
                "UnderIndicator"
        };
    }

    @Override
    protected void showSelector() {
        System.out.println("What rule do you want to use? ");
        int index = 0;
        for (String rule : list) {
            System.out.println(index + ") " + rule);
            index++;
        }
    }
}
