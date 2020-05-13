package old.commands;

import model.Category;
import model.DataManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RemoveCategory extends Command {
    public RemoveCategory() {
        super.regex = "remove (?<category>\\w+)";
    }

    @Override
    public String runCommand(String command) throws Exception {
        Matcher matcher = Pattern.compile(this.regex).matcher(command);
        if (!DataManager.isThereAnyCategoryWithThisName(matcher.group("category"))) {
            return "no category with this name";
        }
        Category category = DataManager.getCategoryByName(matcher.group("category"));
        DataManager.getAllCategory().remove(category);
        return "category removed successfully";
    }
}
