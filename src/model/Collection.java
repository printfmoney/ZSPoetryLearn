package model;

public class Collection {
    private String userName;
    private String poetryId;

    public Collection() {

    }

    public Collection(String userName, String poetryId) {
        this.userName = userName;
        this.poetryId = poetryId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setPoetryId(String poetryId) {
        this.poetryId = poetryId;
    }

    public String getPoetryId() {
        return poetryId;
    }
}
