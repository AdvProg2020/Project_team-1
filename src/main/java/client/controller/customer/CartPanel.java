package client.controller.customer;

import client.Session;
import client.controller.share.Menu;
import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import com.google.gson.reflect.TypeToken;
import common.model.account.PersonalAccount;
import common.model.commodity.DiscountCode;

import static client.Main.inputStream;
import static client.Main.outputStream;

public class CartPanel extends Menu {
    private static final YaGson yaGson = new YaGsonBuilder().setPrettyPrinting().create();

    public CartPanel() {
        fxmlFileAddress = "../../../fxml/customer/Cart.fxml";
    }

    public void decrease(int id) throws Exception {
        PersonalAccount personalAccount = (PersonalAccount) Session.getOnlineAccount();
        personalAccount.removeFromCart(id);
        outputStream.writeUTF("remove from cart " + id);
        outputStream.flush();
    }

    public void increase(int id) throws Exception {
        PersonalAccount personalAccount = (PersonalAccount) Session.getOnlineAccount();
        personalAccount.addToCart(id);
        outputStream.writeUTF("add to cart " + id);
        outputStream.flush();
    }

    public DiscountCode getDiscountCodeWithCode(String code, String username) throws Exception {
        outputStream.writeUTF("send person with username " + username);
        outputStream.flush();
        PersonalAccount account = yaGson.fromJson(inputStream.readUTF(), new TypeToken<PersonalAccount>() {
        }.getType());
        if (!code.equals("")) {
            outputStream.writeUTF("send discount with code " + code);
            outputStream.flush();
            DiscountCode discountCode = yaGson.fromJson(inputStream.readUTF(), new TypeToken<DiscountCode>() {
            }.getType());
            account.doesHaveThisDiscount(discountCode);
            discountCode.isActive();
            account.useThisDiscount(discountCode);
            outputStream.writeUTF("update person " + yaGson.toJson(Session.getOnlineAccount(), new TypeToken<PersonalAccount>() {
            }.getType()));
            outputStream.flush();
            return discountCode;
        }
        return null;
    }
}
