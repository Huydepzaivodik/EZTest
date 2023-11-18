/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author admin
 */
//
public class DateTimeHelper {

    //Cong ngay java util Date
    public static Date addDays(Date day, int i) {
        Calendar c = Calendar.getInstance();
        c.setTime(day);
        c.add(Calendar.DATE, i);
        return c.getTime();
    }

    //chuyen tu java util sang java sql Timestamp
    public static java.sql.Timestamp toTimestampSql(Date day) {
        return new Timestamp(day.getTime());
    }

    //Chuyen tu java Timestamp sang java util Date
    public static java.util.Date toDateUtil(java.sql.Timestamp day) {
        return new java.util.Date(day.getTime());
    }

    //lay danh sach tu ngay a den ngay B duoi dang java sql Timestamp
    public static ArrayList<java.sql.Timestamp> getDatesList(java.sql.Timestamp from, java.sql.Timestamp to) {
        ArrayList<java.sql.Timestamp> dates = new ArrayList<>();
        int days = 0;
        Date e_from = toDateUtil(from);
        Date e_to = toDateUtil(to);
        while (true) {
            Date d = addDays(e_from, days);
            dates.add(toTimestampSql(d));
            days++;
            if (d.compareTo(e_to) >= 0) {
                break;
            }
        }
        return dates;
    }

    //lay thu ngay trong tuan bat dau tu Sunday
    public static String getDayNameofWeek(java.sql.Timestamp s) {
        Date d = toDateUtil(s);
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        switch (dayOfWeek) {
            case 1:
                return "Sunday";
            case 2:
                return "Monday";
            case 3:
                return "Tuesday";
            case 4:
                return "Wednesday";
            case 5:
                return "Thursday";
            case 6:
                return "Friday";
            case 7:
                return "Saturday";
        }
        return "Error";
    }

    //So sanh 2 ngay java sql Timestamp
    public static int compare(java.sql.Timestamp a, java.sql.Timestamp b) {

        LocalDate dateA = a.toLocalDateTime().toLocalDate();
        LocalDate dateB = b.toLocalDateTime().toLocalDate();

        return dateA.compareTo(dateB);
    }
    
    //So sanh 2 ngay java sql Timestamp gom ca ngay gio phut giay miligiay
    public static int compare1(java.sql.Timestamp a, java.sql.Timestamp b) {
        return a.compareTo(b);
    }

    public static void main(String[] args) {

        Timestamp timestamp1 = Timestamp.valueOf("2023-06-25 09:00:00.0");
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

// In timestamp hiện tại
        System.out.println(currentTimestamp);
        System.out.println(DateTimeHelper.compare1(timestamp1, currentTimestamp));

    }
}
