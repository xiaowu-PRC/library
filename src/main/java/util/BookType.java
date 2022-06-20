package util;

public class BookType {
    private int id;
    private String bookTypeName;
    private String bookTypeDesc;

    public BookType() {
        super();
    }

    public BookType(String bookTypeName) {
        this.bookTypeName = bookTypeName;
    }

    public BookType(String BookTypeName, String bookTypeDesc) {
        super();
        this.bookTypeName = BookTypeName;
        this.bookTypeDesc = bookTypeDesc;
    }

    public BookType(int id, String BookTypeName, String bookTypeDesc) {
        super();
        this.id = id;
        this.bookTypeName = BookTypeName;
        this.bookTypeDesc = bookTypeDesc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBookTypeName() {
        return bookTypeName;
    }

    public void setBookTypeName(String bookTypeName) {
        this.bookTypeName = bookTypeName;
    }

    public String getBookTypeDesc() {
        return bookTypeDesc;
    }

    public void setBookTypeDesc(String bookTypeDesc) {
        this.bookTypeDesc = bookTypeDesc;
    }

    @Override
    public String toString() {
        return bookTypeName;
    }
}
