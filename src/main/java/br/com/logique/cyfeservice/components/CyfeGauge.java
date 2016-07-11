package br.com.logique.cyfeservice.components;

/**
 * Created by Yuri on 20/04/2016.
 */
public class CyfeGauge implements CyfeComponent {

    private DataFormat dataFormat;

    public CyfeGauge(DataFormat dataFormat) {
        this.dataFormat = dataFormat;
    }

    public static CyfeGauge fromDataFormat(DataFormat dataFormat) {
        return new CyfeGauge(dataFormat);
    }

    @Override
    public String response() {
        return null;
    }
}