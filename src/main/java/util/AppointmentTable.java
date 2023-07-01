package util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
}