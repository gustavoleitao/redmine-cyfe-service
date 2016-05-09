package br.com.logique.cyfeservice.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by Gustavo on 05/05/2016.
 */
public class DateUtil {

    public static long diffHour(Date startDate, Date endDate){
        long diff = endDate.getTime() - startDate.getTime();
        return TimeUnit.MILLISECONDS.toHours(diff);
    }

    public static String toRedmineFormat(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(date);
    }

}
