package util

import java.sql.Connection
import java.sql.DriverManager

class Dbutil {
    private val dbUrl = "jdbc:mysql://localhost:3306/library"
    private val dbUser = "root"
    private val dbPass = "Lxcisasb181107"
    private val jdbc = "com.mysql.cj.jdbc.Driver"

    @get:Throws(Exception::class)
    val connection: Connection
        get() {
            Class.forName(jdbc)
            return DriverManager.getConnection(dbUrl, dbUser, dbPass)
        }

    @Throws(Exception::class)
    fun close(conn: Connection?) {
        conn?.close()
    }
}