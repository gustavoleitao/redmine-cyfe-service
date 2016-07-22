package br.com.logique.cyfeservice.util;

import br.com.logique.cyfeservice.model.business.DecimalUtil;

import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.Date;

/**
 * Created by Gustavo on 05/05/2016.
 */
public class DateUtil {

    /**
     * Subtracts the start from the end of a time interval.
     * @param startDate
     * @param endDate
     * @return Value representing the time from the start to the end of the interval.
     */
    public static double diffHour(Date startDate, Date endDate){
        long diff = endDate.getTime() - startDate.getTime();
        double baseTime = 1000*3600;
        return diff/baseTime;
    }

    /**
     * Takes a date and formats it for the widget response.
     * @param date
     * @return Formatted date.
     */
    public static String toRedmineFormat(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(date);
    }

    /**
     * Turns minutes to hours.
     * @param timeInMins
     * @return Hours.
     */
    public static Double minToHourMin(Double timeInMins){
        Double hours = DecimalUtil.roundToDouble(timeInMins / 60, 2);
//        Integer minutes;
//        if (timeInMins > 60) {
//            minutes = DecimalUtil.roundToInteger(timeInMins % 60, 2);
//        } else {
//            minutes = DecimalUtil.roundToInteger(timeInMins, 2);
//        }
        return hours;
    }
}
