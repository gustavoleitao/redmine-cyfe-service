package br.com.logique.cyfeservice.util;

import br.com.logique.cyfeservice.model.business.DecimalUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Gustavo on 05/05/2016.
 */
public class DateUtil {

    /**
     * Subtracts the start from the end of a time interval.
     * @param startDate
     * @param endDate
     * @return Value representing the time from the start to the end of the interval in hours.
     */
    public static double diffHour(Date startDate, Date endDate) {
        long diff = endDate.getTime() - startDate.getTime();
        double baseTime = 1000*3600;
        return diff/baseTime;
    }

    /**
     * Takes a date and formats it for the widget response.
     * @param date
     * @return Formatted date.
     */
    public static String toRedmineFormat(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(date);
    }

    /**
     * Converts minutes to hours.
     * @param timeInMins
     * @return Hours.
     */
    public static Double minToHour(Double timeInMins) {
        Double hours = DecimalUtil.roundToDouble(timeInMins / 60, 2);
        return hours;
    }

    /**
     * Converts minutes to hours and minutes in the format HH:MM
     * @param timeInMins
     * @return Time in hours and minutes
     */
    public static String minToHourMin(Double timeInMins) {
        Integer hours = DecimalUtil.roundToInteger(timeInMins / 60, 2);
        Integer minutes;
        if (timeInMins > 60) {
            minutes = DecimalUtil.roundToInteger(timeInMins % 60, 2);
        } else {
            minutes = DecimalUtil.roundToInteger(timeInMins, 2);
        }
        return hours + ":" + minutes;
    }

    /**
     * Converts hours to days and hours in the format DD:HH
     * @param timeInHours
     * @return Time in days and hours
     */
    public static String hoursToDays(Double timeInHours) {
        String stringDays;
        Integer hours;
        Integer intDays = DecimalUtil.roundToInteger(timeInHours / 24, 2);
        if (timeInHours > 24) {
            if (intDays < 10) {
                stringDays = "0" + intDays;
            } else {
                stringDays = intDays.toString();
            }
            hours = DecimalUtil.roundToInteger(timeInHours % 24, 2);
            if (hours < 10) {
                return stringDays + ":0" + hours;
            } else {
                return stringDays + ":" + hours;
            }
        } else {
            hours = DecimalUtil.roundToInteger(timeInHours, 2);
            if (hours < 10) {
                return "00:0" + hours;
            } else {
                return "00:" + hours;
            }
        }
    }
}
