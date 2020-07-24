package client.controller.manager;

import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import common.model.account.BusinessAccount;
import common.model.account.ManagerAccount;
import common.model.account.PersonalAccount;
import common.model.account.SupportAccount;
import client.controller.share.Menu;
import server.dataManager.YaDataManager;

import java.io.IOException;

import static client.Main.outputStream;

public class ManageUsersPanel extends Menu {
    private static final YaGson yaGson = new YaGsonBuilder().setPrettyPrinting().create();

    public ManageUsersPanel() {
        fxmlFileAddress = "../../../fxml/HolyManager/ManageUsers.fxml";
    }

    public void createNewManager(ManagerAccount managerAccount) throws Exception {
        if (YaDataManager.isUsernameExist(managerAccount.getUsername())) {
            throw new Exception("Invalid username");
        }
        if (checkEmail(managerAccount.getEmail())) {
            throw new Exception("This email is unavailable");
        }
        outputStream.writeUTF("add manager " + yaGson.toJson(managerAccount, new TypeToken<ManagerAccount>() {
        }.getType()));
        outputStream.flush();
    }

    private boolean checkEmail(String email) throws IOException {
        for (ManagerAccount manager : YaDataManager.getManagers()) {
            if (manager.getEmail().equals(email)) {
                return true;
            }
        }
        for (PersonalAccount personalAccount : YaDataManager.getPersons()) {
            if (personalAccount.getEmail().equals(email))
                return true;
        }
        for (BusinessAccount reseller : YaDataManager.getBusinesses()) {
            if (reseller.getEmail().equals(email)) {
                return true;
            }
        }

        for (SupportAccount support : YaDataManager.getSupports()) {
            if (support.getEmail().equalsIgnoreCase(email))
                return true;
        }

        return false;
    }
}
