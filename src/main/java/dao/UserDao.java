package dao;

import com.library.library.LibraryController;
import util.SHA256;
import util.StringUtil;
import util.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDao {
    public User login(Connection conn, User user) throws Exception {
        User resultUser = new User();
        String sql = "select * from user where username = ? and password = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, user.getUsername());
        String str = user.getPassword();
        str = SHA256.getSha256Str(str);
        pstmt.setString(2, str);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            resultUser = new User();
            resultUser.setId(rs.getInt("id"));
            resultUser.setUsername(rs.getString("username"));
            resultUser.setPassword(rs.getString("password"));
            resultUser.setIsAdmin(rs.getInt("isAdmin"));
        }
        return resultUser;
    }

    public int add(Connection conn, User user) throws Exception {
        String sql = "insert into user values(null,?,?,?,?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, user.getUsername());
        String str = user.getPassword();
        str = SHA256.getSha256Str(str);
        pstmt.setString(2, str);
        pstmt.setString(3, user.getSex());
        pstmt.setInt(4, user.getIsAdmin());
        return pstmt.executeUpdate();
    }

    public ResultSet list(Connection conn, User user) throws Exception {
        String nowuser = LibraryController.User_Name;
        StringBuilder sql = new StringBuilder("select * from user WHERE userName NOT IN ('" + nowuser + "')");
        if (user.getId() != null && StringUtil.isNotEmpty(user.getId().toString())) {
            sql.append(" and id = ").append(user.getId());
        }
        if (StringUtil.isNotEmpty(user.getUsername())) {
            sql.append(" and username = '").append(user.getUsername()).append("'");
        }
        sql.append(" ORDER BY id ASC");
        PreparedStatement pstmt = conn.prepareStatement(sql.toString());
        return pstmt.executeQuery();
    }


    public int delete(Connection conn, User user) throws Exception {
        String sql = "delete from user where id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, user.getId());
        return pstmt.executeUpdate();
    }

    public int update(Connection conn, User user) throws Exception {
        String sql = "update user set userName = ?,password = ?,sex=?,isAdmin=? where id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, user.getUsername());
        String str = user.getPassword();
        str = SHA256.getSha256Str(str);
        pstmt.setString(2, str);
        pstmt.setString(3, user.getSex());
        pstmt.setInt(4, user.getIsAdmin());
        pstmt.setInt(5, user.getId());
        return pstmt.executeUpdate();
    }
}