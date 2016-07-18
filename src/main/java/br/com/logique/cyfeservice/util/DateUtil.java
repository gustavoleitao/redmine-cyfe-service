package br.com.logique.cyfeservice.util;

import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.Date;

/**
 * Created by Gustavo on 05/05/2016.
 */
public class DateUtil {

    public static double diffHour(Date startDate, Date endDate){
        long diff = endDate.getTime() - startDate.getTime();
        double baseTime = 1000*3600;
        return diff/baseTime;
    }

    public static String toRedmineFormat(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(date);
    }

    public static String toCyfeFormat(YearMonth dateTime) {
        String monthValue = String.valueOf(dateTime.getMonthValue());
        if (monthValue.length() < 2) {
            return String.valueOf(dateTime.getYear()) + "0" + dateTime.getMonthValue();
        } else {
            return String.valueOf(dateTime.getYear()) + dateTime.getMonthValue();
        }
    }
}
