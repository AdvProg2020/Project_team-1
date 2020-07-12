package common.model.commodity;

import common.model.account.PersonalAccount;
import common.model.account.SimpleAccount;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class DiscountCode implements Serializable {
    private String code;
    private Date startDate;
    private Date finishDate;
    private int discountPercentage;
    private int maximumDiscountPrice;
    private int maximumNumberOfUses;
    private ArrayList<String> accountsUsername;

    public DiscountCode(String code, Date startDate, Date finishDate, int discountPercentage, int maximumDiscountPrice,
                        int maximumNumberOfUses, ArrayList<String> accountsUsername) throws Exception {
        if (finishDate.compareTo(startDate) < 0) {
            throw new Exception("dates are not valid");
        }
        setMaximumNumberOfUses(maximumNumberOfUses);
        setDiscountPercentage(discountPercentage);
        setMaximumDiscountPrice(maximumDiscountPrice);
        this.code = code;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.accountsUsername = accountsUsername;
    }

    public String getCode() {
        return code;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) throws Exception {
        if (finishDate.compareTo(startDate) < 0) {
            throw new Exception("dates are not valid");
        }
        this.startDate = startDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) throws Exception {
        if (finishDate.compareTo(startDate) < 0) {
            throw new Exception("dates are not valid");
        }
        this.finishDate = finishDate;
    }

    public int getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(int discountPercentage) throws Exception {
        if (discountPercentage < 1) {
            throw new Exception("not a valid percentage");
        }
        this.discountPercentage = discountPercentage;
    }

    public int getMaximumDiscountPrice() {
        return maximumDiscountPrice;
    }

    public void setMaximumDiscountPrice(int maximumDiscountPrice) throws Exception {
        if (maximumDiscountPrice <= 0) {
            throw new Exception("not a valid price");
        }
        this.maximumDiscountPrice = maximumDiscountPrice;
    }

    public int getMaximumNumberOfUses() {
        return maximumNumberOfUses;
    }

    public void setMaximumNumberOfUses(int maximumNumberOfUses) throws Exception {
        if (maximumNumberOfUses <= 0) {
            throw new Exception("not a valid number");
        }
        this.maximumNumberOfUses = maximumNumberOfUses;
    }

    public ArrayList<String> getAccountsUsername() {
        return accountsUsername;
    }

    public void setAccountsUsername(ArrayList<String> accountsUsername) {
        this.accountsUsername = accountsUsername;
    }

    public void deleteAccount(SimpleAccount simpleAccount) {
        for (String username : accountsUsername) {
            if (username.equals(simpleAccount.getUsername())) {
                accountsUsername.remove(simpleAccount.getUsername());
                return;
            }
        }

    }

    public void addAccount(PersonalAccount simpleAccount) {
        accountsUsername.add(simpleAccount.getUsername());
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void isActive() throws Exception {
        Date now = new Date();
        if (this.startDate.compareTo(now) <= 0 && this.finishDate.compareTo(now) >= 0) {
            return;
        }
        throw new Exception("you can't use this code right now");
    }

    public boolean isOnTime() {
        Date now = new Date();
        return this.startDate.compareTo(now) <= 0 && this.finishDate.compareTo(now) >= 0;
    }

    @Override
    public String toString() {
        StringBuilder accountsUserName = new StringBuilder();
        for (String username : accountsUsername) {
            accountsUserName.append(username);
            accountsUserName.append('-');
        }
        return "DiscountCode{" +
                "code='" + code + '\'' +
                ", startDate=" + startDate.toString() +
                ", finishDate=" + finishDate.toString() +
                ", discountPercentage=" + discountPercentage +
                ", maximumDiscountPrice=" + maximumDiscountPrice +
                ", maximumNumberOfUses=" + maximumNumberOfUses +
                ", accounts=" + accountsUserName.toString() +
                '}';
    }

    public String getInformation() {
        return "code = " + code +
                ", startDate = " + startDate +
                ", finishDate = " + finishDate +
                ", discountPercentage = " + discountPercentage +
                ", maximumDiscountPrice = " + maximumDiscountPrice +
                ", maximumNumberOfUses = " + maximumNumberOfUses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DiscountCode)) return false;
        DiscountCode code1 = (DiscountCode) o;
        return Objects.equals(code, code1.code);
    }
}
