package br.com.logique.cyfeservice.components;

/**
 * Created by Yuri on 22/04/2016.
 */
public class Funnel implements CyfeComponent{

    private float value[];

    private Funnel(float value[]) {
        this.value = value;
    }

    public static Funnel of(float value[]) {
        return new Funnel(value);
    }

    @Override
    public String response() {
        return "Type,Count" + "\n" + "BR-AlarmExpert" + "," + value[0] + "\n" + "DataIntegrator" + "," + value[1]
                + "\n" + "SparkFramework" + "," + value[2] + "\n" + "VRaptor" + "," + value[3] + "\n"
                + "Other" + "," + value[4];
    }
}
