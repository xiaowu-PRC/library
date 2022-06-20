package util;

public class User {
    private String password;
    private String username;
    private Integer id;
    private int isAdmin;
    private String sex;

    public User(String username, String password, String sex, int isAdmin) {
        this.username = username;
        this.password = password;
        this.sex = sex;
        this.isAdmin = isAdmin;
    }

    public User(String username, String password, String sex, int isAdmin, Integer id) {
        this.username = username;
        this.password = password;
        this.sex = sex;
        this.isAdmin = isAdmin;
        this.id = id;
    }

    public User(Integer id) {
        this.id = id;
    }

    public User(Integer id, String username) {
        this.id = id;
        this.username = username;
    }

    public User() {
        super();
    }

    public User(String username, String password) {
        super();
        this.username = username;
        this.password = password;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(int isAdmin) {
        this.isAdmin = isAdmin;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}