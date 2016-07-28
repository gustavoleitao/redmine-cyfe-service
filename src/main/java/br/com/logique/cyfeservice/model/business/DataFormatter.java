package br.com.logique.cyfeservice.model.business;

import br.com.logique.cyfeservice.model.domain.ResponseData;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.ArrayList;
import java.util.List;

/**
 * Formats the data received from Redmine.
 *
 * Created by Yuri on 25/05/2016.
 */
public class DataFormatter {

    public static final String END_LINE = "\n";
    public static final String SEPARATOR = ",";
    private List<ResponseData> dataList;

    private List<String> header;

    private String comparison;

    private DataFormatter(List<ResponseData> dataList, List<String> header, String comparison) {
        this.dataList = dataList;
        this.header = header;
        this.comparison = comparison;
    }

    public List<String> getHeader() {
        return header;
    }

    public List<ResponseData> getDataList() {
        return dataList;
    }

    public String getComparison() {
        return comparison;
    }

    /**
     * Formats header for the Cyfe widget response.
     * @return String with the formatted header.
     */
    public String responseHeader() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < header.size(); i++) {
            builder.append(header.get(i));
            if (i < header.size() - 1) {
                builder.append(SEPARATOR);
            }
        }
        return builder.append(END_LINE).toString();

    }

    /**
     * Formats data received from Redmine for the Cyfe widget response.
     * @return String with the formatted data.
     */
    public String responseDataValues() {
        StringBuilder builder = new StringBuilder();
        dataList.forEach(data -> builder.append(responseDataValue(data)));
        return builder.toString();
    }

    private String responseDataValue(ResponseData responseData) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(responseData.getString()).append(",");
        List<Double> values = responseData.getValues();
        for (int i = 0; i < values.size(); i++) {
            stringBuilder.append(values.get(i));
        }
        return stringBuilder.append(END_LINE).toString();
    }

    /**
     * Formats data received from Redmine for the Cyfe widget response.
     * @return String with the formatted data.
     */
    public String responseDataStrings() {
        StringBuilder stringBuilder = new StringBuilder();
        dataList.forEach(data -> stringBuilder.append(responseDataString(data)));
        return stringBuilder.toString();
    }

    public String responseDataString(ResponseData responseData) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(responseData.getString()).append(",");
        List<String> strings = responseData.getStrings();
        for (int i = 0; i < strings.size(); i++) {
            stringBuilder.append(strings.get(i));
        }
        return stringBuilder.append(END_LINE).toString();
    }

    public String responseDataComparison() {
        return comparison;
    }

    /**
     * Take the most recent value from a list of dataList to be displayed on the Cyfe widget.
     * @return Most recent value from a list of dataList.
     */
    public String responseMostRecentValue() {
        if (dataList.size() >= 1) {
            ResponseData mostRecentResponseData = dataList.get(dataList.size() - 1);
            List<Double> mostRecentValueList = mostRecentResponseData.getValues();
            Double mostRecentValue = mostRecentValueList.get(mostRecentValueList.size() - 1);
            return DecimalUtil.roundToDouble((mostRecentValue), 2).toString();
        } else {
            return "0";
        }
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("dataList", dataList)
                .append("header", header)
                .toString();
    }


    public static class Builder {

        private List<ResponseData> dataList = new ArrayList<>();

        private List<String> header;

        private String comparison;

        public Builder(){}

        public Builder(List<ResponseData> dataList, List<String> header, String comparison){
            this.header = header;
            this.dataList = dataList;
            this.comparison = comparison;
        }

        private Builder withHeader(List<String> header) {
            this.header = header;
            return this;
        }

        public Builder addDataValue(ResponseData value) {
            dataList.add(value);
            return this;
        }

        public DataFormatter build() {
            return new DataFormatter(dataList, header, comparison);
        }

    }
}
