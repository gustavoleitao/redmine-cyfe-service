package br.com.logique.cyfeservice.components;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Yuri on 23/06/2016.
 */

public class PreviousPeriodComparison {

    public static String from(Map<String, Double> currentPeriod) {
        Double current, previous;
        String comparison;
        List<Double> currentPeriodList = new ArrayList<>(currentPeriod.values());
        DecimalFormatSymbols separator = new DecimalFormatSymbols(Locale.ENGLISH);
        separator.setGroupingSeparator('.');
        DecimalFormat decimalFormat = new DecimalFormat("##.##", separator);
        decimalFormat.setRoundingMode(RoundingMode.DOWN);
        if (currentPeriodList.size() > 1) {
            current = currentPeriodList.get(currentPeriodList.size() - 1);
            previous = currentPeriodList.get(currentPeriodList.size() - 2);
            if (current < previous) {
                comparison = decimalFormat.format((1 - (current / previous)) * -100);
            } else {
                comparison = decimalFormat.format((1 - (previous / current)) * 100);
            }
            return comparison;
        } else {
            return "0";
        }
    }
}
