package com.library.library;

import dao.BookDao;
import io.github.palexdev.materialfx.controls.legacy.MFXLegacyTableView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import util.Book;
import util.Dbutil;
import util.Tableview;

import java.sql.Connection;
import java.sql.ResultSet;

public class Query {
    private final Dbutil dbutil = new Dbutil();
    private final BookDao bookdao = new BookDao();
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
    private TableColumn<Tableview, String> no;
    @FXML
    private MFXLegacyTableView<Tableview> queryTable;
    @FXML
    private TableColumn<Tableview, String> startTime;
    private Connection conn = null;

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
    }

    public ObservableList<Tableview> getTableviewList() {
        ObservableList<Tableview> TableviewList = FXCollections.observableArrayList();
        conn = getConnection();
        try {
            Book book=new Book(null,null,null);
            ResultSet rs = bookdao.list2(conn, book);
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
