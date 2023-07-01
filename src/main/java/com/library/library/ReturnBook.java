package com.library.library;

import dao.BookDao;
import io.github.palexdev.materialfx.controls.legacy.MFXLegacyTableView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import util.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReturnBook {

    private final Dbutil dbutil = new Dbutil();
    private final BookDao bookdao = new BookDao();
    private final LocalDateTime nowTime = LocalDateTime.now();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final String formatted = nowTime.format(formatter);
    @FXML
    private TableColumn<Tableview, String> appointmentTime;
    @FXML
    private TableColumn<Tableview, Integer> bookId;
    @FXML
    private TableColumn<Tableview, Integer> bookName;
    @FXML
    private TableColumn<Tableview, Integer> endTime;
    @FXML
    private Button giveback;
    @FXML
    private TableColumn<Tableview, String> isReturned;
    @FXML
    private TableColumn<Tableview, Integer> no;
    @FXML
    private MFXLegacyTableView<Tableview> queryTable;
    @FXML
    private Button reset;
    @FXML
    private TextField s_bookIDTxt;
    @FXML
    private TextField s_bookNameTxt;
    @FXML
    private TextField s_no;
    @FXML
    private Button search;
    @FXML
    private TableColumn<Tableview, String> startTime;
    private Connection conn;
    private int bookid = 0;

    @FXML
    public void initialize() {
        showTableData();
        isReturned.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(item.equals("0") ? "未还" : "已还");
                }
            }
        });

        s_no.setPromptText("请输入或选择一本图书");
        s_bookNameTxt.setPromptText("请输入或选择一本图书");
        s_bookIDTxt.setPromptText("请输入或选择一本图书");

        queryTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                s_no.setText(newSelection.getUid());
                s_bookIDTxt.setText(newSelection.getBook_ID());
                s_bookNameTxt.setText(newSelection.getBookName());
                s_no.setDisable(true);
                s_bookIDTxt.setDisable(true);
                s_bookNameTxt.setDisable(true);
            }
        });
    }

    @FXML
    public void reset(ActionEvent event) {
        resetValue();
    }

    private void resetValue() {
        initialize();
        s_no.clear();
        s_bookNameTxt.clear();
        s_bookIDTxt.clear();
        s_no.setDisable(false);
        s_bookNameTxt.setDisable(false);
        s_bookIDTxt.setDisable(false);
    }

    @FXML
    public void search(ActionEvent event) {
        bookSearchActionPerformed(event);
    }

    private void bookSearchActionPerformed(ActionEvent event) {
        String bookName = s_bookNameTxt.getText();
        String bookID = s_bookIDTxt.getText();
        String id = s_no.getText();

        if (StringUtil.isEmpty(bookName) && StringUtil.isEmpty(bookID) && StringUtil.isEmpty(id)) {
            AlertUtil.showWarning("提示", "提示", "请输入查询条件");
            return;
        }

        if (StringUtil.isNotEmpty(bookID)) {
            bookid = Integer.parseInt(bookID);
        }

        Book book = new Book(id, bookid, bookName);
        showTableData(book);
    }

    @FXML
    public void giveback(ActionEvent event) throws Exception {
        bookReturnActionPerformed(event);
    }

    private void bookReturnActionPerformed(ActionEvent event) throws Exception {
        String id = s_no.getText();
        String bookId = s_bookIDTxt.getText();
        String bookName = s_bookNameTxt.getText();

        Connection conn = getConnection();

        if (StringUtil.isEmpty(id) || StringUtil.isEmpty(bookId) || StringUtil.isEmpty(bookName)) {
            AlertUtil.showError("错误", "错误", "请重新选择");
            return;
        }

        String uid = LibraryController.User_Uid;
        boolean isOverdue = bookdao.overTime(conn, uid, id);

        if (isOverdue) {
            AlertUtil.showError("错误", "错误", "本书已超过最后归还期限，请先续期或联系管理员处理!");
            return;
        }

        int response = AlertUtil.showConfirm("确认", "确认归还下列图书吗？", "编号：" + id + "\r\n图书编号：" + bookId + "\r\n图书名称：" + bookName);

        if (response == 1) {
            conn = getConnection();
            int n = bookdao.returnBook(conn, id, formatted);

            if (n > 0) {
                AlertUtil.showAlert("提示", "提示", "归还成功");
            }

            resetValue();
            showTableData();
        }
    }


    private ObservableList<Tableview> getTableviewList() {
        ObservableList<Tableview> tableviewList = FXCollections.observableArrayList();
        conn = getConnection();
        try {
            ResultSet rs = bookdao.list3(conn);
            while (rs.next()) {
                Tableview tableview = new Tableview(
                        rs.getString("ID"),
                        rs.getString("Book_ID"),
                        rs.getString("bookName"),
                        rs.getString("Start_Time"),
                        rs.getString("End_Time"),
                        rs.getString("Appointment_Time"),
                        rs.getString("isReturned")
                );
                tableviewList.add(tableview);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                dbutil.close(conn);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return tableviewList;
    }

    private ObservableList<Tableview> getTableviewList(Book book) {
        ObservableList<Tableview> tableviewList = FXCollections.observableArrayList();
        conn = getConnection();
        try {
            ResultSet rs = bookdao.list2(conn, book);
            while (rs.next()) {
                Tableview tableview = new Tableview(
                        rs.getString("ID"),
                        rs.getString("Book_ID"),
                        rs.getString("bookName"),
                        rs.getString("Start_Time"),
                        rs.getString("End_Time"),
                        rs.getString("Appointment_Time"),
                        rs.getString("isReturned")
                );
                tableviewList.add(tableview);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                dbutil.close(conn);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return tableviewList;
    }


    private Connection getConnection() {
        try {
            conn = dbutil.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    private void showTableData() {
        ObservableList<Tableview> list = getTableviewList();
        no.setCellValueFactory(new PropertyValueFactory<>("ID"));
        bookId.setCellValueFactory(new PropertyValueFactory<>("book_ID"));
        bookName.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        startTime.setCellValueFactory(new PropertyValueFactory<>("start_Time"));
        endTime.setCellValueFactory(new PropertyValueFactory<>("end_Time"));
        appointmentTime.setCellValueFactory(new PropertyValueFactory<>("appointment_Time"));
        isReturned.setCellValueFactory(new PropertyValueFactory<>("isReturned"));
        queryTable.setItems(list);
    }

    private void showTableData(Book book) {
        ObservableList<Tableview> list = getTableviewList(book);
        no.setCellValueFactory(new PropertyValueFactory<>("ID"));
        bookId.setCellValueFactory(new PropertyValueFactory<>("book_ID"));
        bookName.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        startTime.setCellValueFactory(new PropertyValueFactory<>("start_Time"));
        endTime.setCellValueFactory(new PropertyValueFactory<>("end_Time"));
        appointmentTime.setCellValueFactory(new PropertyValueFactory<>("appointment_Time"));
        isReturned.setCellValueFactory(new PropertyValueFactory<>("isReturned"));
        queryTable.setItems(list);
    }
}