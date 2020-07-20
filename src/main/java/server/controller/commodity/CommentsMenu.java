package server.controller.commodity;

import client.Session;
import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import common.model.account.PersonalAccount;
import common.model.commodity.Comment;
import common.model.commodity.Commodity;
import common.model.log.BuyLog;
import common.model.share.Request;
import server.controller.share.Menu;

import java.io.IOException;

import static client.Main.outputStream;

public class CommentsMenu extends Menu {
    private static final YaGson yaGson = new YaGsonBuilder().setPrettyPrinting().create();
    private Commodity commodity;

    public void setCommodity(Commodity commodity) {
        this.commodity = commodity;
    }

    public boolean hasBoughtThisCommodity() {
        if (Session.getOnlineAccount() == null || !Session.getOnlineAccount().getAccountType().equals("personal")) {
            return false;
        }
        PersonalAccount account = (PersonalAccount) Session.getOnlineAccount();
        for (BuyLog log : account.getBuyLogs()) {
            for (int commodityId : log.getCommoditiesId()) {
                if (commodityId == this.commodity.getCommodityId()) {
                    return true;
                }
            }
        }
        return false;
    }

    public void addComment(String title, String content) throws IOException {
        Comment comment = new Comment(Session.getOnlineAccount().getUsername(), this.commodity.getCommodityId(), title, content,
                hasBoughtThisCommodity());
        Request request = new Request(comment, Session.getOnlineAccount().getUsername());
        outputStream.writeUTF("add request " + yaGson.toJson(request, new TypeToken<Request>() {
        }.getType()));
        outputStream.flush();
    }
}
