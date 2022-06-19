package dao

import util.User
import java.sql.Connection
import java.sql.ResultSet

class UserDao {
    @Throws(Exception::class)
    fun login(conn: Connection, user: User): User? {
        var resultUser: User? = null
        val sql = "select * from user where username = ? and password = ?"
        val pstmt = conn.prepareStatement(sql)
        pstmt.setString(1, user.username)
        pstmt.setString(2, user.password)
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
        pstmt.setString(2, user.password)
        pstmt.setString(3, user.sex)
        pstmt.setInt(4, user.isAdmin)
        return pstmt.executeUpdate()
    }

    @Throws(Exception::class)
    fun list(conn: Connection, user: User): ResultSet {
        val sql = "select * from user"
        val pstmt = conn.prepareStatement(sql)
        return pstmt.executeQuery()
    }
}