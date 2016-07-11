package br.com.logique.cyfeservice.components;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Yuri on 25/05/2016.
 */
public class DataFormat {

    public static final String END_LINE = "\n";
    public static final String SEPARATOR = ",";
    private List<StringValues> values;

    private List<String> header;

    private String comparison;

    private DataFormat(List<StringValues> values, List<String> header, String comparison) {
        this.values = values;
        this.header = header;
        this.comparison = comparison;
    }

    public List<String> getHeader() {
        return header;
    }

    public List<StringValues> getValues() {
        return values;
    }

    public String getComparison() {
        return comparison;
    }

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

    public String responseDataValues() {
        StringBuilder builder = new StringBuilder();
        values.forEach(dataValue -> builder.append(responseDataValue(dataValue)));
        return builder.toString();
    }

    private String responseDataValue(StringValues stringValue) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(stringValue.getString()).append(",");
        List<Double> dataValues = stringValue.getValues();
        for (int i = 0; i < dataValues.size(); i++) {
            stringBuilder.append(dataValues.get(i));
        }
        return stringBuilder.append(END_LINE).toString();
    }

    public String responseDataComparison() {
        return comparison;
    }

    public String responseMostRecentValue() {
        if (values.size() >= 1) {
            StringValues mostRecentStringValues = values.get(values.size() - 1);
            List<Double> mostRecentValueList = mostRecentStringValues.getValues();
            Double mostRecentValue = mostRecentValueList.get(mostRecentValueList.size() - 1);
            DecimalFormatSymbols separator = new DecimalFormatSymbols(Locale.ENGLISH);
            separator.setGroupingSeparator('.');
            DecimalFormat decimalFormat = new DecimalFormat("##.##", separator);
            decimalFormat.setRoundingMode(RoundingMode.DOWN);
            return decimalFormat.format(mostRecentValue);
        } else {
            return "0";
        }
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("values", values)
                .append("header", header)
                .toString();
    }


    public static class Builder {

        private List<StringValues> values = new ArrayList<>();

        private List<String> header;

        private String comparison;

        public Builder(){}

        public Builder(List<StringValues> values, List<String> header, String comparison){
            this.header = header;
            this.values = values;
            this.comparison = comparison;
        }

        private Builder withHeader(List<String> header) {
            this.header = header;
            return this;
        }

        public Builder addDataValue(StringValues value) {
            values.add(value);
            return this;
        }

        public DataFormat build() {
            return new DataFormat(values, header, comparison);
        }

    }
}
