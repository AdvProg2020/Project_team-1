package model;

import model.account.SimpleAccount;

public class Score {
    private SimpleAccount account;
    private int score;
    private Commodity commodity;

    public Score(SimpleAccount account, int score, Commodity commidity) {
        this.account = account;
        this.score = score;
        this.commodity = commidity;
    }

    public SimpleAccount getAccount() {
        return account;
    }

    public int getScore() {
        return score;
    }

    public Commodity getCommodity() {
        return commodity;
    }
}
