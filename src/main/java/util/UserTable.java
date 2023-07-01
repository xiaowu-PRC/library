package util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserTable {
    private Integer id;
    private String userName;
    private String password;
    private String sex;
    private Integer isAdmin;
}