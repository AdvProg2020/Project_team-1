package old.commands;

public abstract class Command {
    protected String regex;

    public boolean checkCommand(String command) {
        return command.matches(regex);
    }

    public abstract String runCommand(String command , ) throws Exception;
}
