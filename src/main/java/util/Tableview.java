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
    String total;
    String remainder;
    String ID;
    String Start_Time;
    String End_Time;
    String Appointment_Time;
    String isReturned;
    String Book_ID;

    public Tableview(String id, String bookName, String author, String sex, Float price, String bookDesc, String bookTypeName) {
        this.id = id;
        this.BookName = bookName;
        this.author = author;
        this.sex = sex;
        this.price = price;
        this.BookDesc = bookDesc;
        this.BookTypeName = bookTypeName;
    }

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
        this.ID = ID;
        this.Book_ID = book_ID;
        this. BookName = bookName;
        this. Start_Time = start_Time;
        this.End_Time = end_Time;
        this.Appointment_Time = appointment_Time;
        this.isReturned = isReturned;
    }

    public String getBook_ID() {
        return Book_ID;
    }

    public void setBook_ID(String book_ID) {
        Book_ID = book_ID;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getStart_Time() {
        return Start_Time;
    }

    public void setStart_Time(String start_Time) {
        Start_Time = start_Time;
    }

    public String getEnd_Time() {
        return End_Time;
    }

    public void setEnd_Time(String end_Time) {
        End_Time = end_Time;
    }

    public String getAppointment_Time() {
        return Appointment_Time;
    }

    public void setAppointment_Time(String appointment_Time) {
        Appointment_Time = appointment_Time;
    }

    public String getIsReturned() {
        return isReturned;
    }

    public void setIsReturned(String isReturned) {
        this.isReturned = isReturned;
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
