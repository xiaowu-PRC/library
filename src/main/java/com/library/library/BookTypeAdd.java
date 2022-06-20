package com.library.library;

import dao.BookTypeDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import util.AlertUtil;
import util.BookType;
import util.Dbutil;
import util.StringUtil;

import java.sql.Connection;

public class BookTypeAdd {
    @FXML
    private Button BookTypeAdd;

    @FXML
    private TextField typetxt;

    @FXML
    private TextArea desctxt;

    @FXML
    private Button reset;
    private Dbutil dbutil = new Dbutil();
    private BookTypeDao booktypedao = new BookTypeDao();

    @FXML
    void TypeAdd(ActionEvent event) {
        String bookTypeName = this.typetxt.getText();
        String bookTypeDesc = this.desctxt.getText();
        if (StringUtil.isEmpty(bookTypeName)) {
            AlertUtil.showError("错误", "错误", "图书种类名称不能为空");
            return;
        }
        BookType bookType = new BookType(bookTypeName, bookTypeDesc);
        Connection conn;
        try {
            conn = dbutil.getConnection();
            int n = booktypedao.add(conn, bookType);
            if (n == 1) {
                AlertUtil.showAlert("提示", "提示", "添加成功");
                this.reset();
            } else {
                AlertUtil.showError("错误", "错误", "添加失败");
                this.reset();
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    @FXML
    void reset(ActionEvent event) {
        reset();
    }

    private void reset() {
        this.typetxt.setText("");
        this.desctxt.setText("");
    }
}
