package br.com.logique.cyfeservice.model.business;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Yuri on 23/06/2016.
 */

public class PreviousPeriodComparison {

    /**
     * Compares the current period data with the previous one.
     * @param periodDataMap
     * @return Percentage comparison between the current and previous period.
     */
    public static String from(Map<String, Double> periodDataMap) {
        Double current, previous;
        String comparison;
        List<Double> currentPeriodList = new ArrayList<>(periodDataMap.values());
        if (currentPeriodList.size() > 1) {
            current = currentPeriodList.get(currentPeriodList.size() - 1);
            previous = currentPeriodList.get(currentPeriodList.size() - 2);
            if (current < previous) {
                comparison = DecimalUtil.roundToDouble(((1 - (current / previous)) * -100), 2).toString();
            } else {
                comparison = DecimalUtil.roundToDouble(((1 - (previous / current)) * 100), 2).toString();
            }
            return comparison;
        } else {
            return "0";
        }
    }
}
