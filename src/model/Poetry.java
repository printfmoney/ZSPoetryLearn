package model;

public class Poetry {
    private String poetryId;
    private String title;
    private String content;
    private String poemsName;
    private String dynastyName;
    private String category;

    public  Poetry() {

    }

    public  Poetry(String poetryId, String title, String content, String poemsName, String dynastyName, String category) {
        this.poetryId = poetryId;
        this.title = title;
        this.content = content;
        this.poemsName = poemsName;
        this.dynastyName = dynastyName;
        this.category = category;
    }

    public String getPoetryId() {
        return poetryId;
    }

    public void setPoetryId(String poetryId) {
        this.poetryId = poetryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPoemsName() {
        return poemsName;
    }

    public void setPoemsName(String poemsName) {
        this.poemsName = poemsName;
    }

    public String getDynastyName() {
        return dynastyName;
    }

    public void setDynastyName(String dynastyName) {
        this.dynastyName = dynastyName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
