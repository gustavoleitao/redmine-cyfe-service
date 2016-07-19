package br.com.logique.cyfeservice.components;

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

    private String string1;
    private String string2;
    private List<Double> values;

    public static ResponseData fromResponseData(String date, Double... value) {
        return new ResponseData(date, value);
    }

    public static ResponseData fromResponseData(String string1, String string2) {
        return new ResponseData(string1, string2);
    }

    private ResponseData(String date, Double... values) {
        this.string1 = date;
        this.values = Arrays.asList(values);
    }

    private ResponseData(String string1, String string2) {
        this.string1 = string1;
        this.string2 = string2;
    }

    public String getString1() {
        return string1;
    }

    public String getString2() {
        return string2;
    }

    public List<Double> getValues() {
        return values;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("Date", string1)
                .append("values", values)
                .toString();
    }
}
