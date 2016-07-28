package br.com.logique.cyfeservice.components;

import br.com.logique.cyfeservice.model.business.DataFormatter;

/**
 * Create the response in the widget format.
 *
 * Created by Yuri on 20/04/2016.
 */
public class CyfePie implements CyfeComponent{

    private DataFormatter dataFormat;

    private CyfePie(DataFormatter dataFormat) {
        this.dataFormat = dataFormat;
    }

    public static CyfePie fromDataFormat(DataFormatter dataFormat) {
        return new CyfePie(dataFormat);
    }

    @Override
    public String response() {
        return null;
    }
}
