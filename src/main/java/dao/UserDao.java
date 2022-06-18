package dao

import util.User
import java.sql.Connection

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
}