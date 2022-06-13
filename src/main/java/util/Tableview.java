package util;

public class Tableview {
    String id;
    String BookTypeName;
    String BookTypeDesc;
    String BookName;
    String author;
    String sex;
    Float price;
    String BookDesc;

    public String getBookName() {
        return BookName;
    }

    public void setBookName(String bookName) {
        BookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getBookDesc() {
        return BookDesc;
    }

    public void setBookDesc(String bookDesc) {
        BookDesc = bookDesc;
    }


    public Tableview(String id, String bookName, String author, String sex, Float price, String bookDesc, String bookTypeName) {
        this.id = id;
        BookName = bookName;
        this.author = author;
        this.sex = sex;
        this.price = price;
        BookDesc = bookDesc;
        BookTypeName = bookTypeName;
    }

    public Tableview(String id, String bookTypeName, String bookTypeDesc) {
        this.id = id;
        this.BookTypeName = bookTypeName;
        this.BookTypeDesc = bookTypeDesc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBookTypeName() {
        return BookTypeName;
    }

    public void setBookTypeName(String bookTypeName) {
        BookTypeName = bookTypeName;
    }

    public String getBookTypeDesc() {
        return BookTypeDesc;
    }

    public void setBookTypeDesc(String bookTypeDesc) {
        BookTypeDesc = bookTypeDesc;
    }
}
