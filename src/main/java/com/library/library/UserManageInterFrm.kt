package com.library.library

import dao.UserDao
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.fxml.FXML
import javafx.scene.control.TableCell
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.control.cell.PropertyValueFactory
import util.Dbutil
import util.Tableview
import util.User
import util.UserTable
import java.sql.Connection
import java.sql.ResultSet

class UserManageInterFrm {
    @FXML
    private lateinit var c_isAdmin: TableColumn<UserTable, Int>

    @FXML
    private lateinit var c_password: TableColumn<UserTable, String>

    @FXML
    private lateinit var c_sex: TableColumn<UserTable, String>

    @FXML
    private lateinit var c_userName: TableColumn<UserTable, String>

    @FXML
    private lateinit var no: TableColumn<UserTable, Int>

    @FXML
    private lateinit var userTable: TableView<UserTable>
    private val dbutil = Dbutil()
    private val userdao = UserDao()
    private lateinit var conn: Connection

    @FXML
    fun initialize() {
        showTableData()
        c_isAdmin.setCellFactory {
            object : TableCell<UserTable?, Int?>() {
                override fun updateItem(item: Int?, empty: Boolean) {
                    super.updateItem(item, empty)
                    text = if (empty) {
                        null
                    } else {
                        if (item == 0) {
                            "否"
                        } else {
                            "是"
                        }
                    }
                }
            }
        }
    }

    fun getConnection(): Connection {
        try {
            conn = dbutil.connection
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return conn
    }

    private fun getTableviewList(): ObservableList<UserTable> {
        val TableviewList = FXCollections.observableArrayList<UserTable>()
        conn = getConnection()
        try {
            val user = User()
            val rs: ResultSet = userdao.list(conn, user)
            var usertable: UserTable
            while (rs.next()) {
                usertable = UserTable(
                    rs.getInt("id"),
                    rs.getString("userName"),
                    rs.getString("password"),
                    rs.getString("sex"),
                    rs.getInt("isAdmin"),
                )
                TableviewList.add(usertable)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                dbutil.close(conn)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return TableviewList
    }

    private fun showTableData() {
        val list = getTableviewList()
        no.cellValueFactory = PropertyValueFactory("id")
        c_userName.cellValueFactory = PropertyValueFactory("userName")
        c_password.cellValueFactory = PropertyValueFactory("password")
        c_sex.cellValueFactory = PropertyValueFactory("sex")
        c_isAdmin.cellValueFactory = PropertyValueFactory("isAdmin")
        userTable.items = list
    }
}