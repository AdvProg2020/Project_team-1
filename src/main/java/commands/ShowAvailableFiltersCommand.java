package commands;

public class ShowAvailableFiltersCommand extends Command {

    private boolean isThisCommandAvailable;

    public ShowAvailableFiltersCommand() {
        super.regex = "$show available filters^";
    }

    public boolean isThisCommandAvailable() {
        return isThisCommandAvailable;
    }

    public void setThisCommandAvailable(boolean thisCommandAvailable) {
        isThisCommandAvailable = thisCommandAvailable;
    }

    @Override
    public String runCommand(String command) {
        if (isThisCommandAvailable)
            return "category, name, category specifications";
        return "invalid command";
    }
}
