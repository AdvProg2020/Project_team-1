package commands;

import model.SuperMarket;

public class ShowAvailableFiltersCommand extends Command {

    private boolean isThisCommandAvailable;

    public boolean isThisCommandAvailable() {
        return isThisCommandAvailable;
    }

    public void setThisCommandAvailable(boolean thisCommandAvailable) {
        isThisCommandAvailable = thisCommandAvailable;
    }

    public ShowAvailableFiltersCommand(String regex) {
        super(regex);
    }

    @Override
    public String runCommand(String command) {
        if (isThisCommandAvailable)
            return "category, name, category specifications";
        return "invalid command";
    }
}
