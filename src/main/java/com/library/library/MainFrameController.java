package com.library.library;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import util.AlertUtil;

import java.io.IOException;
import java.util.Objects;

public class MainFrameController {
    @FXML
    private MenuItem aboutus;
    @FXML
    private MenuBar bar;
    @FXML
    private MenuItem TypeAdd;

    @FXML
    private MenuItem bookAdd;

    @FXML
    private MenuItem maintenance;

    @FXML
    private MenuItem BookManage;

    @FXML
    private MenuItem exit;
    @FXML
    private MenuItem userAdd;

    @FXML
    private MenuItem userManage;


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

    @FXML
    void bookAdd(ActionEvent event) {
        Stage newscene = new Stage();
        Parent root;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("BookAddInterFrm.fxml")));
            newscene.setTitle("图书添加");
            newscene.setScene(new Scene(root));
            newscene.setResizable(false);
            newscene.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void add(ActionEvent event) {
        Stage newscene = new Stage();
        Parent root;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("BookTypeAdd.fxml")));
            newscene.setTitle("添加图书");
            newscene.setScene(new Scene(root));
            newscene.setResizable(false);
            newscene.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void maintenance(ActionEvent event) {
        Stage newscene = new Stage();
        Parent root;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("ManageInterFrm.fxml")));
            newscene.setTitle("图书维护");
            newscene.setScene(new Scene(root));
            newscene.setResizable(false);
            newscene.show();
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
                Stage stage = (Stage) bar.getScene().getWindow();
                stage.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void BookManage(ActionEvent event) {
        Stage newscene = new Stage();
        Parent root;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("BookManageInterFrm.fxml")));
            newscene.setTitle("图书管理");
            newscene.setScene(new Scene(root));
            newscene.setResizable(false);
            newscene.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void userAdd(ActionEvent event) {
        Stage newscene = new Stage();
        Parent root;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("UserAddInterFrm.fxml")));
            newscene.setTitle("用户添加");
            newscene.setScene(new Scene(root));
            newscene.setResizable(false);
            newscene.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void userManage(ActionEvent event) {
        Stage newscene = new Stage();
        Parent root;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("UserManageInterFrm.fxml")));
            newscene.setTitle("用户管理");
            newscene.setScene(new Scene(root));
            newscene.setResizable(false);
            newscene.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
