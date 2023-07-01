package com.library.library;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.leewyatt.rxcontrols.controls.RXTextField;
import com.leewyatt.rxcontrols.event.RXActionEvent;
import dao.BookDao;
import dao.BookTypeDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import util.*;

import java.sql.Connection;
import java.sql.ResultSet;


public class BookAddInterFrm {
    private final Dbutil dbutil = new Dbutil();
    private final BookTypeDao booktypedao = new BookTypeDao();
    private final BookDao bookdao = new BookDao();
    Connection conn = null;
    @FXML
    private Button add;
    @FXML
    private TextField authorTxt;

    @FXML
    private RXTextField totalTxt;

    @FXML
    private TextArea bookDescTxt;

    @FXML
    private TextField bookNameTxt;

    @FXML
    private JFXComboBox<BookType> bookTypeJcb;

    @FXML
    private JFXRadioButton femaleJrb;

    @FXML
    private JFXRadioButton manJrb;

    @FXML
    private TextField priceTxt;

    @FXML
    private Button reset;

    @FXML
    private ToggleGroup sex;

    @FXML
    void initialize() {
        showData();
        priceTxt.setTextFormatter(new TextFormatter<String>(change -> {
            String text = change.getText();
            if (text.matches("[0-9^\\d.]*")) {
                return change;
            }
            return null;
        }));
        totalTxt.setTextFormatter(new TextFormatter<String>(change -> {
            String text = change.getText();
            if (text.matches("[0-9]*")) {
                return change;
            }
            return null;
        }));
    }

    @FXML
    void BookAdd(ActionEvent event) {
        bookAddActionPerformed(event);
    }

    private void bookAddActionPerformed(ActionEvent evt) {
        String bookName = this.bookNameTxt.getText();
        String author = this.authorTxt.getText();
        String price = this.priceTxt.getText();
        String bookDesc = this.bookDescTxt.getText();
        if (StringUtil.isEmpty(bookName)) {
            AlertUtil.showError("错误", "错误", "请输入书名");
            return;
        }
        if (StringUtil.isEmpty(author)) {
            AlertUtil.showError("错误", "错误", "请输入作者");
            return;
        }
        if (StringUtil.isEmpty(price)) {
            AlertUtil.showError("错误", "错误", "请输入价格");
            return;
        }
        String sex = "";
        if (manJrb.isSelected()) {
            sex = "男";
        }
        if (femaleJrb.isSelected()) {
            sex = "女";
        }
        BookType bookType = bookTypeJcb.getSelectionModel().getSelectedItem();
        int bookTypeId = bookType.getId();
        Book book = new Book(bookName, author, sex, Float.parseFloat(price), bookTypeId, bookDesc);
        Connection conn = null;
        try {
            conn = getConnection();
            int addNum = bookdao.add(conn, book);
            if (addNum > 0) {
                AlertUtil.showAlert("提示", "提示", "添加成功");
                resetValue();
            } else {
                AlertUtil.showError("错误", "错误", "添加失败");
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
    }

    @FXML
    void resetValue(ActionEvent event) {
        resetValueActionPerformed(event);
    }

    private void resetValueActionPerformed(ActionEvent event) {
        resetValue();
    }


    private void resetValue() {
        this.bookNameTxt.setText("");
        this.authorTxt.setText("");
        this.priceTxt.setText("");
        this.bookDescTxt.setText("");
        this.manJrb.setSelected(true);
        showData();
    }


    public Connection getConnection() {
        try {
            conn = dbutil.getConnection();
            return conn;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ObservableList<BookType> getBookTypeList() {
        ObservableList<BookType> bookTypeList = FXCollections.observableArrayList();
        BookType bookType;
        try {
            Connection conn = getConnection();
            ResultSet rs = booktypedao.list(conn, new BookType());
            while (rs.next()) {
                bookType = new BookType();
                bookType.setId(rs.getInt("id"));
                bookType.setBookTypeName(rs.getString("bookTypeName"));
                bookTypeList.add(bookType);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bookTypeList;
    }

    public void showData() {
        ObservableList<BookType> list = getBookTypeList();
        bookTypeJcb.setItems(list);
        bookTypeJcb.getSelectionModel().selectFirst();
    }

    @FXML
    void deleteText(RXActionEvent event) {
        RXTextField tf = (RXTextField) event.getSource();
        tf.clear();
    }

}
