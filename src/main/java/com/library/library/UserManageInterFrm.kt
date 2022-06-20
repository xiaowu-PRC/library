package com.library.library

import com.leewyatt.rxcontrols.controls.RXTextField
import dao.UserDao
import javafx.beans.value.ObservableValue
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.scene.control.cell.PropertyValueFactory
import util.*
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

    @FXML
    private lateinit var del: Button

    @FXML
    private lateinit var female: RadioButton

    @FXML
    private lateinit var isAdmin: CheckBox

    @FXML
    private lateinit var man: RadioButton

    @FXML
    private lateinit var modify: Button

    @FXML
    private lateinit var m_no: TextField

    @FXML
    private lateinit var password: TextField

    @FXML
    private lateinit var s_no: RXTextField

    @FXML
    private lateinit var s_userName: RXTextField

    @FXML
    private lateinit var userPane: TitledPane

    @FXML
    private lateinit var search: Button

    @FXML
    private lateinit var sex: ToggleGroup

    @FXML
    private lateinit var userName: TextField

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
        userTable.selectionModel.selectedItemProperty()
            .addListener { _: ObservableValue<out UserTable?>?, _: UserTable?, t1: UserTable? ->
                if (t1 != null) {
                    m_no.text = t1.id.toString()
                    userName.text = t1.userName
                    password.text = t1.password
                    if (t1.sex.equals("男")) {
                        man.isSelected = true
                    }
                    if (t1.sex.equals("女")) {
                        female.isSelected = true
                    }
                    if (t1.isAdmin == 1) {
                        isAdmin.isSelected = true
                    }
                    if (t1.isAdmin == 0) {
                        isAdmin.isSelected = false
                    }
                    userPane.isDisable = false
                }
            }
    }

    @FXML
    fun del(event: ActionEvent) {
        conn = getConnection()
        val id = m_no.text.toInt()
        val user = User(id)
        val i = AlertUtil.showConfirm("二次确认", "确认删除？", "确认删除用户：$user?")
        if (i == 1) {
            val n = userdao.delete(conn, user)
            if (n > 0) {
                AlertUtil.showAlert("提示", "提示", "删除成功")
                showTableData()
            } else {
                AlertUtil.showAlert("提示", "提示", "删除失败")
                showTableData()
            }
            resetValue()
        }
    }

    @FXML
    fun modify(event: ActionEvent) {
        userModifyActionPerformed(event)
    }

    private fun userModifyActionPerformed(event: ActionEvent) {
        val id = m_no.text.toInt()
        val userName = userName.text
        val password = password.text
        var Sex = ""
        if (man.isSelected) {
            Sex = "男"
        }
        if (female.isSelected) {
            Sex = "女"
        }
        var isAdministrator = 0
        if (isAdmin.isSelected) {
            isAdministrator = 1
        }
        conn = getConnection()
        val user = User(userName, password, Sex, isAdministrator, id)
        val c = userdao.update(conn, user)
        if (c > 0) {
            AlertUtil.showAlert("提示", "提示", "修改成功")
            showTableData()
        } else {
            AlertUtil.showAlert("提示", "提示", "修改失败")
            showTableData()
        }
        resetValue()
    }

    @FXML
    fun search(event: ActionEvent) {
        userSearchActionPerformed(event)
    }

    private fun userSearchActionPerformed(event: ActionEvent) {
        var id: Int? = null
        if (StringUtil.isNotEmpty(s_no.text)) {
            id = s_no.text.toInt()
        }
        val userName = s_userName.text
        val user = User(id, userName)
        showTableData(user)
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

    private fun getTableviewList(user: User): ObservableList<UserTable> {
        val TableviewList = FXCollections.observableArrayList<UserTable>()
        conn = getConnection()
        try {
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

    private fun showTableData(user: User) {
        val list = getTableviewList(user)
        no.cellValueFactory = PropertyValueFactory("id")
        c_userName.cellValueFactory = PropertyValueFactory("userName")
        c_password.cellValueFactory = PropertyValueFactory("password")
        c_sex.cellValueFactory = PropertyValueFactory("sex")
        c_isAdmin.cellValueFactory = PropertyValueFactory("isAdmin")
        userTable.items = list
    }

    private fun resetValue() {
        s_no.text = ""
        userName.text = ""
        s_userName.text = ""
        password.text = ""
        s_no.text = ""
        man.isSelected = false
        female.isSelected = false
        isAdmin.isSelected = false
        userPane.isDisable = true
    }
}