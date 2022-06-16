package com.library.library;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import util.Dbutil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ConfirmBorrow {
    @FXML
    public TextField b_bookId;

    @FXML
    public TextField b_bookName;

    @FXML
    private TextField b_endDate;

    @FXML
    private TextField b_startDate;

    @FXML
    private TextField username;

    @FXML
    private Button cancel;

    @FXML
    private Button confirm;

    private Dbutil dbutil = new Dbutil();
    private Connection conn;
    private String uid;
    private String Username;

    @FXML
    void initialize() {
        uid = LibraryController.User_Uid;
        Username = LibraryController.User_Name;
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(7);
        username.setText(Username);
        b_startDate.setText(startDate.toString());
        b_endDate.setText(endDate.toString());
    }

    @FXML
    void cancel(ActionEvent event) {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    void confirm(ActionEvent event) {
        conn = getConnection();
        String sql = "insert into book_appointment(User_ID,Book_ID,Start_Time,End_Time,Appointment_Time) values(?,?,?,?,?)";
        String sql2 = "update book set remainder=remainder-1 where id=?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            PreparedStatement pstmt2 = conn.prepareStatement(sql2);
            pstmt.setString(1, uid);
            pstmt.setString(2, b_bookId.getText());
            pstmt.setString(3, b_startDate.getText());
            pstmt.setString(4, b_endDate.getText());
            pstmt.setString(5, LocalDateTime.now().toString());
            pstmt2.setString(1, b_bookId.getText());
            int result = pstmt.executeUpdate();
            int result2 = pstmt2.executeUpdate();
            if (result > 0 && result2 > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("提示");
                alert.setHeaderText("借阅成功");
                alert.setContentText("您的借阅申请已提交");
                alert.showAndWait();
                Stage stage = (Stage) confirm.getScene().getWindow();
                stage.close();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("提示");
                alert.setHeaderText("借阅失败");
                alert.setContentText("您的借阅申请提交失败");
                alert.showAndWait();
                Stage stage = (Stage) confirm.getScene().getWindow();
                stage.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        try {
            conn = dbutil.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

}
