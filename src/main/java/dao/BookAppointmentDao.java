package dao;

import util.StringUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookAppointmentDao {
    public ResultSet list(Connection conn, int id) throws SQLException {
        String sql = "SELECT ba.ID,u.id as User_ID,u.userName,b.id as Book_ID,bt.bookTypeName,b.bookName,ba.Start_Time,ba.End_Time,ba.Extendable,ba.isReturned,ba.Return_Time FROM booktype bt,book b,book_appointment ba,user u WHERE bt.id=b.bookTypeId and ba.Book_ID=b.id and ba.User_ID=u.id AND ba.User_ID=? ORDER BY ba.ID ASC";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, id);
        return pstmt.executeQuery();
    }

    public int update(Connection conn, int id, int extendable, int isReturned, String returnTime) throws SQLException {
        StringBuilder sql = new StringBuilder("update book_appointment set Extendable=?,isReturned=?");
        if (StringUtil.isNotEmpty(returnTime)) {
            sql.append(",Return_Time=?");
        }
        sql.append(" where ID=?");
        PreparedStatement pstmt = conn.prepareStatement(sql.toString());
        pstmt.setInt(1, extendable);
        pstmt.setInt(2, isReturned);
        if (StringUtil.isNotEmpty(returnTime)) {
            pstmt.setString(3, returnTime);
            pstmt.setInt(4, id);
        } else {
            pstmt.setInt(3, id);
        }
        return pstmt.executeUpdate();
    }
}
