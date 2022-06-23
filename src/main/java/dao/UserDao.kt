package dao

import com.library.library.LibraryController
import util.SHA256
import util.StringUtil
import util.User
import java.security.MessageDigest
import java.sql.Connection
import java.sql.ResultSet

class UserDao {
    @Throws(Exception::class)
    fun login(conn: Connection, user: User): User? {
        var resultUser: User? = null
        val sql = "select * from user where username = ? and password = ?"
        val pstmt = conn.prepareStatement(sql)
        pstmt.setString(1, user.username)
        var str = user.password
        str = SHA256.getSha256Str(str)
        pstmt.setString(2, str)
        val rs = pstmt.executeQuery()
        if (rs.next()) {
            resultUser = User()
            resultUser.id = rs.getInt("id")
            resultUser.username = rs.getString("username")
            resultUser.password = rs.getString("password")
            resultUser.isAdmin = rs.getInt("isAdmin")
        }
        return resultUser
    }

    @Throws(Exception::class)
    fun add(conn: Connection, user: User): Int {
        val sql = "insert into user values(null,?,?,?,?)"
        val pstmt = conn.prepareStatement(sql)
        pstmt.setString(1, user.username)
        var str = user.password
        str = SHA256.getSha256Str(str)
        pstmt.setString(2, str)
        pstmt.setString(3, user.sex)
        pstmt.setInt(4, user.isAdmin)
        return pstmt.executeUpdate()
    }

    @Throws(Exception::class)
    fun list(conn: Connection, user: User): ResultSet {
        val nowuser = LibraryController.User_Name
        val sql = StringBuffer("select * from user WHERE userName NOT IN ('$nowuser')")
        if (StringUtil.isNotEmpty(user.id.toString()) && user.id != null) {
            sql.append(" and id = ").append(user.id)
        }
        if (StringUtil.isNotEmpty(user.username)) {
            sql.append(" and where username = ").append(user.username)
        }
        val pstmt = conn.prepareStatement(sql.toString())
        return pstmt.executeQuery()
    }

    @Throws(Exception::class)
    fun delete(conn: Connection, user: User): Int {
        val sql = "delete from user where id = ?"
        val pstmt = conn.prepareStatement(sql)
        pstmt.setInt(1, user.id)
        return pstmt.executeUpdate()
    }

    @Throws(Exception::class)
    fun update(conn: Connection, user: User): Int {
        val sql = "update user set userName = ?,password = ?,sex=?,isAdmin=? where id = ?"
        val pstmt = conn.prepareStatement(sql)
        pstmt.setString(1, user.username)
        var str = user.password
        str = SHA256.getSha256Str(str)
        pstmt.setString(2, str)
        pstmt.setString(3, user.sex)
        pstmt.setInt(4, user.isAdmin)
        pstmt.setInt(5, user.id)
        return pstmt.executeUpdate()
    }
}