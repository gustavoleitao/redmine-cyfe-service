package br.com.logique.cyfeservice.components;

/**
 * Created by Yuri on 03/05/2016.
 */
public class CyfeGantt implements CyfeComponent {

    private DataFormat dataFormat;

    public CyfeGantt(DataFormat dataFormat) {
            this.dataFormat = dataFormat;
        }

    public static CyfeGantt fromDataFormat(DataFormat dataFormat) {
        return new CyfeGantt(dataFormat);
    }

    @Override
    public String response() {
        return null;
    }
}
