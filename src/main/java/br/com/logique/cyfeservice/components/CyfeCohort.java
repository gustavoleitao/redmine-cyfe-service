package br.com.logique.cyfeservice.components;

/**
 * Created by Yuri on 02/05/2016.
 */
public class CyfeCohort implements CyfeComponent{

    private DataFormat dataFormat;

    public CyfeCohort(DataFormat dataFormat) {
        this.dataFormat = dataFormat;
    }

    public static CyfeCohort fromDataFormat(DataFormat dataFormat) {
        return new CyfeCohort(dataFormat);
    }

    @Override
    public String response() {
        return null;
    }
}
