package com.example.mybook.biz;

import com.example.mybook.bean.Book;
import com.example.mybook.bean.Type;
import com.example.mybook.dao.BookDao;
import com.example.mybook.dao.TypeDao;

import java.sql.SQLException;
import java.util.List;

public class TypeBiz {
    TypeDao typeDao = new TypeDao();

    public List<Type> getAll() {
        try {
            return typeDao.getAll();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int add(String name, long parentId) {
        int result = 0;
        try {
            result = typeDao.add(name, parentId);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
        return result;
    }

    public int modify(long id, String name, long parentId) {
        int result = 0;
        try {
            result = typeDao.modify(id, name, parentId);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
        return result;
    }

    public int remove(long id) throws Exception {
        int result = 0;
        BookDao bookDao = new BookDao();
        try {
            List<Book> books = bookDao.getBooksByTypeId(id);
            if (books.size() > 0) {
                throw new Exception("删除的类型有子信息，删除失败");
            }
            result = typeDao.remove(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Type getById(long id) {
        try {
            return typeDao.getById(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
