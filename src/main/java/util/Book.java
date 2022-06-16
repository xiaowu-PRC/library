package util;

public class Book {
    private int id;
    private String bookName;
    private String author;
    private float price;
    private String sex;
    private Integer bookTypeId;
    private String bookTypeName;
    private String bookDesc;
    private String total;
    private String remainder;
    private String b_ID;

    public Book() {
        super();
    }

    public Book(String bookName, String author, String sex, float price, Integer bookTypeId, String bookDesc) {
        super();
        this.bookName = bookName;
        this.author = author;
        this.sex = sex;
        this.price = price;
        this.bookTypeId = bookTypeId;
        this.bookDesc = bookDesc;
    }


    public Book(String bookName, String author, Integer bookTypeId) {
        super();
        this.bookName = bookName;
        this.author = author;
        this.bookTypeId = bookTypeId;
    }

    public Book(int id, String bookName, String author, Integer bookTypeId) {
        super();
        this.id = id;
        this.bookName = bookName;
        this.author = author;
        this.bookTypeId = bookTypeId;
    }

    public Book(int id, String bookName, String author, String sex, float price, Integer bookTypeId, String bookDesc) {
        super();
        this.id = id;
        this.bookName = bookName;
        this.author = author;
        this.sex = sex;
        this.price = price;
        this.bookTypeId = bookTypeId;
        this.bookDesc = bookDesc;
    }

    public Book(String bookName, String author, String sex, float price, Integer bookTypeId, String bookDesc, String total, String remainder) {
        super();
        this.bookName = bookName;
        this.author = author;
        this.sex = sex;
        this.price = price;
        this.bookTypeId = bookTypeId;
        this.bookDesc = bookDesc;
        this.total = total;
        this.remainder = remainder;
    }


    public Book(int id, String bookName, String author, String sex, float price, Integer bookTypeId, String bookDesc, String total, String remainder) {
        super();
        this.id = id;
        this.bookName = bookName;
        this.author = author;
        this.sex = sex;
        this.price = price;
        this.bookTypeId = bookTypeId;
        this.bookDesc = bookDesc;
        this.total = total;
        this.remainder = remainder;
    }

    public Book(String b_ID, int id, String bookName) {
        this.b_ID = b_ID;
        this.id = id;
        this.bookName = bookName;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getRemainder() {
        return remainder;
    }

    public void setRemainder(String remainder) {
        this.remainder = remainder;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getBookTypeId() {
        return bookTypeId;
    }

    public void setBookTypeId(Integer bookTypeId) {
        this.bookTypeId = bookTypeId;
    }

    public String getBookTypeName() {
        return bookTypeName;
    }

    public void setBookTypeName(String bookTypeName) {
        this.bookTypeName = bookTypeName;
    }

    public String getBookDesc() {
        return bookDesc;
    }

    public void setBookDesc(String bookDesc) {
        this.bookDesc = bookDesc;
    }

    public String getB_ID() {
        return b_ID;
    }

    public void setB_ID(String b_ID) {
        this.b_ID = b_ID;
    }
}
