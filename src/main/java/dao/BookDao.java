package dao

import util.Book
import util.StringUtil
import java.sql.Connection
import java.sql.ResultSet

class BookDao {
    @Throws(Exception::class)
    fun add(conn: Connection, book: Book): Int {
        val sql = "insert into book values(null,?,?,?,?,?,?,?,?)"
        val pstmt = conn.prepareStatement(sql)
        pstmt.setString(1, book.bookName)
        pstmt.setString(2, book.author)
        pstmt.setString(3, book.sex)
        pstmt.setFloat(4, book.price)
        pstmt.setInt(5, book.bookTypeId)
        pstmt.setString(6, book.bookDesc)
        pstmt.setString(7, book.total)
        pstmt.setString(8, book.remainder)
        return pstmt.executeUpdate()
    }

    @Throws(Exception::class)
    fun list(conn: Connection, book: Book): ResultSet {
        val sb = StringBuffer("select * from book b,bookType bt where b.bookTypeId=bt.id")
        if (StringUtil.isNotEmpty(book.bookName)) {
            sb.append(" and b.bookName like '%" + book.bookName + "%'")
        }
        if (StringUtil.isNotEmpty(book.author)) {
            sb.append(" and b.author like '%" + book.author + "%'")
        }
        if (book.bookTypeId != null && book.bookTypeId != -1) {
            sb.append(" and b.bookTypeId=" + book.bookTypeId)
        }
        if (book.id != null && book.id != 0) {
            sb.append(" and b.id=" + book.id)
        }
        val pstmt = conn.prepareStatement(sb.toString())
        return pstmt.executeQuery()
    }

    @Throws(Exception::class)
    fun list2(conn: Connection, book: Book): ResultSet {
        val sb =
            StringBuffer("select ba.ID,Book_ID,bookName,Start_Time,End_Time,Appointment_Time,isReturned from book_appointment ba,user u,book b where ba.User_ID=u.id and ba.Book_ID=b.id")
        if (StringUtil.isNotEmpty(book.b_ID)) {
            sb.append(" and ba.ID like '%" + book.b_ID + "%'")
        }
        if (StringUtil.isNotEmpty(book.id.toString()) && book.id != null && book.id != 0) {
            sb.append(" and b.ID=" + book.id)
        }
        if (StringUtil.isNotEmpty(book.bookName)) {
            sb.append(" and b.bookName like '%" + book.bookName + "%'")
        }
        sb.append(" ORDER BY ID ASC")
        val pstmt = conn.prepareStatement(sb.toString())
        return pstmt.executeQuery()
    }

    @Throws(Exception::class)
    fun list3(conn: Connection, book: Book): ResultSet {
        val sql =
            "select * from book_appointment ba,user u,book b where ba.User_ID=u.id and ba.Book_ID=b.id and isReturned=0 ORDER BY End_Time ASC"
        val pstmt = conn.prepareStatement(sql)
        return pstmt.executeQuery()
    }

    @Throws(Exception::class)
    fun delete(conn: Connection, id: String?): Int {
        val sql = "delete from book where id=?"
        val pstmt = conn.prepareStatement(sql)
        pstmt.setString(1, id)
        return pstmt.executeUpdate()
    }

    @Throws(Exception::class)
    fun update(conn: Connection, book: Book): Int {
        val sql =
            "update book set bookName=?,author=?,sex=?,price=?,bookDesc=?,bookTypeId=?,total=?,remainder=? where id=?"
        val pstmt = conn.prepareStatement(sql)
        pstmt.setString(1, book.bookName)
        pstmt.setString(2, book.author)
        pstmt.setString(3, book.sex)
        pstmt.setFloat(4, book.price)
        pstmt.setString(5, book.bookDesc)
        pstmt.setInt(6, book.bookTypeId)
        pstmt.setString(7, book.total)
        pstmt.setString(8, book.remainder)
        pstmt.setInt(9, book.id)
        return pstmt.executeUpdate()
    }

    @Throws(Exception::class)
    fun existBookTypeId(conn: Connection, bookTypeId: String?): Boolean {
        val sql = "select * from book where bookTypeId=?"
        val pstmt = conn.prepareStatement(sql)
        pstmt.setString(1, bookTypeId)
        val rs = pstmt.executeQuery()
        return rs.next()
    }

    @Throws(Exception::class)
    fun returnBook(conn: Connection, id: String?, nowTime: String?): Int {
        val sql = "update book_appointment set isReturned=1,Return_Time=? where ID=?"
        val pstmt = conn.prepareStatement(sql)
        pstmt.setString(1, nowTime)
        pstmt.setString(2, id)
        return pstmt.executeUpdate()
    }

    @Throws(Exception::class)
    fun renewal(conn: Connection, id: String?, day: String?): Int {
        val sql = "update book_appointment set End_Time=? where ID=?"
        val pstmt = conn.prepareStatement(sql)
        pstmt.setString(1, day)
        pstmt.setString(2, id)
        return pstmt.executeUpdate()
    }
}