package common.model.commodity;

import java.io.Serializable;

public class Score implements Serializable {
    private String username;
    private int score;
    private int commodityId;

    public Score(String username, int score, int commodityId) {
        this.username = username;
        this.score = score;
        this.commodityId = commodityId;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "Score{" +
                "account=" + username +
                ", score=" + score +
                ", commodity=" + commodityId +
                '}';
    }

    public int getScore() {
        return score;
    }

    public int getCommodityId() {
        return commodityId;
    }
}
