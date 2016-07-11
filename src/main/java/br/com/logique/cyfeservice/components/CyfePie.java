package br.com.logique.cyfeservice.components;

/**
 * Created by Yuri on 20/04/2016.
 */
public class CyfePie implements CyfeComponent{

    private DataFormat dataFormat;

    private CyfePie(DataFormat dataFormat) {
        this.dataFormat = dataFormat;
    }

    public static CyfePie fromDataFormat(DataFormat dataFormat) {
        return new CyfePie(dataFormat);
    }

    @Override
    public String response() {
        return null;
    }
}
