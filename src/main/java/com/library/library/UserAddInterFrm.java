package com.library.library;

import com.leewyatt.rxcontrols.controls.RXPasswordField;
import com.leewyatt.rxcontrols.controls.RXTextField;
import dao.UserDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import util.AlertUtil;
import util.Dbutil;
import util.StringUtil;
import util.User;

import java.sql.Connection;

public class UserAddInterFrm {

    private final Dbutil dbutil = new Dbutil();
    private final UserDao userdao = new UserDao();
    @FXML
    private Button add;
    @FXML
    private RXPasswordField c_passwordTxt;
    @FXML
    private Button cancel;
    @FXML
    private RadioButton female;
    @FXML
    private CheckBox isAdmin;
    @FXML
    private RadioButton male;
    @FXML
    private RXPasswordField passwordTxt;
    @FXML
    private ToggleGroup sex;
    @FXML
    private RXTextField userNameTxt;
    private int isAdministrator = 0;
    private String Sex = "";

    @FXML
    public void add(ActionEvent event) throws Exception {
        userAddActionPerformed(event);
    }

    private void userAddActionPerformed(ActionEvent event) throws Exception {
        String username = userNameTxt.getText();
        if (StringUtil.isEmpty(username)) {
            AlertUtil.showError("错误", "错误", "用户名不能为空");
            return;
        }
        String password = passwordTxt.getText();
        if (StringUtil.isEmpty(password)) {
            AlertUtil.showError("错误", "错误", "密码不能为空");
            return;
        }
        String c_password = c_passwordTxt.getText();
        if (StringUtil.isEmpty(c_password)) {
            AlertUtil.showError("错误", "错误", "确认密码不能为空");
            return;
        }
        if (!password.equals(c_password)) {
            AlertUtil.showError("错误", "错误", "两次密码输入不一致");
            return;
        }
        if (male.isSelected()) {
            Sex = "男";
        }
        if (female.isSelected()) {
            Sex = "女";
        }
        isAdministrator = isAdmin.isSelected() ? 1 : 0;

        int i = AlertUtil.showConfirm("二次确认", "确认", "确认添加" + username + "用户吗？");
        if (i == 1) {
            Connection conn = getConnection();
            User user = new User(username, password, Sex, isAdministrator);
            int n = userdao.add(conn, user);
            if (n > 0) {
                AlertUtil.showAlert("提示", "提示", "添加成功");
            } else {
                AlertUtil.showError("错误", "错误", "添加失败");
            }
            int a = AlertUtil.showConfirm("继续添加", "继续添加？", "是否继续添加用户？");
            if (a == 0) {
                Stage nowstage = (Stage) add.getScene().getWindow();
                nowstage.close();
            }
        }
    }

    @FXML
    public void cancel(ActionEvent event) {
        Stage nowstage = (Stage) add.getScene().getWindow();
        nowstage.close();
    }

    @FXML
    public void deleteText(ActionEvent event) {
        TextField tf = (TextField) event.getSource();
        tf.clear();
    }

    public Connection getConnection() {
        Connection conn = null;
        try {
            conn = dbutil.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
}