package br.com.logique.cyfeservice.service;

import br.com.logique.cyfeservice.components.DataFormatter;
import br.com.logique.cyfeservice.components.ResponseData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Unites the data received from Redmine.
 *
 * Created by Yuri on 02/06/2016.
 */
public class DataFormatFunction {

    /**
     * Takes the data necessary for the widget response and puts in a single object.
     * @param keyValueMap
     * @param header
     * @param comparison
     * @return DataFormatter object with all the data for the response.
     */
    public DataFormatter applyStringValueFormat(Map<String, Double> keyValueMap, List<String> header, String comparison) {
        List<ResponseData> responseDataList = new ArrayList<>();
        for (Object object : keyValueMap.entrySet()) {
            Map.Entry<String, Double> pair = (Map.Entry<String, Double>) object;
            ResponseData responseData = ResponseData.fromResponseData(pair.getKey(), pair.getValue());
            responseDataList.add(responseData);
        }
        DataFormatter.Builder builder = new DataFormatter.Builder(responseDataList, header, comparison);
        return builder.build();
    }

    /**
     * Takes the data necessary for the widget response and puts in a single object.
     * @param stringPairMap
     * @param header
     * @param comparison
     * @return DataFormatter object with all the data for the response.
     */
    public DataFormatter applyStringStringFormat(Map<String, String> stringPairMap, List<String> header, String comparison) {
        List<ResponseData> responseDataList = new ArrayList<>();
        for (Object object : stringPairMap.entrySet()) {
            Map.Entry<String, String> pair = (Map.Entry<String, String>) object;
            ResponseData responseData = ResponseData.fromResponseData(pair.getKey(), pair.getValue());
            responseDataList.add(responseData);
        }
        DataFormatter.Builder builder = new DataFormatter.Builder(responseDataList, header, comparison);
        return builder.build();
    }

}