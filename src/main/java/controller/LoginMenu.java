package controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginMenu extends Menu{

    public int login(String input) throws Exception{
        Matcher matcher = Pattern.compile("login (?<username>\\S+) (?<password>\\S+)").matcher(input);

        return 0;
    }
}
