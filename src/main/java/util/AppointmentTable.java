package util;

public class AppointmentTable {
    private Integer ID;
    private Integer User_ID;
    private String userName;
    private Integer Book_ID;
    private String BookTypeName;
    private String BookName;
    private String Start_Time;
    private String End_Time;
    private Integer Extendable;
    private Integer isReturned;
    private String Return_Time;

    public AppointmentTable(Integer ID, Integer user_ID, String userName, Integer book_ID, String bookTypeName, String bookName, String start_Time, String end_Time, Integer extendable, Integer isReturned, String return_Time) {
        this.ID = ID;
        User_ID = user_ID;
        this.userName = userName;
        Book_ID = book_ID;
        BookTypeName = bookTypeName;
        BookName = bookName;
        Start_Time = start_Time;
        End_Time = end_Time;
        Extendable = extendable;
        this.isReturned = isReturned;
        Return_Time = return_Time;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Integer getUser_ID() {
        return User_ID;
    }

    public void setUser_ID(Integer user_ID) {
        User_ID = user_ID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getBook_ID() {
        return Book_ID;
    }

    public void setBook_ID(Integer book_ID) {
        Book_ID = book_ID;
    }

    public String getBookTypeName() {
        return BookTypeName;
    }

    public void setBookTypeName(String bookTypeName) {
        BookTypeName = bookTypeName;
    }

    public String getBookName() {
        return BookName;
    }

    public void setBookName(String bookName) {
        BookName = bookName;
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

    public Integer getExtendable() {
        return Extendable;
    }

    public void setExtendable(Integer extendable) {
        Extendable = extendable;
    }

    public Integer getIsReturned() {
        return isReturned;
    }

    public void setIsReturned(Integer isReturned) {
        this.isReturned = isReturned;
    }

    public String getReturn_Time() {
        return Return_Time;
    }

    public void setReturn_Time(String return_Time) {
        Return_Time = return_Time;
    }
}