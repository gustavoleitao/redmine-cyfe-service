package br.com.logique.cyfeservice.components;

/**
 * Created by Yuri on 25/04/2016.
 */
public class Table implements CyfeComponent{

    private float value1[], value2[];

    private Table(float value1[], float value2[]) {
        this.value1 = value1;
        this.value2 = value2;
    }

    public static Table of(float value1[], float value2[]) {
        return new Table(value1, value2);
    }

    @Override
    public String response() {
        return "Sales Rep," + "Revenue($)," + "Sales" + "\n" + "BR-AlarmExpert," + value1[0] + "," +
                value2[0] + "\n" + "DataIntegrator," + value1[1] + "," + value2[1] + "\n" + "SparkFramework,"
                + value1[2] + "," + value2[2] + "\n" + "VRaptor," + value1[3] + "," + value2[3] + "\n" + "Other,"
                + value1[4] + "," + value2[4];
    }
}
