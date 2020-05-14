package old;

import old.commands.Command;
import model.Commodity;

import java.util.ArrayList;

public class ProductMenu implements CommandProcess {
    CommandProcess commandProcess = new CommandProcess() {
        @Override
        public String commandProcessor(String command) throws Exception {
            return null;
        }
    };


    public static ArrayList<Command> getProductMenuCommand() {
        return productMenuCommand;
    }

    private static ArrayList<Command> productMenuCommand = new ArrayList<Command>();

    public Commodity getCommodity() {
        return commodity;
    }

    public ProductMenu(Commodity commodity) {
        this.commodity = commodity;
    }

    private Commodity commodity;
    @Override
    public String commandProcessor(String command) throws Exception {
        for (Command productCommand : productMenuCommand) {
            if(productCommand.checkCommand(command))
                return productCommand.runCommand(command);
        }
        return "invalid command";
    }
}
