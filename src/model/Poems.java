package model;

public class Poems {
    private String name;
    private String dynastyName;

    public Poems() {

    }

    public Poems(String name, String dynastyName) {
        this.name = name;
        this.dynastyName = dynastyName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDynastyName(String dynastyName) {
        this.dynastyName = dynastyName;
    }

    public String getDynastyName() {
        return dynastyName;
    }
}
