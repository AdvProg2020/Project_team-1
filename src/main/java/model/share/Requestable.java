package model.share;

public interface Requestable {
    void setStatus(Status status);
    void addObj() throws Exception;
    Status getStatus();
}
