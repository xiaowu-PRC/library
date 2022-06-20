package com.library.library;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import util.AlertUtil;

import java.io.IOException;
import java.util.Objects;

public class UserMainFrm {
    @FXML
    public Label currentUser;
    @FXML
    private MenuItem exit;

    @FXML
    private Button borrowBook;

    @FXML
    private Button returnBook;

    @FXML
    private Button query;
    private String Username;
    @FXML
    private MenuItem about;


    @FXML
    void initialize() {
        Username = LibraryController.User_Name;
        currentUser.setText("当前用户：" + Username);
    }

    @FXML
    void borrowBook(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("BorrowBook.fxml"));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setTitle("借阅图书");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void query(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Query.fxml"));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setTitle("查询历史借书记录");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void returnBook(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ReturnBook.fxml"));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setTitle("还书");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void exit(ActionEvent event) {
        int n = AlertUtil.showConfirm("提示", "提示", "确定要退出吗？");
        if (n == 1) {
            Stage newStage = new Stage();
            Parent root;
            try {
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Library.fxml")));
                newStage.setTitle("图书馆管理系统");
                newStage.setScene(new Scene(root));
                newStage.setResizable(false);
                newStage.show();
                Stage stage = (Stage) borrowBook.getScene().getWindow();
                stage.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void about(ActionEvent event) {
        Stage thirdScene = new Stage();
        Parent root;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("About.fxml")));
            thirdScene.setTitle("版权信息©");
            thirdScene.setScene(new Scene(root));
            thirdScene.setResizable(false);
            thirdScene.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
