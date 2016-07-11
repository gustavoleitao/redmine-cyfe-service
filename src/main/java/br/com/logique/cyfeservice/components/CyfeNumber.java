package br.com.logique.cyfeservice.components;

/**
 * Created by Yuri on 22/04/2016.
 */
public class CyfeNumber implements CyfeComponent {

    private double value;
    private String title;


    private CyfeNumber(double value, String title) {
        this.value = value;
        this.title = title;
    }

    public static CyfeNumber fromData(double value, String title) {
        return new CyfeNumber(value, title);
    }

    public static CyfeNumber fromData(int intValue, String title) {
        return new CyfeNumber(intValue, title);
    }

    @Override
    public String response() {
        return title + "\n" + (int) value + "\n" + "Color,#cb6318";
    }
}
