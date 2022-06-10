package com.library.library;

import dao.UserDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
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
    private TextField usernametxt;

    @FXML
    private TextField passwordtxt;

    @FXML
    private Button login;
    private UserDao userdao = new UserDao();
    private Dbutil dbutil = new Dbutil();

    @FXML
    void login(ActionEvent event) {
        String username = usernametxt.getText();
        String password = passwordtxt.getText();
        User user = new User(username, password);
        Connection conn = null;
        try {
            conn = dbutil.getConnection();
            User loginUser = userdao.login(conn, user);
            if (loginUser != null) {
                Stage thirdScene = new Stage();
                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource("MainFrm.fxml"));
                    thirdScene.setTitle("主界面");
                    thirdScene.setScene(new Scene(root));
                    thirdScene.show();
                    Stage stage = (Stage) login.getScene().getWindow();
                    stage.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                alert2.setContentText("登录失败");
                alert2.show();
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
        } else {
            alert3.close();
        }
    }

}