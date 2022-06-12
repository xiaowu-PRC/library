package com.library.library;

import dao.BookTypeDao;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import util.BookType;
import util.Dbutil;
import util.StringUtil;
import util.Tableview;

import java.sql.*;

public class ManageInterFrm {
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
    private Dbutil dbutil = new Dbutil();
    private BookTypeDao booktypedao = new BookTypeDao();
    String query = null;
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    Tableview tableview = null;

    @FXML
    void initialize() {
        showData();
        booktypetable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tableview>() {
            @Override
            public void changed(ObservableValue<? extends Tableview> observableValue, Tableview tableview, Tableview t1) {
                if (t1 != null) {
                    idtxt.setText(t1.getId());
                    nametxt.setText(t1.getBookTypeName());
                    desctxt.setText(t1.getBookTypeDesc());
                }
            }
        });
    }

    @FXML
    void change(ActionEvent event) {
        String id = idtxt.getText();
        String name = nametxt.getText();
        String desc = desctxt.getText();
        if (StringUtil.isEmpty(id)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("错误");
            alert.setHeaderText("错误");
            alert.setContentText("请选择一条记录");
            alert.showAndWait();
            return;
        }
        if (StringUtil.isEmpty(name)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("错误");
            alert.setHeaderText("错误");
            alert.setContentText("请输入类型名称");
            alert.showAndWait();
            return;
        }
        BookType bookType=new BookType(Integer.parseInt(id),name,desc);
        Connection conn = null;
        try {
            conn = dbutil.getConnection();
            int modifyNumber = booktypedao.update(conn, bookType);
            if (modifyNumber > 0) {
                Alert alert3 = new Alert(Alert.AlertType.INFORMATION);
                alert3.setTitle("提示");
                alert3.setHeaderText("提示");
                alert3.setContentText("修改成功");
                alert3.showAndWait();
                showData();
            } else {
                Alert alert4=new Alert(Alert.AlertType.ERROR);
                alert4.setTitle("错误");
                alert4.setHeaderText("错误");
                alert4.setContentText("修改失败");
                alert4.showAndWait();
                showData();
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            try {
                dbutil.close(conn);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    @FXML
    void del(ActionEvent event) {
        String id = idtxt.getText();
        if (StringUtil.isEmpty(id)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("错误");
            alert.setHeaderText("错误");
            alert.setContentText("未选择要删除的项");
            alert.showAndWait();
            return;
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("确认");
            alert.setHeaderText("确认");
            alert.setContentText("确认删除？");
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    conn = getConnection();
                    try {
                        int deleteNumber;
                        deleteNumber = booktypedao.delete(conn, id);
                        if (deleteNumber > 0) {
                            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                            alert2.setTitle("提示");
                            alert2.setHeaderText("提示");
                            alert2.setContentText("删除成功");
                            alert2.showAndWait();
                            showData();
                        } else {
                            Alert alert2 = new Alert(Alert.AlertType.ERROR);
                            alert2.setTitle("错误");
                            alert2.setHeaderText("错误");
                            alert2.setContentText("删除失败");
                            alert2.showAndWait();
                            showData();
                        }
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    } finally {
                        try {
                            dbutil.close(conn);
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
            });
        }
    }

    @FXML
    private void search(ActionEvent event) {
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


    public ObservableList<Tableview> getTableviewList() {
        ObservableList<Tableview> TableviewList = FXCollections.observableArrayList();
        String searchBookTypeName = searchtxt.getText();
        Connection conn = getConnection();
        if (StringUtil.isEmpty(searchBookTypeName)) {
            query = "SELECT * FROM booktype";
        } else {
            query = "SELECT * FROM booktype where bookTypeName like '%" + searchtxt.getText() + "%'";
        }
        Statement st;
        ResultSet rs;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            Tableview tableview;
            while (rs.next()) {
                tableview = new Tableview(rs.getString("id"), rs.getString("bookTypeName"), rs.getString("BookTypeDesc"));
                TableviewList.add(tableview);
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
}