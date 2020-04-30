package commands;

public abstract class CommandAlt {
    protected String commandRegex;

    protected boolean isCommandMatch(String input) {
        return input.matches(commandRegex);
    }

    protected abstract String runCommand(String input) throws Exception;
}
