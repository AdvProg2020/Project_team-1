package commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Command {
    protected String regex;

    public String getRegex() {
        return regex;
    }

    public Command(String regex) {
        this.regex = regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    protected Pattern pattern = Pattern.compile(regex);
    protected Matcher matcher;


    public abstract boolean checkCommand(String command);
    public abstract void runCommand(String command);
}
