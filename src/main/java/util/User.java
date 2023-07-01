package util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String password;
    private String username;
    private Integer id;
    private Integer isAdmin;
    private String sex;

    public User(String username, String password, String sex, Integer isAdmin) {
        this.username = username;
        this.password = password;
        this.sex = sex;
        this.isAdmin = isAdmin;
    }

    public User(String username, String password, String sex, Integer isAdmin, Integer id) {
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

    public User(String username, String password) {
        super();
        this.username = username;
        this.password = password;
    }
}