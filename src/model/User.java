package model;

public class User {
    private String name;
    private String password;
    String userType;

    public User(String name) {
        this(name, "", "普通用户");
    }

    public User(String name, String password) {
        this(name, password, "普通用户");
    }

    public User(String name, String password, String  userType) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
