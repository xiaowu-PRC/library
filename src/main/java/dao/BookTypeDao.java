package dao;

import util.BookType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class BookTypeDao {
    public int add(Connection conn, BookType bookType) throws Exception {
        String sql = "insert into booktype values(null,?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, bookType.getBookTypeName());
        pstmt.setString(2, bookType.getBookTypeDesc());
        return pstmt.executeUpdate();
    }

    public ResultSet list(Connection conn, BookType bookType) throws Exception {
        String sql = "select * from booktype";
        if (bookType.getBookTypeName() != null) {
            sql += " where bookTypeName like '%" + bookType.getBookTypeName() + "%'";
        }
        Statement stmt = conn.createStatement();
        return stmt.executeQuery(sql);
    }

    public int delete(Connection conn, String id) throws Exception {
        String sql = "delete from booktype where id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, id);
        return pstmt.executeUpdate();
    }

    public int update(Connection conn, BookType bookType) throws Exception {
        String sql = "update booktype set bookTypeName = ?, bookTypeDesc = ? where id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, bookType.getBookTypeName());
        pstmt.setString(2, bookType.getBookTypeDesc());
        pstmt.setInt(3, bookType.getId());
        return pstmt.executeUpdate();
    }
}