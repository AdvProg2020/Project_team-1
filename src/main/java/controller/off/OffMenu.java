package controller.off;

import controller.comparator.Sort;
import controller.data.YaDataManager;
import controller.share.Menu;
import model.commodity.Off;

import java.util.ArrayList;

public class OffMenu extends Menu {
    public ArrayList<Off> sortOffs(String field) throws Exception {
        ArrayList<Off> offs = YaDataManager.getOffs();
        Sort.sortOffArrayList(offs, field);
        return offs;
    }

    public OffMenu() {
        fxmlFileAddress = "../../fxml/OffMenu.fxml";
    }
}
