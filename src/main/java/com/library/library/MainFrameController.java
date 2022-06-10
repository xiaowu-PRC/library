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
    void about(ActionEvent event) {
        Stage thirdScene = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("About.fxml"));
            thirdScene.setTitle("主界面");
            thirdScene.setScene(new Scene(root));
            thirdScene.show();
            Stage stage = (Stage) bar.getScene().getWindow();
            stage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
