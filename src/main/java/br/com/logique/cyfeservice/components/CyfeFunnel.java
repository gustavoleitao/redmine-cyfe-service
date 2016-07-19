package br.com.logique.cyfeservice.components;

/**
 * Create the response in the widget format.
 *
 * Created by Yuri on 22/04/2016.
 */
public class CyfeFunnel implements CyfeComponent{

    private DataFormatter dataFormat;

    public CyfeFunnel(DataFormatter dataFormat) {
        this.dataFormat = dataFormat;
    }

    public static CyfeFunnel fromDataFormat(DataFormatter dataFormat) {
        return new CyfeFunnel(dataFormat);
    }

    @Override
    public String response() {
        return null;
    }
}
