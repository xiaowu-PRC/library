package dao;

import util.Book;
import util.StringUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BookDao {
    public int add(Connection conn, Book book) throws Exception {
        String sql = "insert into book values(null,?,?,?,?,?,?,?,?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, book.getBookName());
        pstmt.setString(2, book.getAuthor());
        pstmt.setString(3, book.getSex());
        pstmt.setFloat(4, book.getPrice());
        pstmt.setInt(5, book.getBookTypeId());
        pstmt.setString(6, book.getBookDesc());
        pstmt.setString(7, book.getTotal());
        pstmt.setString(8, book.getRemainder());
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
        if (book.getId() != null && book.getId() != 0) {
            sb.append(" and b.id=" + book.getId());
        }
        PreparedStatement pstmt = conn.prepareStatement(sb.toString());
        return pstmt.executeQuery();
    }

    public ResultSet list2(Connection conn, Book book) throws Exception {
        StringBuffer sb = new StringBuffer("select ba.ID,Book_ID,bookName,Start_Time,End_Time,Appointment_Time,isReturned from book_appointment ba,user u,book b where ba.User_ID=u.id and ba.Book_ID=b.id");
        if (StringUtil.isNotEmpty(book.getB_ID())) {
            sb.append(" and ba.ID like '%" + book.getB_ID() + "%'");
        }
        if (StringUtil.isNotEmpty(book.getId().toString())&&book.getId() != null && book.getId() != 0) {
            sb.append(" and b.ID=" + book.getId());
        }
        if (StringUtil.isNotEmpty(book.getBookName())) {
            sb.append(" and b.bookName like '%" + book.getBookName() + "%'");
        }
        sb.append(" ORDER BY Appointment_Time ASC");
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
        String sql = "update book set bookName=?,author=?,sex=?,price=?,bookDesc=?,bookTypeId=?,total=?,remainder=? where id=?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, book.getBookName());
        pstmt.setString(2, book.getAuthor());
        pstmt.setString(3, book.getSex());
        pstmt.setFloat(4, book.getPrice());
        pstmt.setString(5, book.getBookDesc());
        pstmt.setInt(6, book.getBookTypeId());
        pstmt.setString(7, book.getTotal());
        pstmt.setString(8, book.getRemainder());
        pstmt.setInt(9, book.getId());

        return pstmt.executeUpdate();
    }

    public boolean existBookTypeId(Connection conn, String bookTypeId) throws Exception {
        String sql = "select * from book where bookTypeId=?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, bookTypeId);
        ResultSet rs = pstmt.executeQuery();
        return rs.next();
    }

    public int returnBook(Connection conn,String id)throws Exception{
        String sql="update book_appointment set isReturned=1 where ID=?";
        PreparedStatement pstmt=conn.prepareStatement(sql);
        pstmt.setString(1,id);
        return pstmt.executeUpdate();
    }
}
