package com.library.library;

import com.leewyatt.rxcontrols.controls.RXTextField;
import dao.BookAppointmentDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import util.AlertUtil;
import util.AppointmentTable;
import util.Dbutil;
import util.StringUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class BookAppointmentInterFrm {

    private final BookAppointmentDao bookAppointmentDao = new BookAppointmentDao();
    private final Dbutil dbutil = new Dbutil();
    @FXML
    private TableView<AppointmentTable> appointmentTable;
    @FXML
    private TableColumn<AppointmentTable, Integer> b_id;
    @FXML
    private TableColumn<AppointmentTable, String> b_name;
    @FXML
    private TableColumn<AppointmentTable, String> b_type;
    @FXML
    private TextField bidtxt;
    @FXML
    private TextField bookNametxt;
    @FXML
    private CheckBox extendable;
    @FXML
    private TextField idtxt;
    @FXML
    private TableColumn<AppointmentTable, Integer> no;
    @FXML
    private TitledPane operationPanel;
    @FXML
    private CheckBox returned;
    @FXML
    private Button search;
    @FXML
    private Button submit;
    @FXML
    private TableColumn<AppointmentTable, String> t_borrowDate;
    @FXML
    private TableColumn<AppointmentTable, String> t_endDate;
    @FXML
    private TableColumn<AppointmentTable, Integer> t_extendable;
    @FXML
    private TableColumn<AppointmentTable, Integer> t_returned;
    @FXML
    private TableColumn<AppointmentTable, String> t_returnedTime;
    @FXML
    private TableColumn<AppointmentTable, Integer> u_no;
    @FXML
    private RXTextField uid;
    @FXML
    private TextField uidtxt;
    @FXML
    private TableColumn<AppointmentTable, String> userName;

    @FXML
    void initialize() {
        appointmentTable.getSelectionModel().selectedItemProperty()
                .addListener((obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        idtxt.setText(Integer.toString(newSelection.getID()));
                        uidtxt.setText(Integer.toString(newSelection.getUser_ID()));
                        bidtxt.setText(Integer.toString(newSelection.getBook_ID()));
                        bookNametxt.setText(newSelection.getBookName());
                        extendable.setSelected(newSelection.getExtendable() == 1);
                        if (newSelection.getIsReturned() == 1) {
                            returned.setSelected(true);
                            operationPanel.setDisable(true);
                        } else {
                            returned.setSelected(false);
                            operationPanel.setDisable(false);
                        }
                    }
                });
    }

    @FXML
    void deleteText(ActionEvent event) {
        TextField tf = (TextField) event.getSource();
        tf.clear();
    }

    @FXML
    void search(ActionEvent event) {
        if (StringUtil.isEmpty(uid.getText())) {
            AlertUtil.showError("错误", "错误", "请输入用户编号!");
        } else {
            showTableData();
        }
    }

    @FXML
    void submit(ActionEvent event) throws SQLException {
        int id = Integer.parseInt(idtxt.getText());
        int Extendable = extendable.isSelected() ? 1 : 0;
        int isReturned = returned.isSelected() ? 1 : 0;

        int a = AlertUtil.showConfirm("确认", "二次确认", "确认操作?");
        String returnTime = "";
        if (a == 1) {
            Connection conn = getConnection();
            if (returned.isSelected()) {
                returnTime = LocalDateTime.now().toString();
            }
            int b = bookAppointmentDao.update(conn, id, Extendable, isReturned, returnTime);
            if (b > 0) {
                AlertUtil.showAlert("提示", "提示", "修改成功!");
            } else {
                AlertUtil.showError("错误", "错误", "修改失败!");
            }
            resetValue();
        }
    }

    private ObservableList<AppointmentTable> getTableviewList() {
        int uid = Integer.parseInt(this.uid.getText());
        ObservableList<AppointmentTable> TableviewList = FXCollections.observableArrayList();
        Connection conn = getConnection();
        try {
            ResultSet rs = bookAppointmentDao.list(conn, uid);
            while (rs.next()) {
                AppointmentTable appointmentTable = new AppointmentTable(
                        rs.getInt("ID"),
                        rs.getInt("User_ID"),
                        rs.getString("userName"),
                        rs.getInt("Book_ID"),
                        rs.getString("bookTypeName"),
                        rs.getString("bookName"),
                        rs.getString("Start_Time"),
                        rs.getString("End_Time"),
                        rs.getInt("Extendable"),
                        rs.getInt("isReturned"),
                        rs.getString("Return_Time")
                );
                TableviewList.add(appointmentTable);
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
        return TableviewList;
    }

    private void showTableData() {
        ObservableList<AppointmentTable> list = getTableviewList();
        no.setCellValueFactory(new PropertyValueFactory<>("ID"));
        u_no.setCellValueFactory(new PropertyValueFactory<>("User_ID"));
        userName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        b_id.setCellValueFactory(new PropertyValueFactory<>("Book_ID"));
        b_type.setCellValueFactory(new PropertyValueFactory<>("bookTypeName"));
        b_name.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        t_borrowDate.setCellValueFactory(new PropertyValueFactory<>("Start_Time"));
        t_endDate.setCellValueFactory(new PropertyValueFactory<>("End_Time"));
        t_extendable.setCellValueFactory(new PropertyValueFactory<>("Extendable"));
        t_returned.setCellValueFactory(new PropertyValueFactory<>("isReturned"));
        t_returnedTime.setCellValueFactory(new PropertyValueFactory<>("Return_Time"));
        appointmentTable.setItems(list);
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

    private void resetValue() {
        idtxt.setText("");
        uidtxt.setText("");
        bidtxt.setText("");
        bookNametxt.setText("");
        extendable.setSelected(false);
        returned.setSelected(false);
        operationPanel.setDisable(true);
        showTableData();
    }
}