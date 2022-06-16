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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import util.Dbutil;
import util.User;

import java.io.IOException;
import java.sql.Connection;
import java.util.Optional;

public class LibraryController {
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
    public static String User_Uid;
    public static String User_Name;

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
            int uid=loginUser.getId();
            User_Uid=String.valueOf(uid);
            User_Name=loginUser.getUsername();
            if (loginUser != null) {
                if (loginUser.getIsAdmin() == 1) {
                    isAdmin = 1;
                } else {
                    isAdmin = 0;
                }
                if (isAdmin == 1) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("登录成功（管理员）");
                    alert.setHeaderText("欢迎管理员" + loginUser.getUsername() + "登录");
                    alert.setContentText("登录成功");
                    alert.showAndWait();
                    Stage thirdScene = new Stage();
                    Parent root = null;
                    try {
                        root = FXMLLoader.load(getClass().getResource("MainFrm.fxml"));
                        thirdScene.setTitle("管理员主界面");
                        thirdScene.setScene(new Scene(root));
                        thirdScene.show();
                        Stage stage = (Stage) login.getScene().getWindow();
                        stage.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (isAdmin == 0) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("登录成功（用户）");
                    alert.setHeaderText("欢迎亲爱的" + loginUser.getUsername() + "用户登录");
                    alert.setContentText("登录成功");
                    alert.showAndWait();
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("UserMainFrm.fxml"));
                        Parent parent =loader.load();
                        Stage thirdScene = new Stage();
                        Scene scene = new Scene(parent);
                        thirdScene.setTitle("主界面");
                        thirdScene.setScene(scene);
                        thirdScene.show();
                        Stage stage = (Stage) login.getScene().getWindow();
                        stage.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("登录失败");
                alert.setHeaderText("登录失败");
                alert.setContentText("用户名或密码错误");
                alert.showAndWait();
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
        Alert alert3 = new Alert(Alert.AlertType.CONFIRMATION);
        alert3.setContentText("确定退出吗？");
        Optional<ButtonType> result = alert3.showAndWait();
        if (result.get() == ButtonType.OK) {
            System.exit(0);
        }
    }

    @FXML
    void deleteText(RXActionEvent event) {
        RXTextField tf = (RXTextField) event.getSource();
        tf.clear();
    }
}