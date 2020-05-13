package old.commands;

import model.Category;
import model.CategorySpecification;
import model.DataManager;

import java.util.HashSet;

public class EditCategoryCommand extends Command {
    public EditCategoryCommand() {
        this.regex = "^edit \\S+ \\S+ \\S+ ?\\S+";
    }

    public boolean isNumeric(String s) {
        try {
            Integer.parseInt(s);
        } catch (Exception e) {
            return false;
        }
        return true;
    }


    public boolean checkCategoryName(String name){
        return DataManager.isThereAnyCategoryWithThisName(name);
    }

    public String changeName(String name , Category category){
        category.setName(name);
        return "Category name successfully changed";
    }

    public String changeCategorySpecification(String[] splitCommand, Category category){
        String title = splitCommand[4];
        HashSet<String> options = new HashSet<String>();
        if (splitCommand[3].equals("add")){
            for (int i = 5 ; i < splitCommand.length ; i++){
                options.add(splitCommand[i]);
            }
            CategorySpecification categorySpecifications = new CategorySpecification(title , options);
            category.getFieldOptions().add(categorySpecifications);
            return "Category specification added";
        }
        if (isNumeric(splitCommand[5]) && splitCommand[4].equals("remove")) {
            category.getFieldOptions().remove(Integer.parseInt(splitCommand[5]));
            return "Category specification removed";
        }
        return "Invalid input";
    }

    @Override
    public String runCommand(String command) throws Exception {
        String[] splitCommand = command.split(" ");
        if (!checkCategoryName(splitCommand[1]))
            return "invalid name";
        Category category = DataManager.getCategoryByName(splitCommand[1]);
        if (splitCommand[2].equals("name")){
           return changeName(splitCommand[3] , category);
        }
        if (splitCommand[2].equals("category specification")){
            return changeCategorySpecification(splitCommand , category);
        }
        return "Invalid command";
    }
}
