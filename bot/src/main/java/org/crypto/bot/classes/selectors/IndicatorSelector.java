package org.crypto.bot.classes.selectors;

public class IndicatorSelector extends ListSelector<String> {

    public IndicatorSelector() {
        this.list = new String[] {
                "Simple Moving Average",
                "Exp. Moving Average",
                "Moving Average Convergence Divergence"
        };
    }

    @Override
    protected void showSelector() {
        System.out.println("What indicator do you want to use? ");
        int index = 0;
        for (String rule : list) {
            System.out.println(index + ") " + rule);
            index++;
        }
    }
}
