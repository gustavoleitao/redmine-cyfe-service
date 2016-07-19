package br.com.logique.cyfeservice.components;

/**
 * Create the response in the widget format.
 *
 * Created by Yuri on 02/05/2016.
 */
public class CyfeCohort implements CyfeComponent{

    private DataFormatter dataFormat;

    public CyfeCohort(DataFormatter dataFormat) {
        this.dataFormat = dataFormat;
    }

    public static CyfeCohort fromDataFormat(DataFormatter dataFormat) {
        return new CyfeCohort(dataFormat);
    }

    @Override
    public String response() {
        return null;
    }
}
