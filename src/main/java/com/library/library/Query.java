package com.library.library;

import dao.BookDao;
import io.github.palexdev.materialfx.controls.legacy.MFXLegacyTableView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import util.AlertUtil;
import util.Dbutil;
import util.Tableview;

import java.sql.Connection;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.Optional;

public class Query {
    private final Dbutil dbutil = new Dbutil();
    private final BookDao bookdao = new BookDao();
    public LocalDate date;
    public String id;
    @FXML
    private TableColumn<Tableview, String> appointmentTime;
    @FXML
    private TableColumn<Tableview, String> bookId;
    @FXML
    private TableColumn<Tableview, String> bookName;
    @FXML
    private TableColumn<Tableview, String> endTime;
    @FXML
    private TableColumn<Tableview, String> isReturned;
    @FXML
    private Button renewal;
    @FXML
    private TableColumn<Tableview, String> no;
    @FXML
    private MFXLegacyTableView<Tableview> queryTable;
    @FXML
    private TableColumn<Tableview, String> startTime;
    private Connection conn = null;
    private String returned;

    @FXML
    void initialize() {
        showTableData();
        isReturned.setCellFactory(tc -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty && item != null) {
                    if ("0".equals(item)) {
                        setText("未还");
                    } else {
                        setText("已还");
                    }
                }
            }
        });
        queryTable.getSelectionModel().selectedItemProperty().addListener((observableValue, tableview, t1) -> {
            if (t1 != null) {
                date = LocalDate.parse(t1.getEnd_Time());
                id = t1.getUid();
                returned = t1.getIsReturned();
            }
        });
    }

    @FXML
    void renewal(ActionEvent event) throws Exception {
        if ("1".equals(returned)) {
            AlertUtil.showError("错误", "错误", "此图书已还，无法续期");
            return;
        }
        conn = getConnection();
        boolean a = bookdao.Extended(conn, id);
        if (a) {
            AlertUtil.showError("错误", "错误", "根据相关规定要求，所有借阅最多续期一次，本书您已续过期!");
            return;
        }
        ChoiceDialog<Integer> dialog = new ChoiceDialog<>(7, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14);
        dialog.setTitle("选择天数");
        dialog.setHeaderText("请选择天数\n最长为14天\n未选择或无效选择则默认为7天");
        dialog.setContentText("请选择天数");
        Optional<Integer> addDate = dialog.showAndWait();
        if (addDate.isPresent()) {
            LocalDate newEndDate = date.plusDays(addDate.orElse(7));
            int n = bookdao.renewal(conn, id, newEndDate.toString());
            if (n > 0) {
                AlertUtil.showAlert("提示", "提示", "续借成功");
            } else {
                AlertUtil.showAlert("提示", "提示", "续借失败");
            }
            showTableData();
        }
    }


    public ObservableList<Tableview> getTableviewList() {
        ObservableList<Tableview> TableviewList = FXCollections.observableArrayList();
        conn = getConnection();
        try {
            ResultSet rs = bookdao.list4(conn);
            Tableview tableview;
            while (rs.next()) {
                tableview = new Tableview(rs.getString("ID"), rs.getString("Book_ID"), rs.getString("bookName"), rs.getString("Start_Time"), rs.getString("End_Time"), rs.getString("Appointment_Time"), rs.getString("isReturned"));
                TableviewList.add(tableview);
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

    public Connection getConnection() {
        try {
            conn = dbutil.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    public void showTableData() {
        ObservableList<Tableview> list = getTableviewList();
        no.setCellValueFactory(new PropertyValueFactory<>("ID"));
        bookId.setCellValueFactory(new PropertyValueFactory<>("book_ID"));
        bookName.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        startTime.setCellValueFactory(new PropertyValueFactory<>("start_Time"));
        endTime.setCellValueFactory(new PropertyValueFactory<>("end_Time"));
        appointmentTime.setCellValueFactory(new PropertyValueFactory<>("appointment_Time"));
        isReturned.setCellValueFactory(new PropertyValueFactory<>("isReturned"));
        queryTable.setItems(list);
    }
}
