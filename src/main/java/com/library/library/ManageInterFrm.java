package com.library.library;

import dao.BookDao;
import dao.BookTypeDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import util.*;

import java.sql.Connection;
import java.sql.ResultSet;

public class ManageInterFrm {
    String query = null;
    Connection conn = null;
    @FXML
    private Button search;
    @FXML
    private TextField idtxt;
    @FXML
    private TableView<Tableview> booktypetable;
    @FXML
    private TextArea desctxt;
    @FXML
    private TextField searchtxt;
    @FXML
    private TextField nametxt;
    @FXML
    private Button change;
    @FXML
    private Button del;
    @FXML
    private TableColumn<Tableview, String> no;
    @FXML
    private TableColumn<Tableview, String> name;
    @FXML
    private TableColumn<Tableview, String> desc;
    private final Dbutil dbutil = new Dbutil();
    private final BookTypeDao booktypedao = new BookTypeDao();
    private final BookDao bookdao = new BookDao();
    @FXML
    private TitledPane operPane;


    @FXML
    void initialize() {
        showData();
        booktypetable.getSelectionModel().selectedItemProperty().addListener((observableValue, tableview, t1) -> {
            if (t1 != null) {
                idtxt.setText(t1.getId());
                nametxt.setText(t1.getBookTypeName());
                desctxt.setText(t1.getBookTypeDesc());
                operPane.setDisable(false);
            }
        });
    }

    @FXML
    void change(ActionEvent event) {
        String id = idtxt.getText();
        String name = nametxt.getText();
        String desc = desctxt.getText();
        if (StringUtil.isEmpty(id)) {
            AlertUtil.showError("错误", "错误", "请选择一条记录");
            return;
        }
        if (StringUtil.isEmpty(name)) {
            AlertUtil.showError("错误", "错误", "图书种类名称不能为空");
            return;
        }
        BookType bookType = new BookType(Integer.parseInt(id), name, desc);
        Connection conn = null;
        try {
            conn = dbutil.getConnection();
            int modifyNumber = booktypedao.update(conn, bookType);
            if (modifyNumber > 0) {
                AlertUtil.showAlert("提示", "提示", "修改成功");
                showData();
            } else {
                AlertUtil.showError("错误", "错误", "修改失败");
                showData();
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            try {
                dbutil.close(conn);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @FXML
    void del(ActionEvent event) {
        String id = idtxt.getText();
        if (StringUtil.isEmpty(id)) {
            AlertUtil.showError("错误", "错误", "请选择一条记录");
        } else {
            int n = AlertUtil.showConfirm("提示", "提示", "确定删除该图书种类吗？");
            if (n == 1) {
                conn = getConnection();
                try {
                    boolean flag = bookdao.existBookTypeId(conn, id);
                    if (flag) {
                        AlertUtil.showError("错误", "错误", "该图书种类下有图书，不能删除");
                        return;
                    }
                    int deleteNumber;
                    deleteNumber = booktypedao.delete(conn, id);
                    if (deleteNumber > 0) {
                        AlertUtil.showAlert("提示", "提示", "删除成功");
                    } else {
                        AlertUtil.showError("错误", "错误", "删除失败");
                    }
                    showData();
                    resetValue();
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    try {
                        dbutil.close(conn);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }

    @FXML
    private void search(ActionEvent event) {
        showData();
    }

    public Connection getConnection() {
        try {
            conn = dbutil.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }


    public ObservableList<Tableview> getTableviewList() {
        ObservableList<Tableview> TableviewList = FXCollections.observableArrayList();
        String searchBookTypeName = searchtxt.getText();
        try {
            Connection conn = getConnection();
            BookType bookType = new BookType(searchBookTypeName);
            ResultSet rs = booktypedao.list(conn, bookType);
            Tableview tableview;
            while (rs.next()) {
                tableview = new Tableview(rs.getString("id"), rs.getString("bookTypeName"), rs.getString("BookTypeDesc"));
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

    public void showData() {
        ObservableList<Tableview> list = getTableviewList();
        no.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("bookTypeName"));
        desc.setCellValueFactory(new PropertyValueFactory<>("BookTypeDesc"));
        booktypetable.setItems(list);
    }

    public void resetValue() {
        idtxt.setText("");
        nametxt.setText("");
        desctxt.setText("");
        operPane.setDisable(true);
    }
}