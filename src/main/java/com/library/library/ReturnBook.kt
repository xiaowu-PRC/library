package com.library.library

import dao.BookDao
import io.github.palexdev.materialfx.controls.legacy.MFXLegacyTableView
import javafx.beans.value.ChangeListener
import javafx.beans.value.ObservableValue
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.TableCell
import javafx.scene.control.TableColumn
import javafx.scene.control.TextField
import javafx.scene.control.cell.PropertyValueFactory
import util.*
import java.sql.Connection
import java.sql.ResultSet

class ReturnBook {
    @FXML
    private lateinit var appointmentTime: TableColumn<Tableview, String>

    @FXML
    private lateinit var bookId: TableColumn<Tableview, String>

    @FXML
    private lateinit var bookName: TableColumn<Tableview, String>

    @FXML
    private lateinit var endTime: TableColumn<Tableview, String>

    @FXML
    private lateinit var isReturned: TableColumn<Tableview, String>

    @FXML
    private lateinit var no: TableColumn<Tableview, String>

    @FXML
    private lateinit var queryTable: MFXLegacyTableView<Tableview>

    @FXML
    private lateinit var reset: Button

    @FXML
    private lateinit var s_bookIDTxt: TextField

    @FXML
    private lateinit var s_bookNameTxt: TextField

    @FXML
    private lateinit var s_no: TextField

    @FXML
    private lateinit var search: Button

    @FXML
    private lateinit var giveback: Button

    @FXML
    private lateinit var startTime: TableColumn<Tableview, String>
    private lateinit var conn: Connection
    private val dbutil = Dbutil()
    private val bookdao = BookDao()

    @FXML
    fun initialize() {
        showTableData()
        isReturned.setCellFactory {
            object : TableCell<Tableview?, String?>() {
                override fun updateItem(item: String?, empty: Boolean) {
                    super.updateItem(item, empty)
                    text = if (empty) {
                        null
                    } else {
                        if (item == "0") {
                            "未还"
                        } else {
                            "已还"
                        }
                    }
                }
            }
        }
        s_no.promptText = "请输入或选择一本图书"
        s_bookNameTxt.promptText = "请输入或选择一本图书"
        s_bookIDTxt.promptText = "请输入或选择一本图书"
        queryTable.selectionModel.selectedItemProperty()
            .addListener { _: ObservableValue<out Tableview?>?, _: Tableview?, t1: Tableview? ->
                if (t1 != null) {
                    s_no.text = t1.getID()
                    s_bookIDTxt.text = t1.book_ID
                    s_bookNameTxt.text = t1.bookName
                    s_no.isDisable = true
                    s_bookIDTxt.isDisable = true
                    s_bookNameTxt.isDisable = true
                }
            }
    }

    @FXML
    fun reset(event: ActionEvent?) {
        initialize()
        s_no.clear()
        s_bookNameTxt.clear()
        s_bookIDTxt.clear()
        s_no.isDisable = false
        s_bookNameTxt.isDisable = false
        s_bookIDTxt.isDisable = false
    }

    @FXML
    fun search(event: ActionEvent) {
        bookSearchActionPerformed(event)
    }

    private fun bookSearchActionPerformed(event: ActionEvent) {
        val bookName = s_bookNameTxt.text
        val bookID = s_bookIDTxt.text
        val bookid = bookID.toInt()
        val id = s_no.text
        if (StringUtil.isEmpty(bookName) && StringUtil.isEmpty(bookID) && StringUtil.isEmpty(id)) {
            AlertUtil.showWarning("提示", "提示", "请输入查询条件")
        }
        val book = Book(id, bookid, bookName)
        showTableData(book)
    }

    @FXML
    fun giveback(event: ActionEvent) {
        bookReturnActionPerformed(event)
    }

    private fun bookReturnActionPerformed(event: ActionEvent) {
        val id = s_no.text
        val bookId = s_bookIDTxt.text
        val bookName = s_bookNameTxt.text
        if(StringUtil.isEmpty(id) || StringUtil.isEmpty(bookId) || StringUtil.isEmpty(bookName)){
            AlertUtil.showError("错误", "错误", "请重新选择")
            return
        }
        val i=AlertUtil.showConfirm("确认", "确认归还下列图书吗？", "编号："+id+"\r\n"+"图书编号："+bookId+"\r\n"+"图书名称："+bookName)
        if(i==1)
        {
            conn=getConnection()
            val n=bookdao.returnBook(conn,id)
            if(n>0)
            {
                AlertUtil.showAlert("提示", "提示", "归还成功")
            }
            reset(null)
            showTableData()
        }
    }


    private fun getTableviewList(): ObservableList<Tableview> {
        val TableviewList = FXCollections.observableArrayList<Tableview>()
        conn = getConnection()
        try {
            val book = Book(null, null, null)
            val rs: ResultSet = bookdao.list2(conn, book)
            var tableview: Tableview
            while (rs.next()) {
                tableview = Tableview(
                    rs.getString("ID"),
                    rs.getString("Book_ID"),
                    rs.getString("bookName"),
                    rs.getString("Start_Time"),
                    rs.getString("End_Time"),
                    rs.getString("Appointment_Time"),
                    rs.getString("isReturned")
                )
                TableviewList.add(tableview)
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

    fun getTableviewList(book: Book?): ObservableList<Tableview>? {
        val TableviewList = FXCollections.observableArrayList<Tableview>()
        conn = getConnection()
        try {
            val rs = bookdao.list(conn, book)
            var tableview: Tableview
            while (rs.next()) {
                tableview = Tableview(
                    rs.getString("ID"),
                    rs.getString("Book_ID"),
                    rs.getString("bookName"),
                    rs.getString("Start_Time"),
                    rs.getString("End_Time"),
                    rs.getString("Appointment_Time"),
                    rs.getString("isReturned")
                )
                TableviewList.add(tableview)
            }
        } catch (e: java.lang.Exception) {
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

    fun getConnection(): Connection {
        try {
            conn = dbutil.connection
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return conn
    }

    private fun showTableData() {
        val list = getTableviewList()
        no.cellValueFactory = PropertyValueFactory("ID")
        bookId.cellValueFactory = PropertyValueFactory("book_ID")
        bookName.cellValueFactory = PropertyValueFactory("bookName")
        startTime.cellValueFactory = PropertyValueFactory("start_Time")
        endTime.cellValueFactory = PropertyValueFactory("end_Time")
        appointmentTime.cellValueFactory = PropertyValueFactory("appointment_Time")
        isReturned.cellValueFactory = PropertyValueFactory("isReturned")
        queryTable.items = list
    }

    fun showTableData(book: Book?) {
        val list = getTableviewList(book)
        no.cellValueFactory = PropertyValueFactory("ID")
        bookId.cellValueFactory = PropertyValueFactory("book_ID")
        bookName.cellValueFactory = PropertyValueFactory("bookName")
        startTime.cellValueFactory = PropertyValueFactory("start_Time")
        endTime.cellValueFactory = PropertyValueFactory("end_Time")
        appointmentTime.cellValueFactory = PropertyValueFactory("appointment_Time")
        isReturned.cellValueFactory = PropertyValueFactory("isReturned")
        queryTable.items = list
    }
}