package com.library.library;

import com.leewyatt.rxcontrols.controls.RXPasswordField;
import com.leewyatt.rxcontrols.controls.RXTextField;
import com.leewyatt.rxcontrols.event.RXActionEvent;
import dao.UserDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import util.AlertUtil;
import util.Dbutil;
import util.User;

import java.io.IOException;
import java.sql.Connection;
import java.util.Objects;

public class LibraryController {
    public static String User_Uid;
    public static String User_Name;
    @FXML
    private Button exit;
    @FXML
    private RXTextField usernametxt;
    @FXML
    private RXPasswordField passwordtxt;
    @FXML
    private Button login;
    private UserDao userdao = new UserDao();
    private Dbutil dbutil = new Dbutil();
    private int isAdmin;

    @FXML
    void initialize() {
        usernametxt.setPromptText("请输入用户名");
        passwordtxt.setPromptText("请输入密码");
    }

    @FXML
    public void login(ActionEvent event) {
        String username = usernametxt.getText();
        String password = passwordtxt.getText();
        User user = new User(username, password);
        Connection conn = null;
        try {
            conn = dbutil.getConnection();
            User loginUser = userdao.login(conn, user);
            if (loginUser != null) {
                User_Name = loginUser.getUsername();
                int uid = loginUser.getId();
                User_Uid = String.valueOf(uid);
                if (loginUser.getIsAdmin() == 1) {
                    isAdmin = 1;
                } else {
                    isAdmin = 0;
                }
                if (isAdmin == 1) {
                    AlertUtil.showAlert("登录成功", "欢迎", "欢迎管理员" + loginUser.getUsername());
                    Stage thirdScene = new Stage();
                    Parent root = null;
                    try {
                        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainFrm.fxml")));
                        thirdScene.setTitle("管理员主界面");
                        thirdScene.setScene(new Scene(root));
                        thirdScene.setResizable(false);
                        thirdScene.show();
                        Stage stage = (Stage) login.getScene().getWindow();
                        stage.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (isAdmin == 0) {
                    AlertUtil.showAlert("登录成功", "欢迎", "欢迎亲爱的" + loginUser.getUsername() + "用户登录！");
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("UserMainFrm.fxml"));
                        Parent parent = loader.load();
                        Stage thirdScene = new Stage();
                        Scene scene = new Scene(parent);
                        thirdScene.setTitle("主界面");
                        thirdScene.setResizable(false);
                        thirdScene.setScene(scene);
                        thirdScene.show();
                        Stage stage = (Stage) login.getScene().getWindow();
                        stage.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else if (loginUser == null) {
                AlertUtil.showError("失败", "登录失败", "用户名或密码错误");
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
    void exit(ActionEvent event) {
        int result = AlertUtil.showConfirm("二次确认", "退出", "确定退出？");
        if (result == 1) {
            System.exit(0);
        }
    }

    @FXML
    void deleteText(RXActionEvent event) {
        RXTextField tf = (RXTextField) event.getSource();
        tf.clear();
    }
}