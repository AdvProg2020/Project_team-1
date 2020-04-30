package commands;

public abstract class CommandAlt {
    protected String commandRegex;

    protected boolean isCommandMatch(String input) {
        if (input.matches(commandRegex)) {
            return true;
        }
        return false;
    }

    protected abstract String runCommand(String input) throws Exception;
}
