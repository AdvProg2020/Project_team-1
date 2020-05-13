package old.commands.get_info_to_reg;

import old.commands.Command;
import old.GetAccountInfoMenu;
import controller.HandleMenu;
import model.SuperMarket;
import model.account.BusinessAccount;
import model.account.ManagerAccount;
import model.account.PersonalAccount;

import java.util.regex.Matcher;

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
                SuperMarket.addAccount(new PersonalAccount(matcher.group("username"),
                        matcher.group("firstName"), matcher.group("lastName"),
                        matcher.group("email"), matcher.group("phone"), matcher.group("password")));
            } else if (role.equals("admin")) {
                SuperMarket.addAccount(new ManagerAccount(matcher.group("username"),
                        matcher.group("firstName"), matcher.group("lastName"),
                        matcher.group("email"), matcher.group("phone"), matcher.group("password")));
            } else {
                GetAccountInfoMenu menu = (GetAccountInfoMenu) HandleMenu.getMenu();
                PersonalAccount account = new PersonalAccount(matcher.group("username"),
                        matcher.group("firstName"), matcher.group("lastName"),
                        matcher.group("email"), matcher.group("phone"),
                        matcher.group("password"))
                menu.sendBusinessAccountRequest(account, new BusinessAccount(matcher.group("username"),
                        matcher.group("firstName"), matcher.group("lastName"), account,
                        matcher.group("email"), matcher.group("phone"),
                        matcher.group("password"), matcher.group("company")));
            }
        } catch (Exception e) {
            return e.getMessage();
        }
        return "created account successfully";
    }
}
