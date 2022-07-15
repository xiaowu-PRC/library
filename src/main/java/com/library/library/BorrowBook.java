package com.library.library;

import com.leewyatt.rxcontrols.controls.RXTextField;
import com.leewyatt.rxcontrols.event.RXActionEvent;
import dao.BookDao;
import dao.BookTypeDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.controlsfx.control.SearchableComboBox;
import util.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.time.LocalDate;

public class BorrowBook {
    public ObservableList<BookType> list;
    public ObservableList<BookType> list2;
    @FXML
    private TableColumn<Tableview, String> Author;
    @FXML
    private TableColumn<Tableview, String> BookDesc;
    @FXML
    private TableColumn<Tableview, String> BookName;
    @FXML
    private TableColumn<Tableview, String> BookType;
    @FXML
    private TableColumn<Tableview, String> No;
    @FXML
    private TableColumn<Tableview, String> Price;
    @FXML
    private TableColumn<Tableview, String> Remainder;
    @FXML
    private TableColumn<Tableview, String> Sex;
    @FXML
    private TableColumn<Tableview, String> Total;
    @FXML
    private RXTextField s_idTxt;
    @FXML
    private Button search;
    @FXML
    private RXTextField s_bookNameTxt;
    @FXML
    private Button reset;
    @FXML
    private SearchableComboBox<BookType> s_bookTypeJcb;
    @FXML
    private Button borrow;
    @FXML
    private RXTextField s_authorTxt;
    private final Dbutil dbutil = new Dbutil();
    private final BookTypeDao booktypedao = new BookTypeDao();
    private final BookDao bookdao = new BookDao();
    private Connection conn = null;
    @FXML
    private TableView<Tableview> bookTable;

    @FXML
    void initialize() {
        s_idTxt.setPromptText("请输入或选择一本图书");
        s_bookNameTxt.setPromptText("请输入或选择一本图书");
        s_authorTxt.setPromptText("请输入或选择一本图书");
        showTableData();
        showData("search");
        bookTable.getSelectionModel().selectedItemProperty().addListener((observableValue, tableview, t1) -> {
            if (t1 != null) {
                s_idTxt.setDisable(true);
                s_bookNameTxt.setDisable(true);
                s_authorTxt.setDisable(true);
                s_bookTypeJcb.setDisable(true);
                showData2("modify");
                s_idTxt.setText(t1.getId());
                s_bookNameTxt.setText(t1.getBookName());
                s_authorTxt.setText(t1.getAuthor());
                String bookTypeName = t1.getBookTypeName();
                s_bookTypeJcb.setItems(list2);
                int n = s_bookTypeJcb.getItems().size();
                for (int i = 0; i < n; i++) {
                    if (s_bookTypeJcb.getItems().get(i).getBookTypeName().equals(bookTypeName)) {
                        s_bookTypeJcb.getSelectionModel().select(i);
                        break;
                    }
                }
            }
        });
    }

    public void showData(String type) {
        list = getBookTypeList(type);
        s_bookTypeJcb.setItems(list);
        s_bookTypeJcb.getSelectionModel().select(0);
    }

