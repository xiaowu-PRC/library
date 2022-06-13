package com.library.library;

import com.jfoenix.controls.JFXRadioButton;
import com.leewyatt.rxcontrols.controls.RXTextField;
import com.leewyatt.rxcontrols.event.RXActionEvent;
import dao.BookDao;
import dao.BookTypeDao;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import util.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Optional;

public class BookManageInterFrm {
    @FXML
    private TableColumn<Tableview, String> Author;
    private BookType bookType=new BookType();
    Connection conn=null;
    String query = null;
    Dbutil dbutil=new Dbutil();
    private BookTypeDao booktypedao=new BookTypeDao();
    private BookDao bookdao=new BookDao();
    public ObservableList<BookType> list;
    public ObservableList<BookType> list2;

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
    private Button update;

    @FXML
    private Button del;



    @FXML
    void initialize() {
        showData("search");
        showTableData();
        bookTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tableview>() {
            @Override
            public void changed(ObservableValue<? extends Tableview> observableValue, Tableview tableview, Tableview t1) {
                showData2("modify");
                if (t1 != null) {
                    idTxt.setText(t1.getId());
                    bookNameTxt.setText(t1.getBookName());
                    authorTxt.setText(t1.getAuthor());
                    String sex=t1.getSex();
                    if("男".equals(sex))
                    {
                        manJrb.setSelected(true);
                    }else if("女".equals(sex))
                    {
                        femaleJrb.setSelected(true);
                    }
                    priceTxt.setText((Float)t1.getPrice()+"");
                    bookDescTxt.setText(t1.getBookDesc());
                    String bookTypeName=t1.getBookTypeName();
                    bookTypeJcb.setItems(list2);
                    int n=bookTypeJcb.getItems().size();
                    for(int i=0;i<n;i++)
                    {
                        BookType item=bookTypeJcb.getItems().get(i);
                        if(bookTypeJcb.getItems().get(i).getBookTypeName().equals(bookTypeName))
                        {
                            bookTypeJcb.getSelectionModel().select(i);
                            break;
                        }
                    }
                }
            }
        });
    }

    @FXML
    void search(ActionEvent event) {
        bookSearchActionPerformed(event);
    }

    private void bookSearchActionPerformed(ActionEvent event) {
        String bookName=this.s_bookNameTxt.getText();
        String author=this.s_authorTxt.getText();
        BookType bookType=this.s_bookTypeJcb.getSelectionModel().getSelectedItem();
        int bookTypeId=bookType.getId();
        Book book=new Book(bookName, author, bookTypeId);
        showTableData(book);
    }

    @FXML
    void bookUpdate(ActionEvent event) {
        bookUpdateActionPerformed(event);
    }

    private void bookUpdateActionPerformed(ActionEvent event) {
        String id=idTxt.getText();
        String bookName=bookNameTxt.getText();
        String author=authorTxt.getText();
        String price=priceTxt.getText();
        String bookDesc=bookDescTxt.getText();
        if(StringUtil.isEmpty(id))
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("错误");
            alert.setHeaderText("错误");
            alert.setContentText("请选择一条记录");
            alert.showAndWait();
        }
        if(StringUtil.isEmpty(bookName))
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("错误");
            alert.setHeaderText("错误");
            alert.setContentText("请输入书名");
            alert.showAndWait();
        }
        if(StringUtil.isEmpty(author))
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("错误");
            alert.setHeaderText("错误");
            alert.setContentText("请输入作者");
            alert.showAndWait();
        }
        if(StringUtil.isEmpty(price))
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("错误");
            alert.setHeaderText("错误");
            alert.setContentText("请输入价格");
            alert.showAndWait();
        }
        String sex="";
        if(manJrb.isSelected())
        {
            sex="男";
        }else if(femaleJrb.isSelected())
        {
            sex="女";
        }
        BookType bookType=bookTypeJcb.getSelectionModel().getSelectedItem();
        int bookTypeId= bookType.getId();
        Book book=new Book(Integer.parseInt(id),bookName,author,sex,Float.parseFloat(price),bookTypeId,bookDesc);
        Connection conn=null;
        conn=getConnection();
        try {
        int addNum= bookdao.update(conn,book);
        if(addNum>0)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("提示");
            alert.setHeaderText("提示");
            alert.setContentText("修改成功");
            alert.showAndWait();
            resetValue();
            showTableData();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("错误");
            alert.setHeaderText("错误");
            alert.setContentText("修改失败");
            alert.showAndWait();
            showTableData();
        }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
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
        String id=idTxt.getText();
        if(StringUtil.isEmpty(id))
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("错误");
            alert.setHeaderText("错误");
            alert.setContentText("请选择一条记录");
            alert.showAndWait();
            return;
        }
        Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
        alert2.setContentText("确定要删除吗？");
        Optional<ButtonType> result = alert2.showAndWait();
        if (result.get() == ButtonType.OK) {
            Connection conn=null;
            conn=getConnection();
            try {
                int delNum=bookdao.delete(conn,id);
                if(delNum>0) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("提示");
                    alert.setHeaderText("提示");
                    alert.setContentText("删除成功");
                    alert.showAndWait();
                    resetValue();
                    showTableData();
                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("错误");
                    alert.setHeaderText("错误");
                    alert.setContentText("删除失败");
                    alert.showAndWait();
                    showTableData();
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public ObservableList<BookType> getBookTypeList(String type) {
        ObservableList<BookType> bookTypeList = FXCollections.observableArrayList();
        BookType bookType = null;
        try {
            Connection conn = getConnection();
            ResultSet rs = booktypedao.list(conn, new BookType());
            if("search".equals(type))
            {
                bookType=new BookType();
                bookType.setBookTypeName("请选择...");
                bookType.setId(-1);
                bookTypeList.add(bookType);
            }
            while (rs.next()) {
                bookType = new BookType();
                bookType.setBookTypeName(rs.getString("bookTypeName"));
                bookType.setId(rs.getInt("id"));
                if("search".equals(type))
                {
                    bookTypeList.add(bookType);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bookTypeList;
    }

    public ObservableList<BookType> getBookTypeList2(String type) {
        ObservableList<BookType> bookTypeList = FXCollections.observableArrayList();
        BookType bookType = null;
        try {
            Connection conn = getConnection();
            ResultSet rs = booktypedao.list(conn, new BookType());
            while (rs.next()) {
                bookType = new BookType();
                bookType.setBookTypeName(rs.getString("bookTypeName"));
                bookType.setId(rs.getInt("id"));
               if("modify".equals(type)){
                    bookTypeList.add(bookType);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bookTypeList;
    }
    public void showData(String type) {
        list =getBookTypeList(type);
        s_bookTypeJcb.setItems(list);
        s_bookTypeJcb.getSelectionModel().select(0);
    }

    public void showData2(String type)
    {
        list2=getBookTypeList2(type);
        bookTypeJcb.setItems(list2);
        bookTypeJcb.getSelectionModel().select(0);
    }

    public Connection getConnection() {
        try {
            conn = dbutil.getConnection();
            return conn;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ObservableList<Tableview> getTableviewList(Book book) {
            ObservableList<Tableview> TableviewList = FXCollections.observableArrayList();
            conn = getConnection();
            try {
            ResultSet rs = bookdao.list(conn, book);
            Tableview tableview;
            while (rs.next()) {
                tableview = new Tableview(rs.getString("id"), rs.getString("bookName"), rs.getString("author"),rs.getString("sex"),rs.getFloat("price"),rs.getString("bookDesc"),rs.getString("bookTypeName"));
                TableviewList.add(tableview);
            }
            } catch (Exception e) {
                throw new RuntimeException(e);
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
        bookTable.setItems(list);
    }

    private void resetValue()
    {
        idTxt.setText("");
        bookNameTxt.setText("");
        authorTxt.setText("");
        priceTxt.setText("");
        bookDescTxt.setText("");
        bookTypeJcb.getSelectionModel().select(0);
        manJrb.setSelected(true);
    }
    void deleteText(RXActionEvent event) {
        RXTextField tf = (RXTextField) event.getSource();
        tf.clear();
    }
}
