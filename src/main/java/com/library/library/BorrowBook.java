package com.library.library;

import com.leewyatt.rxcontrols.controls.RXTextField;
import com.leewyatt.rxcontrols.event.RXActionEvent;
import dao.BookDao;
import dao.BookTypeDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.controlsfx.control.SearchableComboBox;
import util.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;

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
    private Dbutil dbutil = new Dbutil();
    private BookTypeDao booktypedao = new BookTypeDao();
    private BookDao bookdao = new BookDao();
    private Connection conn = null;
    @FXML
    private TableView<Tableview> bookTable;
    private Book book;

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
                    BookType item = s_bookTypeJcb.getItems().get(i);
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
        BookType bookType = null;
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
        BookType bookType = null;
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
    void borrow(ActionEvent event) throws IOException {
        String bookName = this.s_bookNameTxt.getText();
        String author = this.s_authorTxt.getText();
        BookType bookType = this.s_bookTypeJcb.getSelectionModel().getSelectedItem();
        int bookTypeId = bookType.getId();
        if (StringUtil.isEmpty(bookName) || StringUtil.isEmpty(author) || bookTypeId == -1 || StringUtil.isEmpty(s_idTxt.getText())) {
            AlertUtil.showError("错误", "错误", "请重新选择");
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ConfirmBorrow.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        ConfirmBorrow target = loader.getController();
        target.b_bookName.setText(s_bookNameTxt.getText());
        target.b_bookId.setText(s_idTxt.getText());
        stage.setTitle("确认借阅");
        stage.setScene(scene);
        stage.show();
        Stage stage1 = (Stage) search.getScene().getWindow();
        stage1.close();
    }

    public void showData2(String type) {
        list2 = getBookTypeList2(type);
        s_bookTypeJcb.setItems(list2);
        s_bookTypeJcb.getSelectionModel().select(0);
    }

    @FXML
    void reset(ActionEvent event) {
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
