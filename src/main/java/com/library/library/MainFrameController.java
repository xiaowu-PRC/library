package com.library.library;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.io.IOException;

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
    void about(ActionEvent event) {
        Stage thirdScene = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("About.fxml"));
            thirdScene.setTitle("版权信息©");
            thirdScene.setScene(new Scene(root));
            thirdScene.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void bookAdd(ActionEvent event) {
        Stage newscene = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("BookAddInterFrm.fxml"));
            newscene.setTitle("图书添加");
            newscene.setScene(new Scene(root));
            newscene.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }


    @FXML
    void add(ActionEvent event) {
        Stage newscene = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("BookTypeAdd.fxml"));
            newscene.setTitle("添加图书");
            newscene.setScene(new Scene(root));
            newscene.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void maintenance(ActionEvent event) {
        Stage newscene = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("ManageInterFrm.fxml"));
            newscene.setTitle("图书维护");
            newscene.setScene(new Scene(root));
            newscene.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void BookManage(ActionEvent event) {
        Stage newscene = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("BookManageInterFrm.fxml"));
            newscene.setTitle("图书管理");
            newscene.setScene(new Scene(root));
            newscene.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
