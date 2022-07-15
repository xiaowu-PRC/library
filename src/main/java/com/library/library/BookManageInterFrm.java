package com.library.library;

import com.jfoenix.controls.JFXRadioButton;
import com.leewyatt.rxcontrols.controls.RXTextField;
import com.leewyatt.rxcontrols.event.RXActionEvent;
import dao.BookDao;
import dao.BookTypeDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import util.*;

import java.sql.Connection;
import java.sql.ResultSet;

public class BookManageInterFrm {
    private final BookType bookType = new BookType();
    private final BookTypeDao booktypedao = new BookTypeDao();
    private final BookDao bookdao = new BookDao();
    public ObservableList<BookType> list;
    public ObservableList<BookType> list2;
    Connection conn = null;
    String query = null;
    final Dbutil dbutil = new Dbutil();
    @FXML
    private TableColumn<Tableview, String> Author;
    @FXML
    private TableColumn<Tableview, String> BookDesc;

    @FXML
    private TableColumn<Tableview, String> BookName;

    @FXML
    private TableColumn<Tableview, String> BookType;

    @FXML
    private TableColumn<Tableview, String> No;

    @FXML
    private TableColumn<Tableview, String> Price;

    @FXML
    private TableColumn<Tableview, String> Sex;

    @FXML
    private TableView<Tableview> bookTable;

    @FXML
    private TextField priceTxt;

    @FXML
    private TextField s_authorTxt;

    @FXML
    private TextField s_bookNameTxt;

    @FXML
    private ComboBox<BookType> s_bookTypeJcb;

    @FXML
    private Button search;

    @FXML
    private TextField authorTxt;

    @FXML
    private TextArea bookDescTxt;

    @FXML
    private TextField bookNameTxt;

    @FXML
    private ComboBox<BookType> bookTypeJcb;

    @FXML
    private JFXRadioButton femaleJrb;

    @FXML
    private TextField idTxt;

    @FXML
    private JFXRadioButton manJrb;

    @FXML
    private TitledPane operatePanel;

    @FXML
    private Button update;

    @FXML
    private Button del;
    @FXML
    private RXTextField remainderTxt;
    @FXML
    private RXTextField totalTxt;
    @FXML
    private TableColumn<Tableview, String> Remainder;
    @FXML
    private TableColumn<Tableview, String> Total;

    @FXML
    void initialize() {
        idTxt.setPromptText("请输入图书编号");
        bookNameTxt.setPromptText("请输入图书名称");
        authorTxt.setPromptText("请输入图书作者");
        priceTxt.setPromptText("请输入图书价格");
        remainderTxt.setPromptText("请输入图书剩余数量");
        totalTxt.setPromptText("请输入图书总数量");
        bookTypeJcb.setPromptText("请选择...");
        showData("search");
        showTableData();
        bookTable.getSelectionModel().selectedItemProperty().addListener((observableValue, tableview, t1) -> {
            showData2("modify");
            if (t1 != null) {
                operatePanel.setDisable(false);
                idTxt.setText(t1.getId());
                bookNameTxt.setText(t1.getBookName());
                authorTxt.setText(t1.getAuthor());
                String sex = t1.getSex();
                if ("男".equals(sex)) {
                    manJrb.setSelected(true);
                } else if ("女".equals(sex)) {
                    femaleJrb.setSelected(true);
                }
                priceTxt.setText(t1.getPrice() + "");
                bookDescTxt.setText(t1.getBookDesc());
                String bookTypeName = t1.getBookTypeName();
                totalTxt.setText(t1.getTotal());
                remainderTxt.setText(t1.getRemainder());
                bookTypeJcb.setItems(list2);
                int n = bookTypeJcb.getItems().size();
                for (int i = 0; i < n; i++) {
                    BookType item = bookTypeJcb.getItems().get(i);
                    if (bookTypeJcb.getItems().get(i).getBookTypeName().equals(bookTypeName)) {
                        bookTypeJcb.getSelectionModel().select(i);
                        break;
                    }
                }
            }
        });
        totalTxt.setTextFormatter(new TextFormatter<String>(change -> {
            String text = change.getText();
            if (text.matches("[0-9]*")) {
                return change;
            }
            return null;
        }));
        remainderTxt.setTextFormatter(new TextFormatter<String>(change -> {
            String text = change.getText();
            if (text.matches("[0-9]*")) {
                return change;
            }
            return null;
        }));
        priceTxt.setTextFormatter(new TextFormatter<String>(change -> {
            String text = change.getText();
            if (text.matches("[0-9^\\d.]*")) {
                return change;
            }
            return null;
        }));
    }


