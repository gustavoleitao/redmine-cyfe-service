package br.com.logique.cyfeservice.model.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Arrays;
import java.util.List;

/**
 * Stores all the data necessary for the response.
 *
 * Created by Yuri on 25/05/2016.
 */
public class ResponseData {

    private String string;
    private List<Double> values;
    private List<String> strings;

    public static ResponseData fromResponseData(String date, Double... value) {
        return new ResponseData(date, value);
    }

    public static ResponseData fromResponseData(String date, String... strings) {
        return new ResponseData(date, strings);
    }

    private ResponseData(String date, Double... values) {
        this.string = date;
        this.values = Arrays.asList(values);
    }

    private ResponseData(String date, String... strings) {
        this.string = date;
        this.strings = Arrays.asList(strings);
    }

    public String getString() {
        return string;
    }

    public List<Double> getValues() {
        return values;
    }

    public List<String> getStrings() {
        return strings;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("Date", string)
                .append("values", values)
                .toString();
    }
}
