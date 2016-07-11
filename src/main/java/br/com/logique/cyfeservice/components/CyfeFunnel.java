package br.com.logique.cyfeservice.components;

/**
 * Created by Yuri on 22/04/2016.
 */
public class CyfeFunnel implements CyfeComponent{

    private DataFormat dataFormat;

    public CyfeFunnel(DataFormat dataFormat) {
        this.dataFormat = dataFormat;
    }

    public static CyfeFunnel fromDataFormat(DataFormat dataFormat) {
        return new CyfeFunnel(dataFormat);
    }

    @Override
    public String response() {
        return null;
    }
}
