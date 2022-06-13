package com.library.library;

import com.jfoenix.controls.JFXComboBox;
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
import util.Book;
import util.BookType;
import util.Dbutil;
import util.StringUtil;

import java.sql.Connection;
import java.sql.ResultSet;


public class BookAddInterFrm {
    @FXML
    private Button add;
    private Dbutil dbutil = new Dbutil();
    private BookTypeDao booktypedao = new BookTypeDao();
    private BookDao bookdao = new BookDao();
    private Book book=new Book();
    Connection conn = null;

    @FXML
    private TextField authorTxt;

    @FXML
    private TextArea bookDescTxt;

    @FXML
    private TextField bookNameTxt;

    @FXML
    private JFXComboBox<BookType> bookTypeJcb;

    @FXML
    private JFXRadioButton femaleJrb;

    @FXML
    private JFXRadioButton manJrb;

    @FXML
    private TextField priceTxt;

    @FXML
    private Button reset;

    @FXML
    private ToggleGroup sex;
    @FXML
    void initialize(){
        showData();
    }

    @FXML
    void BookAdd(ActionEvent event) {
        bookAddActionPerformed(event);
    }

    private void bookAddActionPerformed(ActionEvent evt) {
       String bookName =this.bookNameTxt.getText();
       String author = this.authorTxt.getText();
       String price=this.priceTxt.getText();
       String bookDesc = this.bookDescTxt.getText();
       if(StringUtil.isEmpty(bookName))
       {
           Alert alert = new Alert(Alert.AlertType.ERROR);
              alert.setTitle("错误");
                alert.setHeaderText("错误");
                alert.setContentText("请输入书名");
                alert.showAndWait();
                return;
       }
       if(StringUtil.isEmpty(author))
       {
           Alert alert = new Alert(Alert.AlertType.ERROR);
              alert.setTitle("错误");
                alert.setHeaderText("错误");
                alert.setContentText("请输入作者");
                alert.showAndWait();
                return;
       }
       if(StringUtil.isEmpty(price))
       {
           Alert alert = new Alert(Alert.AlertType.ERROR);
              alert.setTitle("错误");
                alert.setHeaderText("错误");
                alert.setContentText("请输入价格");
                alert.showAndWait();
                return;
       }
       String sex="";
       if(manJrb.isSelected())
       {
           sex="男";
       }
       if(femaleJrb.isSelected())
       {
           sex="女";
       }
       BookType bookType=bookTypeJcb.getSelectionModel().getSelectedItem();
       int bookTypeId= bookType.getId();
       Book book=new Book(bookName,author,sex,Float.parseFloat(price),bookTypeId,bookDesc);
       Connection conn=null;
       try {
           conn=getConnection();
           int addNum=bookdao.add(conn,book);
           if(addNum>0)
           {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("提示");
                alert.setHeaderText("提示");
                alert.setContentText("添加成功");
                alert.showAndWait();
                resetValue();
           }
           else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                 alert.setTitle("错误");
                 alert.setHeaderText("错误");
                 alert.setContentText("添加失败");
                 alert.showAndWait();
           }
       }catch(Exception e){
           e.printStackTrace();
       }finally{
           try {
               dbutil.close(conn);
           } catch (Exception e) {
               e.printStackTrace();
           }
       }
    }

    @FXML
    void resetValue(ActionEvent event) {
        resetValueActionPerformed(event);
    }

    private void resetValueActionPerformed(ActionEvent event) {
        resetValue();
    }


    private void resetValue() {
        this.bookNameTxt.setText("");
        this.authorTxt.setText("");
        this.priceTxt.setText("");
        this.bookDescTxt.setText("");
        this.manJrb.setSelected(true);
        showData();
    }


    public Connection getConnection() {
        try {
            conn = dbutil.getConnection();
            return conn;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public ObservableList<BookType> getBookTypeList() {
        ObservableList<BookType> bookTypeList = FXCollections.observableArrayList();
        BookType bookType = null;
        try {
            Connection conn = getConnection();
            ResultSet rs = booktypedao.list(conn, new BookType());
            while (rs.next()) {
                bookType = new BookType();
                bookType.setId(rs.getInt("id"));
                bookType.setBookTypeName(rs.getString("bookTypeName"));
                bookTypeList.add(bookType);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bookTypeList;
    }
    public void showData() {
        ObservableList<BookType> list =getBookTypeList();
        bookTypeJcb.setItems(list);
        bookTypeJcb.getSelectionModel().selectFirst();
    }

    void deleteText(RXActionEvent event) {
        RXTextField tf = (RXTextField) event.getSource();
        tf.clear();
    }

}
