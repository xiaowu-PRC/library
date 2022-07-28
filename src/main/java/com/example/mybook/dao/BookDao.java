package com.example.mybook.dao;

import com.example.mybook.bean.Book;
import com.example.mybook.util.DBHelper;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class BookDao {
    public static void main(String[] args) {
        List<Book> books = null;
        try {
            books = new BookDao().getBooksByTypeId(14);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(books.size());
    }

    public List<Book> getBooksByTypeId(long typeId) throws SQLException {
        QueryRunner runner = new QueryRunner();
        Connection conn = DBHelper.getConnection();
        String sql = "SELECT * FROM book WHERE typeId =?";
        List<Book> books = runner.query(conn, sql, new BeanListHandler<Book>(Book.class), typeId);
        conn.close();
        return books;
    }
}
