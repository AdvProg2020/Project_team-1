package bank;

import java.util.Date;
import java.util.UUID;

public class AuthenticationToken {
    private int id;
    private String uuid;
    private int accountId;
    private int expired;
    private long createTime;

    public AuthenticationToken(int accountId) {
        id = 0;
        uuid = UUID.randomUUID().toString();
        this.accountId = accountId;
        expired = 0;
        createTime = System.currentTimeMillis();
    }

    public int getId() {
        return id;
    }

    public String getUuid() {
        return uuid;
    }

    public int getAccountId() {
        return accountId;
    }

    public int getExpired() {
        return expired;
    }

    public long getCreateTime() {
        return createTime;
    }
}
