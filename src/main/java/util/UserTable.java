package util;

public class UserTable {
    private Integer id;
    private String userName;
    private String password;
    private String sex;
    private Integer isAdmin;

    public UserTable(Integer id, String userName, String password, String sex, Integer isAdmin) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.sex = sex;
        this.isAdmin = isAdmin;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Integer isAdmin) {
        this.isAdmin = isAdmin;
    }
}