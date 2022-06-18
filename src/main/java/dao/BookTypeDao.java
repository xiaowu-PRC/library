package dao

import util.BookType
import java.sql.Connection
import java.sql.ResultSet

class BookTypeDao {
    @Throws(Exception::class)
    fun add(conn: Connection, bookType: BookType): Int {
        val sql = "insert into booktype values(null,?, ?)"
        val pstmt = conn.prepareStatement(sql)
        pstmt.setString(1, bookType.bookTypeName)
        pstmt.setString(2, bookType.bookTypeDesc)
        return pstmt.executeUpdate()
    }

    @Throws(Exception::class)
    fun list(conn: Connection, bookType: BookType): ResultSet {
        var sql = "select * from booktype"
        if (bookType.bookTypeName != null) {
            sql += " where bookTypeName like '%" + bookType.bookTypeName + "%'"
        }
        val stmt = conn.createStatement()
        return stmt.executeQuery(sql)
    }

    @Throws(Exception::class)
    fun delete(conn: Connection, id: String?): Int {
        val sql = "delete from booktype where id = ?"
        val pstmt = conn.prepareStatement(sql)
        pstmt.setString(1, id)
        return pstmt.executeUpdate()
    }

    @Throws(Exception::class)
    fun update(conn: Connection, bookType: BookType): Int {
        val sql = "update booktype set bookTypeName = ?, bookTypeDesc = ? where id = ?"
        val pstmt = conn.prepareStatement(sql)
        pstmt.setString(1, bookType.bookTypeName)
        pstmt.setString(2, bookType.bookTypeDesc)
        pstmt.setInt(3, bookType.id)
        return pstmt.executeUpdate()
    }
}