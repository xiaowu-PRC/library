package dao

import util.Book
import util.StringUtil
import java.sql.Connection
import java.sql.ResultSet
import java.time.LocalDateTime

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
    fun borrow(conn: Connection, uid: String?, bookId: String?, startDate: String?, endDate: String?): Int {
        val sql =
            "insert into book_appointment(User_ID,Book_ID,Start_Time,End_Time,Appointment_Time,Extendable,isReturned) values(?,?,?,?,?,?,?)"
        val sql2 = "update book set remainder=remainder-1 where id=?"
        val pstmt = conn.prepareStatement(sql)
        val pstmt2 = conn.prepareStatement(sql2)
        pstmt.setString(1, uid)
        pstmt.setString(2, bookId)
        pstmt.setString(3, startDate)
        pstmt.setString(4, endDate)
        pstmt.setString(5, LocalDateTime.now().toString())
        pstmt.setString(6, "1")
        pstmt.setString(7, "0")
        pstmt2.setString(1, bookId)
        val a = pstmt.executeUpdate()
        val b = pstmt2.executeUpdate()
        return a + b
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
        sb.append(" ORDER BY b.id ASC")
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
        sb.append(" and isReturned NOT IN (1) ORDER BY ID ASC")
        val pstmt = conn.prepareStatement(sb.toString())
        return pstmt.executeQuery()
    }

    @Throws(Exception::class)
    fun list3(conn: Connection): ResultSet {
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
    fun list4(conn: Connection): ResultSet {
        val sql =
            "select ba.ID,Book_ID,bookName,Start_Time,End_Time,Appointment_Time,isReturned from book_appointment ba,user u,book b where ba.User_ID=u.id and ba.Book_ID=b.id ORDER BY ID ASC"
        val pstmt = conn.prepareStatement(sql)
        return pstmt.executeQuery()
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
    fun notReturnBook(conn: Connection, User_ID: String?, Book_ID: String?): Boolean {
        val sql = "select * from book_appointment where User_ID=? and Book_ID=? and isReturned NOT IN (1)"
        val pstmt = conn.prepareStatement(sql)
        pstmt.setString(1, User_ID)
        pstmt.setString(2, Book_ID)
        val rs = pstmt.executeQuery()
        return rs.next()
    }

    @Throws(Exception::class)
    fun overTime(conn: Connection, User_ID: String?, ID: String?): Boolean {
        val sql =
            "select * from book_appointment where User_ID=? and End_Time<now() and isReturned Not In (1) and Extendable Not IN (1) and ID=?"
        val pstmt = conn.prepareStatement(sql)
        pstmt.setString(1, User_ID)
        pstmt.setString(2, ID)
        val rs = pstmt.executeQuery()
        return rs.next()
    }

    @Throws(Exception::class)
    fun returnBook(conn: Connection, id: String?, nowTime: String?): Int {
        val sql =
            "update book b, book_appointment ba set ba.Extendable=0,ba.isReturned=1,ba.Return_Time=?,b.remainder=b.remainder+1 where b.id=ba.Book_ID and ba.ID=?"
        val pstmt = conn.prepareStatement(sql)
        pstmt.setString(1, nowTime)
        pstmt.setString(2, id)
        return pstmt.executeUpdate()
    }

    @Throws(Exception::class)
    fun renewal(conn: Connection, id: String?, day: String?): Int {
        val sql = "update book_appointment set End_Time=?,Extendable=? where ID=?"
        val pstmt = conn.prepareStatement(sql)
        pstmt.setString(1, day)
        pstmt.setString(2, "0")
        pstmt.setString(3, id)
        return pstmt.executeUpdate()
    }

    @Throws(Exception::class)
    fun Extended(conn: Connection, id: String?): Boolean {
        val sql = "select * from book_appointment where id = ? and Extendable=0"
        val pstmt = conn.prepareStatement(sql)
        pstmt.setString(1, id)
        val rs = pstmt.executeQuery()
        return rs.next()
    }

    @Throws(Exception::class)
    fun checkOverTime(conn: Connection, User_ID: String?): Boolean {
        val sql = "select * from book_appointment where User_ID=? and End_Time<now() and isReturned Not In (1)"
        val pstmt = conn.prepareStatement(sql)
        pstmt.setString(1, User_ID)
        val rs = pstmt.executeQuery()
        return rs.next()
    }
}