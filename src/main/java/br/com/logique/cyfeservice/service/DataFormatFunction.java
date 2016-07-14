package br.com.logique.cyfeservice.service;

import br.com.logique.cyfeservice.components.DataFormat;
import br.com.logique.cyfeservice.components.StringValues;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Yuri on 02/06/2016.
 */
public class DataFormatFunction {

    /**
     * Takes the data necessary for the widget response and puts in a single object.
     * @param keyValueMap
     * @param header
     * @param comparison
     * @return DataFormat object with all the data for the response.
     */
    public DataFormat apply(Map<String, Double> keyValueMap, List<String> header, String comparison) {
        List<StringValues> stringValuesList = new ArrayList<>();
        for (Object object : keyValueMap.entrySet()) {
            Map.Entry<String, Double> pair = (Map.Entry<String, Double>) object;
            StringValues stringValues = StringValues.fromStringValue(pair.getKey(), pair.getValue());
            stringValuesList.add(stringValues);
        }
        DataFormat.Builder builder = new DataFormat.Builder(stringValuesList, header, comparison);
        return builder.build();
    }

}