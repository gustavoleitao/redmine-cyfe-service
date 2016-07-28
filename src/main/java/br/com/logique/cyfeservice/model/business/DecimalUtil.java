package br.com.logique.cyfeservice.model.business;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by Yuri on 21/07/2016.
 */
public class DecimalUtil {

    /**
     * Receives a double and round its value to a chosen number of decimal places.
     * @param value
     * @param places
     * @return Double rounded value.
     */
    public static Double roundToDouble(double value, int places){
        if(places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bigDecimal = new BigDecimal(value);
        bigDecimal = bigDecimal.setScale(places, RoundingMode.HALF_DOWN);
        return bigDecimal.doubleValue();
    }

    /**
     * Receives a double, round its value and removes the fractional part.
     * @param value
     * @param places
     * @return Integer from double rounded value.
     */
    public static Integer roundToInteger(double value, int places){
        if(places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bigDecimal = new BigDecimal(value);
        bigDecimal = bigDecimal.setScale(places, RoundingMode.HALF_DOWN);
        return bigDecimal.intValue();
    }
}
