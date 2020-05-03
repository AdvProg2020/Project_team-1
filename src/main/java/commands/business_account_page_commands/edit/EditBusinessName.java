package commands.business_account_page_commands.edit;

import commands.CommandAlt;
import controller.SystemInScanner;
import model.account.BusinessAccount;
import model.account.SimpleAccount;

import java.util.Scanner;

public class EditBusinessName extends CommandAlt {
    public EditBusinessName() {
        commandRegex = "^edit businessName$";
    }

    @Override
    protected String runCommand(SimpleAccount requestSender, String input) throws Exception {
        Scanner scanner = SystemInScanner.getScanner();
        System.out.println("Enter new business name : ");
        String newBusinessName = scanner.nextLine();
        ((BusinessAccount)requestSender).changeBusinessName(newBusinessName);
        return "Business name successfully changed";
    }
}
