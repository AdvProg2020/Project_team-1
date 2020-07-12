package server.controller.off;

import server.controller.comparator.Sort;
import server.data.YaDataManager;
import server.controller.share.Menu;
import common.model.commodity.Off;

import java.util.ArrayList;

public class OffMenu extends Menu {
    public ArrayList<Off> sortOffs(String field) throws Exception {
        ArrayList<Off> offs = YaDataManager.getOffs();
        Sort.sortOffArrayList(offs, field);
        return offs;
    }

    public OffMenu() {
        fxmlFileAddress = "../../../fxml/OffMenu.fxml";
    }
}
