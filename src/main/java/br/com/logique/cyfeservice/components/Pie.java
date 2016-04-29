package br.com.logique.cyfeservice.components;

/**
 * Created by Yuri on 20/04/2016.
 */
public class Pie implements CyfeComponent{

    private float value[];

    private Pie(float value[]) {
        this.value = value;
    }

    public static Pie of(float value[]) {
        return new Pie(value);
    }

    @Override
    public String response() {
        return "Plan 1, Plan 2, Plan 3, Plan 4, Plan 5" + "\n" + value[0] + "," + value[1] + "," + value[2] + ","
                + value[3] + "," + value[4] + "," + "\n" + "Color," + "#FF0000" + "," + "#FFFF00" + ","
                + "#008000" + "," + "#0000FF" + "," + "#800080";
    }
}
