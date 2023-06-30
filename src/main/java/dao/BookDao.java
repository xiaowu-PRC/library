package dao;

import util.Book;
import util.StringUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

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

    public int borrow(Connection conn, String uid, String bookId, String startDate, String endDate) throws Exception {
        String sql = "insert into book_appointment(User_ID,Book_ID,Start_Time,End_Time,Appointment_Time,Extendable,isReturned) values(?,?,?,?,?,?,?)";
        String sql2 = "update book set remainder=remainder-1 where id=?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        PreparedStatement pstmt2 = conn.prepareStatement(sql2);
        pstmt.setString(1, uid);
        pstmt.setString(2, bookId);
        pstmt.setString(3, startDate);
        pstmt.setString(4, endDate);
        pstmt.setString(5, LocalDateTime.now().toString());
        pstmt.setString(6, "1");
        pstmt.setString(7, "0");
        pstmt2.setString(1, bookId);
        int a = pstmt.executeUpdate();
        int b = pstmt2.executeUpdate();
        return a + b;
    }

    public ResultSet list(Connection conn, Book book) throws Exception {
        StringBuilder sb = new StringBuilder("select * from book b,booktype bt where b.bookTypeId=bt.id");
        if (StringUtil.isNotEmpty(book.getBookName())) {
            sb.append(" and b.bookName like '%").append(book.getBookName()).append("%'");
        }
        if (StringUtil.isNotEmpty(book.getAuthor())) {
            sb.append(" and b.author like '%").append(book.getAuthor()).append("%'");
        }
        if (book.getBookTypeId() != null && book.getBookTypeId() != -1) {
            sb.append(" and b.bookTypeId=").append(book.getBookTypeId());
        }
        if (book.getId() != null && book.getId() != 0) {
            sb.append(" and b.id=").append(book.getId());
        }
        sb.append(" ORDER BY b.id ASC");
        PreparedStatement pstmt = conn.prepareStatement(sb.toString());
        return pstmt.executeQuery();
    }

    public ResultSet list2(Connection conn, Book book) throws Exception {
        StringBuilder sb = new StringBuilder("select ba.ID,Book_ID,bookName,Start_Time,End_Time,Appointment_Time,isReturned from book_appointment ba,user u,book b where ba.User_ID=u.id and ba.Book_ID=b.id");
        if (StringUtil.isNotEmpty(book.getB_ID())) {
            sb.append(" and ba.ID like '%").append(book.getB_ID()).append("%'");
        }
        if (StringUtil.isNotEmpty(String.valueOf(book.getId())) && book.getId() != null && book.getId() != 0) {
            sb.append(" and b.ID=").append(book.getId());
        }
        if (StringUtil.isNotEmpty(book.getBookName())) {
            sb.append(" and b.bookName like '%").append(book.getBookName()).append("%'");
        }
        sb.append(" and isReturned NOT IN (1) ORDER BY ID ASC");
        PreparedStatement pstmt = conn.prepareStatement(sb.toString());
        return pstmt.executeQuery();
    }

    public ResultSet list3(Connection conn) throws Exception {
        String sql = "select * from book_appointment ba,user u,book b where ba.User_ID=u.id and ba.Book_ID=b.id and isReturned=0 ORDER BY End_Time ASC";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        return pstmt.executeQuery();
    }

    public int delete(Connection conn, String id) throws SQLException {
        String sql = "delete from book where id=?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, id);
        return pstmt.executeUpdate();
    }

    public ResultSet list4(Connection conn) throws SQLException {
        String sql = "select ba.ID,Book_ID,bookName,Start_Time,End_Time,Appointment_Time,isReturned from book_appointment ba,user u,book b where ba.User_ID=u.id and ba.Book_ID=b.id ORDER BY ID ASC";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        return pstmt.executeQuery();
    }

    public int update(Connection conn, Book book) throws SQLException {
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

    public Boolean existBookTypeId(Connection conn, String bookTypeId) throws Exception {
        String sql = "select * from book where bookTypeId=?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, bookTypeId);
        ResultSet rs = pstmt.executeQuery();
        return rs.next();
    }

    public Boolean notReturnBook(Connection conn, String User_ID, String Book_ID) throws SQLException {
        String sql = "select * from book_appointment where User_ID=? and Book_ID=? and isReturned NOT IN (1)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, User_ID);
        pstmt.setString(2, Book_ID);
        ResultSet rs = pstmt.executeQuery();
        return rs.next();
    }

    public Boolean overTime(Connection conn, String User_ID, String ID) throws SQLException {
        String sql = "select * from book_appointment where User_ID=? and End_Time<now() and isReturned Not In (1) and Extendable Not IN (1) and ID=?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, User_ID);
        pstmt.setString(2, ID);
        ResultSet rs = pstmt.executeQuery();
        return rs.next();
    }

    public int returnBook(Connection conn, String id, String nowTime) throws Exception {
        String sql = "update book b, book_appointment ba set ba.Extendable=0,ba.isReturned=1,ba.Return_Time=?,b.remainder=b.remainder+1 where b.id=ba.Book_ID and ba.ID=?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, nowTime);
        pstmt.setString(2, id);
        return pstmt.executeUpdate();
    }

    public int renewal(Connection conn, String id, String day) throws SQLException {
        String sql = "update book_appointment set End_Time=?,Extendable=? where ID=?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, day);
        pstmt.setString(2, "0");
        pstmt.setString(3, id);
        return pstmt.executeUpdate();
    }

    public Boolean Extended(Connection conn, String id) throws SQLException {
        String sql = "select * from book_appointment where id = ? and Extendable=0";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, id);
        ResultSet rs = pstmt.executeQuery();
        return rs.next();
    }

    public Boolean checkOverTime(Connection conn, String User_ID) throws SQLException {
        String sql = "select * from book_appointment where User_ID=? and End_Time<now() and isReturned Not In (1)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, User_ID);
        ResultSet rs = pstmt.executeQuery();
        return rs.next();
    }
}