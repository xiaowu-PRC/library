package com.library.library

import com.leewyatt.rxcontrols.controls.RXTextField
import com.leewyatt.rxcontrols.event.RXActionEvent
import dao.BookAppointmentDao
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
import java.time.LocalDateTime

class BookAppointmentInterFrm {
    @FXML
    private lateinit var appointmentTable: TableView<AppointmentTable>
    private val dbutil = Dbutil()
    private val bookAppointmentDao = BookAppointmentDao()
    private lateinit var conn: Connection

    @FXML
    private lateinit var b_id: TableColumn<AppointmentTable, Int>

    @FXML
    private lateinit var b_name: TableColumn<AppointmentTable, String>

    @FXML
    private lateinit var b_type: TableColumn<AppointmentTable, String>

    @FXML
    private lateinit var extendable: CheckBox

    @FXML
    private lateinit var no: TableColumn<AppointmentTable, Int>

    @FXML
    private lateinit var returned: CheckBox

    @FXML
    private lateinit var search: Button

    @FXML
    private lateinit var submit: Button

    @FXML
    private lateinit var operationPanel: TitledPane

    @FXML
    private lateinit var uidtxt: TextField

    @FXML
    private lateinit var bidtxt: TextField

    @FXML
    private lateinit var bookNametxt: TextField

    @FXML
    private lateinit var idtxt: TextField

    @FXML
    private lateinit var t_borrowDate: TableColumn<AppointmentTable, String>

    @FXML
    private lateinit var t_endDate: TableColumn<AppointmentTable, String>

    @FXML
    private lateinit var t_extendable: TableColumn<AppointmentTable, Int>

    @FXML
    private lateinit var t_returned: TableColumn<AppointmentTable, Int>

    @FXML
    private lateinit var u_no: TableColumn<AppointmentTable, Int>

    @FXML
    private lateinit var uid: RXTextField

    @FXML
    private lateinit var userName: TableColumn<AppointmentTable, String>

    @FXML
    private lateinit var t_returnedTime: TableColumn<AppointmentTable, String>

    @FXML
    fun initialize() {
        appointmentTable.selectionModel.selectedItemProperty()
            .addListener { _: ObservableValue<out AppointmentTable?>?, _: AppointmentTable?, t1: AppointmentTable? ->
                if (t1 != null) {
                    idtxt.text = t1.id.toString()
                    uidtxt.text = t1.user_ID.toString()
                    bidtxt.text = t1.book_ID.toString()
                    bookNametxt.text = t1.bookName
                    extendable.isSelected = t1.extendable == 1
                    if (t1.isReturned == 1) {
                        returned.isSelected = true
                        operationPanel.isDisable = true
                    } else {
                        returned.isSelected = false
                        operationPanel.isDisable = false
                    }
                }
            }
    }

    @FXML
    fun deleteText(event: RXActionEvent) {
        val tf = event.source as RXTextField
        tf.clear()
    }

    @FXML
    fun search(event: ActionEvent) {
        if (StringUtil.isEmpty(uid.text)) {
            AlertUtil.showError("错误", "错误", "请输入用户编号!")
            return
        } else {
            showTableData()
        }
    }

    @FXML
    fun submit(event: ActionEvent) {
        val id = idtxt.text.toInt()
        var Extendable = 0
        var isReturned = 0
        if (extendable.isSelected) {
            Extendable = 1
        }
        if (returned.isSelected) {
            isReturned = 1
        }
        val a = AlertUtil.showConfirm("确认", "二次确认", "确认操作?")
        var returnTime = ""
        if (a == 1) {
            conn = getConnection()
            if (returned.isSelected) {
                returnTime = LocalDateTime.now().toString()
            }
            val b = bookAppointmentDao.update(conn, id, Extendable, isReturned, returnTime)
            if (b > 0) {
                AlertUtil.showAlert("提示", "提示", "修改成功!")
            } else {
                AlertUtil.showError("错误", "错误", "修改失败!")
            }
            resetValue()
        }
    }

    private fun getTableviewList(): ObservableList<AppointmentTable> {
        val uid = uid.text.toInt()
        val TableviewList = FXCollections.observableArrayList<AppointmentTable>()
        conn = getConnection()
        try {
            val rs: ResultSet = bookAppointmentDao.list(conn, uid)
            var appointmentTable: AppointmentTable
            while (rs.next()) {
                appointmentTable = AppointmentTable(
                    rs.getInt("ID"),
                    rs.getInt("User_ID"),
                    rs.getString("userName"),
                    rs.getInt("Book_ID"),
                    rs.getString("bookTypeName"),
                    rs.getString("bookName"),
                    rs.getString("Start_Time"),
                    rs.getString("End_Time"),
                    rs.getInt("Extendable"),
                    rs.getInt("isReturned"),
                    rs.getString("Return_Time")
                )
                TableviewList.add(appointmentTable)
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
        no.cellValueFactory = PropertyValueFactory("ID")
        u_no.cellValueFactory = PropertyValueFactory("User_ID")
        userName.cellValueFactory = PropertyValueFactory("userName")
        b_id.cellValueFactory = PropertyValueFactory("Book_ID")
        b_type.cellValueFactory = PropertyValueFactory("bookTypeName")
        b_name.cellValueFactory = PropertyValueFactory("bookName")
        t_borrowDate.cellValueFactory = PropertyValueFactory("Start_Time")
        t_endDate.cellValueFactory = PropertyValueFactory("End_Time")
        t_extendable.cellValueFactory = PropertyValueFactory("Extendable")
        t_returned.cellValueFactory = PropertyValueFactory("isReturned")
        t_returnedTime.cellValueFactory = PropertyValueFactory("Return_Time")
        appointmentTable.items = list
    }

    fun getConnection(): Connection {
        try {
            conn = dbutil.connection
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return conn
    }

    private fun resetValue() {
        idtxt.text = ""
        uidtxt.text = ""
        bidtxt.text = ""
        bookNametxt.text = ""
        extendable.isSelected = false
        returned.isSelected = false
        operationPanel.isDisable = true
        showTableData()
    }
}