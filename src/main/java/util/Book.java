package util;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    private Integer id;
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

    public Book(Integer id, String bookName, String author, Integer bookTypeId) {
        super();
        this.id = id;
        this.bookName = bookName;
        this.author = author;
        this.bookTypeId = bookTypeId;
    }


    public Book(Integer id, String bookName, String author, String sex, float price, Integer bookTypeId, String bookDesc, String total, String remainder) {
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

    public Book(String b_ID, Integer id, String bookName) {
        this.b_ID = b_ID;
        this.id = id;
        this.bookName = bookName;
    }
}