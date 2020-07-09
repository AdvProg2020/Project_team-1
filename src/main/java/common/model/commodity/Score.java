package common.model.commodity;

import common.model.account.SimpleAccount;

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

    @Override
    public String toString() {
        return "Score{" +
                "account=" + account.getUsername() +
                ", score=" + score +
                ", commodity=" + commodity.getName() +
                '}';
    }

    public int getScore() {
        return score;
    }

    public Commodity getCommodity() {
        return commodity;
    }
}
