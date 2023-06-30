package util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookType {
    private int id;
    private String bookTypeName;
    private String bookTypeDesc;

    public BookType(String bookTypeName) {
        this.bookTypeName = bookTypeName;
    }

    public BookType(String BookTypeName, String bookTypeDesc) {
        super();
        this.bookTypeName = BookTypeName;
        this.bookTypeDesc = bookTypeDesc;
    }

    @Override
    public String toString() {
        return bookTypeName;
    }
}
