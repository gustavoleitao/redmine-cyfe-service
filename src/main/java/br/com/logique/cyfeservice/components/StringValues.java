package br.com.logique.cyfeservice.components;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Yuri on 25/05/2016.
 */
public class StringValues {

    private String string;

    private List<Double> values;

    public static StringValues fromStringValue(String date, Double... value) {
        return new StringValues(date, value);
    }

    private StringValues(String string, Double... values) {
        this.string = string;
        this.values = Arrays.asList(values);
    }

    public String getString() {
        return string;
    }

    public List<Double> getValues() {
        return values;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("Date", string)
                .append("values", values)
                .toString();
    }
}
