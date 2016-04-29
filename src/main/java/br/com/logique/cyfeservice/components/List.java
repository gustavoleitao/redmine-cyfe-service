package br.com.logique.cyfeservice.components;

/**
 * Created by Yuri on 25/04/2016.
 */
public class List implements CyfeComponent{

    private float value[];

    private List(float value[]) {
        this.value = value;
    }

    public static List of(float value[]) {
        return new List(value);
    }

    @Override
    public String response() {
        return "Sales Rep" + "," + "Revenue($)" + "\n" + "BR-AlarmExpert" + "," + value[0] + "\n" + "DataIntegrator" + "," +
                value[1] + "\n" + "SparkFramework" + "," + value[2] + "\n" + "VRaptor" + "," + value[3] + "\n"
                + "Other" + "," + value[4] + "\n" + "Color,#ff7f00";
    }
}