    public Connection getConnection() {
        try {
            conn = dbutil.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    public ObservableList<Tableview> getTableviewList(Book book) {
        ObservableList<Tableview> TableviewList = FXCollections.observableArrayList();
        conn = getConnection();
        try {
            ResultSet rs = bookdao.list(conn, book);
            Tableview tableview;
            while (rs.next()) {
                tableview = new Tableview(rs.getString("id"), rs.getString("bookName"), rs.getString("author"), rs.getString("sex"), rs.getFloat("price"), rs.getString("bookDesc"), rs.getString("bookTypeName"), rs.getString("total"), rs.getString("remainder"));
                TableviewList.add(tableview);
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

        return TableviewList;

    }

    public void showTableData() {
        Book book = new Book();
        ObservableList<Tableview> list = getTableviewList(book);
        No.setCellValueFactory(new PropertyValueFactory<>("id"));
        BookName.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        Author.setCellValueFactory(new PropertyValueFactory<>("author"));
        Sex.setCellValueFactory(new PropertyValueFactory<>("sex"));
        Price.setCellValueFactory(new PropertyValueFactory<>("price"));
        BookDesc.setCellValueFactory(new PropertyValueFactory<>("bookDesc"));
        BookType.setCellValueFactory(new PropertyValueFactory<>("bookTypeName"));
        Total.setCellValueFactory(new PropertyValueFactory<>("total"));
        Remainder.setCellValueFactory(new PropertyValueFactory<>("remainder"));
        bookTable.setItems(list);
    }

    public void showTableData(Book book) {
        ObservableList<Tableview> list = getTableviewList(book);
        No.setCellValueFactory(new PropertyValueFactory<>("id"));
        BookName.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        Author.setCellValueFactory(new PropertyValueFactory<>("author"));
        Sex.setCellValueFactory(new PropertyValueFactory<>("sex"));
        Price.setCellValueFactory(new PropertyValueFactory<>("price"));
        BookDesc.setCellValueFactory(new PropertyValueFactory<>("bookDesc"));
        BookType.setCellValueFactory(new PropertyValueFactory<>("bookTypeName"));
        Total.setCellValueFactory(new PropertyValueFactory<>("total"));
        Remainder.setCellValueFactory(new PropertyValueFactory<>("remainder"));
        bookTable.setItems(list);
    }

    public ObservableList<BookType> getBookTypeList(String type) {
        ObservableList<BookType> bookTypeList = FXCollections.observableArrayList();
        BookType bookType;
        try {
            Connection conn = getConnection();
            ResultSet rs = booktypedao.list(conn, new BookType());
            if ("search".equals(type)) {
                bookType = new BookType();
                bookType.setBookTypeName("请选择...");
                bookType.setId(-1);
                bookTypeList.add(bookType);
            }
            while (rs.next()) {
                bookType = new BookType();
                bookType.setBookTypeName(rs.getString("bookTypeName"));
                bookType.setId(rs.getInt("id"));
                if ("search".equals(type)) {
                    bookTypeList.add(bookType);
                }
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
        return bookTypeList;
    }

    public ObservableList<BookType> getBookTypeList2(String type) {
        ObservableList<BookType> bookTypeList = FXCollections.observableArrayList();
        BookType bookType;
        try {
            conn = getConnection();
            ResultSet rs = booktypedao.list(conn, new BookType());
            while (rs.next()) {
                bookType = new BookType();
                bookType.setBookTypeName(rs.getString("bookTypeName"));
                bookType.setId(rs.getInt("id"));
                if ("modify".equals(type)) {
                    bookTypeList.add(bookType);
                }
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
        return bookTypeList;
    }

    @FXML
    void search(ActionEvent event) {
        bookSearchActionPerformed(event);
    }

    private void bookSearchActionPerformed(ActionEvent event) {
        String bookName = this.s_bookNameTxt.getText();
        String author = this.s_authorTxt.getText();
        BookType bookType = this.s_bookTypeJcb.getSelectionModel().getSelectedItem();
        int bookTypeId = bookType.getId();
        if (StringUtil.isEmpty(bookName) && StringUtil.isEmpty(author) && bookTypeId == -1 && StringUtil.isEmpty(s_idTxt.getText())) {
            AlertUtil.showWarning("提示", "提示", "请输入查询条件");
        }
        Book book;
        if (StringUtil.isNotEmpty(s_idTxt.getText())) {
            int bookId = Integer.parseInt(s_idTxt.getText());
            book = new Book(bookId, bookName, author, bookTypeId);
        } else {
            book = new Book(bookName, author, bookTypeId);
        }
        showTableData(book);
    }

    @FXML
    void deleteText(RXActionEvent event) {
        RXTextField tf = (RXTextField) event.getSource();
        tf.clear();
    }

    @FXML
    void borrow(ActionEvent event) throws Exception {
        bookBorrowActionPerformed(event);
    }

    private void bookBorrowActionPerformed(ActionEvent event) throws Exception {
        String uid = LibraryController.User_Uid;
        String username = LibraryController.User_Name;
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(7);
        String time1 = startDate.toString();
        String time2 = endDate.toString();
        String bookName = this.s_bookNameTxt.getText();
        String author = this.s_authorTxt.getText();
        BookType bookType = this.s_bookTypeJcb.getSelectionModel().getSelectedItem();
        String book_type = this.s_bookTypeJcb.getSelectionModel().getSelectedItem().toString();
        int bookTypeId = bookType.getId();
        String bookId = s_idTxt.getText();
        if (StringUtil.isEmpty(bookName) || StringUtil.isEmpty(author) || bookTypeId == -1 || StringUtil.isEmpty(s_idTxt.getText())) {
            AlertUtil.showError("错误", "错误", "请重新选择");
            return;
        }
        conn = getConnection();
        boolean a = bookdao.checkOverTime(conn, uid);
        if (a) {
            AlertUtil.showError("错误", "错误", "您有超过归还期限的书，未处理前禁止再次借书!");
            return;
        }
        boolean b = bookdao.notReturnBook(conn, uid, bookId);
        if (b) {
            AlertUtil.showError("错误", "错误", "您已借阅本书，不允许多本借阅!");
            return;
        }
        int i = AlertUtil.showConfirm("确认", "是否确认借阅？", "借阅人:" + username + "\r\n借阅图书:" + bookName + "\r\n图书编号:" + bookId + "\r\n图书类别:" + book_type + "\r\n借阅开始:" + startDate + "\r\n最晚归还:" + endDate);
        if (i == 1) {
            conn = getConnection();
            int result = 0;
            try {
                result = bookdao.borrow(conn, uid, bookId, time1, time2);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (result > 1) {
                AlertUtil.showAlert("借阅成功", "提示", "借阅成功");
            } else {
                AlertUtil.showError("借阅失败", "提示", "借阅失败");
            }
            resetValue();
            dbutil.close(conn);
        }
    }

    public void showData2(String type) {
        list2 = getBookTypeList2(type);
        s_bookTypeJcb.setItems(list2);
        s_bookTypeJcb.getSelectionModel().select(0);
    }

    @FXML
    void reset(ActionEvent event) {
        resetValue();
    }

    public void resetValue() {
        initialize();
        s_idTxt.clear();
        s_bookNameTxt.clear();
        s_authorTxt.clear();
        s_bookTypeJcb.getSelectionModel().select(0);
        s_idTxt.setDisable(false);
        s_bookNameTxt.setDisable(false);
        s_authorTxt.setDisable(false);
        s_bookTypeJcb.setDisable(false);
    }
}
