package client.controller.commodity;

import client.Session;
import client.view.commandline.View;
import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import common.model.account.PersonalAccount;
import common.model.commodity.Comment;
import common.model.commodity.Commodity;
import common.model.commodity.Score;
import common.model.log.BuyLog;
import common.model.share.Request;
import client.controller.share.Menu;
import client.controller.share.MenuHandler;

import java.io.IOException;

import static client.Main.inputStream;
import static client.Main.outputStream;

public class ProductMenu extends Menu {
    private static final YaGson yaGson = new YaGsonBuilder().setPrettyPrinting().create();
    private Commodity commodity;
    private Commodity comparingCommodity;

    public ProductMenu() {
        fxmlFileAddress = "../../../fxml/Commodity.fxml";
    }

    public Commodity getComparingCommodity() {
        return comparingCommodity;
    }

    public void setComparingCommodity(Commodity comparingCommodity) {
        this.comparingCommodity = comparingCommodity;
    }

    public Commodity getCommodity() {
        return this.commodity;
    }

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

    public void addToCart() throws Exception {
        if (Session.getOnlineAccount() == null) {
            MenuHandler.getInstance().setCurrentMenu(View.loginRegisterPanel);
            View.loginRegisterPanel.setPreviousMenu(this);
            throw new Exception("you have to login or register first");
        }
        PersonalAccount personalAccount = ((PersonalAccount) Session.getOnlineAccount());
        personalAccount.addToCart(commodity.getCommodityId());
        outputStream.writeUTF("add to cart " + commodity.getCommodityId());
        outputStream.flush();
    }

    public void rateProduct(int id, int rate) throws Exception {
        PersonalAccount account = (PersonalAccount) Session.getOnlineAccount();
        for (BuyLog log : account.getBuyLogs()) {
            for (int commodityId : log.getCommoditiesId()) {
                if (commodityId == id) {
                    outputStream.writeUTF("add score " + id + " " + rate);
                    outputStream.flush();
                    return;
                }
            }
        }
        throw new Exception("you didn't buy this product, so you can't rate it");
    }

    public boolean canRateProduct(int id) throws Exception {
        if (Session.getOnlineAccount() == null || !Session.getOnlineAccount().getAccountType().equalsIgnoreCase("personal")) {
            return false;
        }
        try {
            PersonalAccount account = (PersonalAccount) Session.getOnlineAccount();
            for (BuyLog log : account.getBuyLogs()) {
                for (int commodityId : log.getCommoditiesId()) {
                    if (commodityId == id) {
                        outputStream.writeUTF("send commodity with id " + commodityId);
                        outputStream.flush();
                        Commodity commodity = yaGson.fromJson(inputStream.readUTF(), new TypeToken<Commodity>() {
                        }.getType());
                        for (Score score : commodity.getScores()) {
                            if (score.getUsername().equals(account.getUsername())) {
                                return false;
                            }
                        }
                        return true;
                    }
                }
            }
        } catch (NullPointerException ignored) {
        }
        return false;
    }
}
