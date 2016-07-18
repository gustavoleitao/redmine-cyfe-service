package br.com.logique.cyfeservice.components;

/**
 * Create the response in the widget format.
 *
 * Created by Yuri on 20/04/2016.
 */
public class CyfeGauge implements CyfeComponent {

    private DataFormatter dataFormat;

    public CyfeGauge(DataFormatter dataFormat) {
        this.dataFormat = dataFormat;
    }

    public static CyfeGauge fromDataFormat(DataFormatter dataFormat) {
        return new CyfeGauge(dataFormat);
    }

    @Override
    public String response() {
        return null;
    }
}