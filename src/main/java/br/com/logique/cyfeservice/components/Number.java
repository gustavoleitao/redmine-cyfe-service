package br.com.logique.cyfeservice.components;

/**
 * Created by Yuri on 22/04/2016.
 */
public class Number implements CyfeComponent {

    private double value;
    private String title;


    private Number(double value, String title) {
        this.value = value;
        this.title = title;
    }

    public static Number of(double value, String title) {
        return new Number(value, title);
    }

    public static Number of(int intValue, String title) {
        return new Number(intValue, title);
    }

    @Override
    public String response() {
        return title + "\n" + value;
    }
}
