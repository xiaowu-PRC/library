package com.library.library;

import com.leewyatt.rxcontrols.controls.RXTextField;
import com.leewyatt.rxcontrols.event.RXActionEvent;
import dao.UserDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import util.*;

import java.sql.Connection;
import java.sql.ResultSet;

public class UserManageInterFrm {

    private final Dbutil dbutil = new Dbutil();
    private final UserDao userdao = new UserDao();
    @FXML
    private TableColumn<UserTable, Integer> c_isAdmin;
    @FXML
    private TableColumn<UserTable, String> c_password;
    @FXML
    private TableColumn<UserTable, String> c_sex;
    @FXML
    private TableColumn<UserTable, String> c_userName;
    @FXML
    private Button del;
    @FXML
    private RadioButton female;
    @FXML
    private CheckBox isAdmin;
    @FXML
    private TextField m_no;
    @FXML
    private RadioButton man;
    @FXML
    private Button modify;
    @FXML
    private TableColumn<UserTable, Integer> no;
    @FXML
    private TextField password;
    @FXML
    private RXTextField s_no;
    @FXML
    private RXTextField s_userName;
    @FXML
    private Button search;
    @FXML
    private ToggleGroup sex;
    @FXML
    private TextField userName;
    @FXML
    private TitledPane userPane;
    @FXML
    private TableView<UserTable> userTable;
    private Connection conn = null;

    @FXML
    public void initialize() {
        showTableData();
        c_isAdmin.setCellFactory(new Callback<>() {
            @Override
            public TableCell<UserTable, Integer> call(TableColumn<UserTable, Integer> param) {
                return new TableCell<>() {
                    @Override
                    protected void updateItem(Integer item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setText(null);
                        } else {
                            setText(item == 0 ? "否" : "是");
                        }
                    }
                };
            }
        });

        userTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                m_no.setText(String.valueOf(newValue.getId()));
                userName.setText(newValue.getUserName());
                password.setText(newValue.getPassword());
                if ("男".equals(newValue.getSex())) {
                    man.setSelected(true);
                }
                if ("女".equals(newValue.getSex())) {
                    female.setSelected(true);
                }
                if (newValue.getIsAdmin() == 1) {
                    isAdmin.setSelected(true);
                }
                if (newValue.getIsAdmin() == 0) {
                    isAdmin.setSelected(false);
                }
                userPane.setDisable(false);
            }
        });
    }

    @FXML
    void del(ActionEvent event) throws Exception {
        userDelActionPerformed(event);
    }

    private void userDelActionPerformed(ActionEvent event) throws Exception {
        conn = getConnection();
        Integer id = Integer.valueOf(m_no.getText());
        User user = new User(id);
        int i = AlertUtil.showConfirm("二次确认", "确认删除？", "确认删除用户：$user?");
        if (i == 1) {
            int n = userdao.delete(conn, user);
            if (n > 0) {
                AlertUtil.showAlert("提示", "提示", "删除成功");
                showTableData();
            } else {
                AlertUtil.showAlert("提示", "提示", "删除失败");
                showTableData();
            }
            resetValue();
        }
    }

    @FXML
    void modify(ActionEvent event) throws Exception {
        userModifyActionPerformed(event);
    }

    private void userModifyActionPerformed(ActionEvent event) throws Exception {
        Integer id = Integer.valueOf(m_no.getText());
        String username = userName.getText();
        String pwd = password.getText();
        String Sex = "";
        if (man.isSelected()) {
            Sex = "男";
        }
        if (female.isSelected()) {
            Sex = "女";
        }
        int isAdministrator = 0;
        if (isAdmin.isSelected()) {
            isAdministrator = 1;
        }
        conn = getConnection();
        User user = new User(username, pwd, Sex, isAdministrator, id);
        int c = userdao.update(conn, user);
        if (c > 0) {
            AlertUtil.showAlert("提示", "提示", "修改成功");
            showTableData();
        } else {
            AlertUtil.showAlert("提示", "提示", "修改失败");
            showTableData();
        }
        resetValue();
    }

    @FXML
    void search(ActionEvent event) {
        userSearchActionPerformed(event);
    }

    private void userSearchActionPerformed(ActionEvent event) {
        Integer id = null;
        if (StringUtil.isNotEmpty(s_no.getText())) {
            id = Integer.valueOf(s_no.getText());
        }
        String userName = s_userName.getText();
        User user = new User(id, userName);
        showTableData(user);
        userPane.setDisable(true);
    }


    public Connection getConnection() {
        try {
            conn = dbutil.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    private ObservableList<UserTable> getTableviewList() {
        ObservableList<UserTable> tableviewList = FXCollections.observableArrayList();
        conn = getConnection();
        try {
            User user = new User();
            ResultSet rs = userdao.list(conn, user);
            UserTable usertable;
            while (rs.next()) {
                usertable = new UserTable(
                        rs.getInt("id"),
                        rs.getString("userName"),
                        rs.getString("password"),
                        rs.getString("sex"),
                        rs.getInt("isAdmin")
                );
                tableviewList.add(usertable);
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


    private ObservableList<UserTable> getTableviewList(User user) {
        ObservableList<UserTable> tableviewList = FXCollections.observableArrayList();
        conn = getConnection();
        try {
            ResultSet rs = userdao.list(conn, user);
            UserTable usertable;
            while (rs.next()) {
                usertable = new UserTable(
                        rs.getInt("id"),
                        rs.getString("userName"),
                        rs.getString("password"),
                        rs.getString("sex"),
                        rs.getInt("isAdmin")
                );
                tableviewList.add(usertable);
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


    private void showTableData() {
        ObservableList<UserTable> list = getTableviewList();
        no.setCellValueFactory(new PropertyValueFactory<>("id"));
        c_userName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        c_password.setCellValueFactory(new PropertyValueFactory<>("password"));
        c_sex.setCellValueFactory(new PropertyValueFactory<>("sex"));
        c_isAdmin.setCellValueFactory(new PropertyValueFactory<>("isAdmin"));
        userTable.setItems(list);
    }

    private void showTableData(User user) {
        ObservableList<UserTable> list = getTableviewList(user);
        no.setCellValueFactory(new PropertyValueFactory<>("id"));
        c_userName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        c_password.setCellValueFactory(new PropertyValueFactory<>("password"));
        c_sex.setCellValueFactory(new PropertyValueFactory<>("sex"));
        c_isAdmin.setCellValueFactory(new PropertyValueFactory<>("isAdmin"));
        userTable.setItems(list);
    }

    private void resetValue() {
        s_no.setText("");
        userName.setText("");
        s_userName.setText("");
        password.setText("");
        s_no.setText("");
        man.setSelected(false);
        female.setSelected(false);
        isAdmin.setSelected(false);
        userPane.setDisable(true);
    }

    @FXML
    void deleteText(RXActionEvent event) {
        RXTextField tf = (RXTextField) event.getSource();
        tf.clear();
    }
}