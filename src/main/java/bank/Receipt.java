package bank;

public class Receipt {
    private String receiptType;
    private int money;
    private int sourceAccountID;
    private int destAccountID;
    private String description;
    private int id;
    private int paid;
    private transient String json;

    public Receipt(String receiptType, int money, int sourceAccountID, int destAccountID, String description) {
        this.receiptType = receiptType;
        this.money = money;
        this.sourceAccountID = sourceAccountID;
        this.destAccountID = destAccountID;
        this.description = description;
        paid = 0;
    }

    public Receipt(String receiptType, int money, int sourceAccountID, int destAccountID, String description,
                   int id, int paid, String json) {
        this.receiptType = receiptType;
        this.money = money;
        this.sourceAccountID = sourceAccountID;
        this.destAccountID = destAccountID;
        this.description = description;
        this.id = id;
        this.paid = paid;
        this.json = json;
    }

    public String getReceiptType() {
        return receiptType;
    }

    public int getMoney() {
        return money;
    }

    public int getSourceAccountID() {
        return sourceAccountID;
    }

    public int getDestAccountID() {
        return destAccountID;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPaid() {
        return paid;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }
}
