package util;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tableview {
    private String id;
    private String BookTypeName;
    private String BookTypeDesc;
    private String BookName;
    private String author;
    private String sex;
    private Float price;
    private String BookDesc;
    private String total;
    private String remainder;
    private String uid;
    private String Start_Time;
    private String End_Time;
    private String Appointment_Time;
    private String isReturned;
    private String Book_ID;


    public Tableview(String id, String bookName, String author, String sex, Float price, String bookDesc, String bookTypeName, String total, String remainder) {
        this.id = id;
        this.BookName = bookName;
        this.author = author;
        this.sex = sex;
        this.price = price;
        this.BookDesc = bookDesc;
        this.BookTypeName = bookTypeName;
        this.total = total;
        this.remainder = remainder;
    }

    public Tableview(String id, String bookTypeName, String bookTypeDesc) {
        this.id = id;
        this.BookTypeName = bookTypeName;
        this.BookTypeDesc = bookTypeDesc;
    }

    public Tableview(String ID, String book_ID, String bookName, String start_Time, String end_Time, String appointment_Time, String isReturned) {
        this.uid = ID;
        this.Book_ID = book_ID;
        this.BookName = bookName;
        this.Start_Time = start_Time;
        this.End_Time = end_Time;
        this.Appointment_Time = appointment_Time;
        this.isReturned = isReturned;
    }
}