    @FXML
    void search(ActionEvent event) {
        bookSearchActionPerformed(event);
    }

    private void bookSearchActionPerformed(ActionEvent event) {
        String bookName = this.s_bookNameTxt.getText();
        String author = this.s_authorTxt.getText();
        BookType bookType = this.s_bookTypeJcb.getSelectionModel().getSelectedItem();
        int bookTypeId = bookType.getId();
        Book book = new Book(bookName, author, bookTypeId);
        showTableData(book);
        operatePanel.setDisable(true);
    }

    @FXML
    void bookUpdate(ActionEvent event) {
        bookUpdateActionPerformed(event);
    }

    private void bookUpdateActionPerformed(ActionEvent event) {
        String id = idTxt.getText();
        String bookName = bookNameTxt.getText();
        String author = authorTxt.getText();
        String price = priceTxt.getText();
        String bookDesc = bookDescTxt.getText();
        String total = totalTxt.getText();
        String remainder = remainderTxt.getText();
        int c_total = Integer.parseInt(total);
        int c_remainder = Integer.parseInt(remainder);
        if (StringUtil.isEmpty(id)) {
            AlertUtil.showError("错误", "错误", "图书编号不能为空");
            return;
        }
        if (StringUtil.isEmpty(bookName)) {
            AlertUtil.showError("错误", "错误", "图书名称不能为空");
            return;
        }
        if (StringUtil.isEmpty(author)) {
            AlertUtil.showError("错误", "错误", "图书作者不能为空");
            return;
        }
        if (StringUtil.isEmpty(price)) {
            AlertUtil.showError("我觉得我有必要说两句", "我在你们这才买了几件商品,我已经发现了三个问题", "第三,这些书已经算完了账,这位小同志,你怎么不给我数据呢？");
            return;
        }
        if (StringUtil.isEmpty(total)) {
            AlertUtil.showError("114514", "因为疏忽大意，管理员在输入框上输入了不正确的内容，而系统提出的条件是", "给我输入总数啊（恼）");
            return;
        }
        if (StringUtil.isEmpty(remainder)) {
            AlertUtil.showError("错误", "错误", "图书剩余数量不能为空");
            return;
        }
        String sex = "";
        if (manJrb.isSelected()) {
            sex = "男";
        } else if (femaleJrb.isSelected()) {
            sex = "女";
        }
        BookType bookType = bookTypeJcb.getSelectionModel().getSelectedItem();
        int bookTypeId = bookType.getId();
        if (c_remainder > c_total) {
            AlertUtil.showError("错误", "错误", "图书剩余数量不能大于总数量");
            return;
        }
        Book book = new Book(Integer.parseInt(id), bookName, author, sex, Float.parseFloat(price), bookTypeId, bookDesc, total, remainder);
        Connection conn;
        conn = getConnection();
        try {
            int addNum = bookdao.update(conn, book);
            if (addNum > 0) {
                AlertUtil.showAlert("提示", "提示", "修改成功");
                resetValue();
            } else {
                AlertUtil.showError("错误", "错误", "修改失败");
            }
            showTableData();
            operatePanel.setDisable(true);
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
    void bookDel(ActionEvent event) {
        bookDelActionPerformed(event);
    }

    private void bookDelActionPerformed(ActionEvent event) {
        String id = idTxt.getText();
        if (StringUtil.isEmpty(id)) {
            AlertUtil.showError("错误", "错误", "请选择一条记录");
            return;
        }
        int result = AlertUtil.showConfirm("二次确认", "删除确认", "确定要删除吗？");
        if (result == 1) {
            Connection conn;
            conn = getConnection();
            try {
                int delNum = bookdao.delete(conn, id);
                if (delNum > 0) {
                    AlertUtil.showAlert("提示", "提示", "删除成功");
                    resetValue();
                } else {
                    AlertUtil.showError("错误", "错误", "删除失败");
                }
                showTableData();
                operatePanel.setDisable(true);
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
    }


    public ObservableList<BookType> getBookTypeList(String type) {
        ObservableList<BookType> bookTypeList = FXCollections.observableArrayList();
        BookType bookType;
        try {
            Connection conn = getConnection();
            ResultSet rs = booktypedao.list(conn, new BookType());
            if ("search".equals(type)) {
                bookType = new BookType();
                bookType.setBookTypeName("请选择...");
                bookType.setId(-1);
                bookTypeList.add(bookType);
            }
            while (rs.next()) {
                bookType = new BookType();
                bookType.setBookTypeName(rs.getString("bookTypeName"));
                bookType.setId(rs.getInt("id"));
                if ("search".equals(type)) {
                    bookTypeList.add(bookType);
                }
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
        return bookTypeList;
    }

    public ObservableList<BookType> getBookTypeList2(String type) {
        ObservableList<BookType> bookTypeList = FXCollections.observableArrayList();
        BookType bookType;
        try {
            conn = getConnection();
            ResultSet rs = booktypedao.list(conn, new BookType());
            while (rs.next()) {
                bookType = new BookType();
                bookType.setBookTypeName(rs.getString("bookTypeName"));
                bookType.setId(rs.getInt("id"));
                if ("modify".equals(type)) {
                    bookTypeList.add(bookType);
                }
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
        return bookTypeList;
    }

    public void showData(String type) {
        list = getBookTypeList(type);
        s_bookTypeJcb.setItems(list);
        s_bookTypeJcb.getSelectionModel().select(0);
    }

    public void showData2(String type) {
        list2 = getBookTypeList2(type);
        bookTypeJcb.setItems(list2);
        bookTypeJcb.getSelectionModel().select(0);
    }

    public Connection getConnection() {
        try {
            conn = dbutil.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    public ObservableList<Tableview> getTableviewList(Book book) {
        ObservableList<Tableview> TableviewList = FXCollections.observableArrayList();
        conn = getConnection();
        try {
            ResultSet rs = bookdao.list(conn, book);
            Tableview tableview;
            while (rs.next()) {
                tableview = new Tableview(rs.getString("id"), rs.getString("bookName"), rs.getString("author"), rs.getString("sex"), rs.getFloat("price"), rs.getString("bookDesc"), rs.getString("bookTypeName"), rs.getString("total"), rs.getString("remainder"));
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

    public void showTableData() {
        Book book = new Book();
        ObservableList<Tableview> list = getTableviewList(book);
        No.setCellValueFactory(new PropertyValueFactory<>("id"));
        BookName.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        Author.setCellValueFactory(new PropertyValueFactory<>("author"));
        Sex.setCellValueFactory(new PropertyValueFactory<>("sex"));
        Price.setCellValueFactory(new PropertyValueFactory<>("price"));
        BookDesc.setCellValueFactory(new PropertyValueFactory<>("bookDesc"));
        BookType.setCellValueFactory(new PropertyValueFactory<>("bookTypeName"));
        Total.setCellValueFactory(new PropertyValueFactory<>("total"));
        Remainder.setCellValueFactory(new PropertyValueFactory<>("remainder"));
        bookTable.setItems(list);
    }

    public void showTableData(Book book) {
        ObservableList<Tableview> list = getTableviewList(book);
        No.setCellValueFactory(new PropertyValueFactory<>("id"));
        BookName.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        Author.setCellValueFactory(new PropertyValueFactory<>("author"));
        Sex.setCellValueFactory(new PropertyValueFactory<>("sex"));
        Price.setCellValueFactory(new PropertyValueFactory<>("price"));
        BookDesc.setCellValueFactory(new PropertyValueFactory<>("bookDesc"));
        BookType.setCellValueFactory(new PropertyValueFactory<>("bookTypeName"));
        Total.setCellValueFactory(new PropertyValueFactory<>("total"));
        Remainder.setCellValueFactory(new PropertyValueFactory<>("remainder"));
        bookTable.setItems(list);
    }

    private void resetValue() {
        idTxt.setText("");
        bookNameTxt.setText("");
        authorTxt.setText("");
        priceTxt.setText("");
        bookDescTxt.setText("");
        bookTypeJcb.getSelectionModel().select(0);
        totalTxt.setText("");
        remainderTxt.setText("");
        manJrb.setSelected(true);
    }

    @FXML
    void deleteText(RXActionEvent event) {
        RXTextField tf = (RXTextField) event.getSource();
        tf.clear();
    }
}
