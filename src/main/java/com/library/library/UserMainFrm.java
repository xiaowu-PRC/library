package com.library.library;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class UserMainFrm {
    @FXML
    public Label currentUser;
    @FXML
    private Button borrowBook;
    private String Username;

    @FXML
    void initialize(){
        Username=LibraryController.User_Name;
        currentUser.setText("当前用户："+Username);
    }
    @FXML
    void borrowBook(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("BorrowBook.fxml"));
            Parent parent =loader.load();
            Scene scene = new Scene(parent);
            Stage newstage = new Stage();
            newstage.setTitle("借阅图书");
            newstage.setScene(scene);
            newstage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
