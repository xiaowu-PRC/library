package dao;

import util.Book;
import util.StringUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BookDao {
    public int add(Connection conn, Book book) throws Exception {
        String sql = "insert into book values(null,?,?,?,?,?,?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, book.getBookName());
        pstmt.setString(2, book.getAuthor());
        pstmt.setString(3, book.getSex());
        pstmt.setFloat(4, book.getPrice());
        pstmt.setInt(5, book.getBookTypeId());
        pstmt.setString(6, book.getBookDesc());
        return pstmt.executeUpdate();
    }

    public ResultSet list(Connection conn, Book book) throws Exception {
        StringBuffer sb = new StringBuffer("select * from book b,bookType bt where b.bookTypeId=bt.id");
        if (StringUtil.isNotEmpty(book.getBookName())) {
            sb.append(" and b.bookName like '%" + book.getBookName() + "%'");
        }
        if (StringUtil.isNotEmpty(book.getAuthor())) {
            sb.append(" and b.author like '%" + book.getAuthor() + "%'");
        }
        if (book.getBookTypeId() != null && book.getBookTypeId() != -1) {
            sb.append(" and b.bookTypeId=" + book.getBookTypeId());
        }
        PreparedStatement pstmt = conn.prepareStatement(sb.toString());
        return pstmt.executeQuery();
    }

    public int delete(Connection conn, String id) throws Exception {
        String sql = "delete from book where id=?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, id);
        return pstmt.executeUpdate();
    }

    public int update(Connection conn, Book book) throws Exception {
        String sql = "update book set bookName=?,author=?,sex=?,price=?,bookDesc=?,bookTypeId=? where id=?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, book.getBookName());
        pstmt.setString(2, book.getAuthor());
        pstmt.setString(3, book.getSex());
        pstmt.setFloat(4, book.getPrice());
        pstmt.setString(5, book.getBookDesc());
        pstmt.setInt(6, book.getBookTypeId());
        pstmt.setInt(7, book.getId());
        return pstmt.executeUpdate();
    }

    public boolean existBookTypeId(Connection conn, String bookTypeId) throws Exception {
        String sql = "select * from book where bookTypeId=?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, bookTypeId);
        ResultSet rs = pstmt.executeQuery();
        return rs.next();
    }
}
