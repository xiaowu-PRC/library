package util;

public class Tableview {
    String id;
    String BookTypeName;
    String BookTypeDesc;

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
