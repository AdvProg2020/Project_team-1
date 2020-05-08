package commands.get_info_to_reg;

import commands.Command;
import model.account.ManagerAccount;
import model.account.PersonalAccount;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetAccountInformation extends Command {
    public static Matcher matcher;
    private String username;
    private String role;

    public GetAccountInformation(String username, String role) {
        this.username = username;
        this.role = role;
        this.regex = "(?<password>\\S+) (?<firstName>\\w+) (?<lastName>\\w+) (?<email>\\S+) (?<phone>\\d+)" +
                "?(?<company>\\w+)";
    }

    @Override
    public String runCommand(String command) {
        try {
            if (role.equals("customer")) {
                new PersonalAccount(matcher.group("username"), matcher.group("firstName"),
                        matcher.group("lastName"), matcher.group("email"), matcher.group("phone"),
                        matcher.group("password"));
            } else if (role.equals("admin")) {
                new ManagerAccount(matcher.group("username"), matcher.group("firstName"),
                        matcher.group("lastName"), matcher.group("email"), matcher.group("phone"),
                        matcher.group("password"));
            } //to make a seller account
        } catch (Exception e) {
            return e.getMessage();
        }
        return "created account successfully";
    }

    public boolean checkCommand(String command) {
        matcher = Pattern.compile(this.regex).matcher(command);
        return matcher.matches();
    }
}
