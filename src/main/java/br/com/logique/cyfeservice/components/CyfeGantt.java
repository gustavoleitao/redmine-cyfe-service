package br.com.logique.cyfeservice.components;

import br.com.logique.cyfeservice.model.business.DataFormatter;

/**
 * Create the response in the widget format.
 *
 * Created by Yuri on 03/05/2016.
 */
public class CyfeGantt implements CyfeComponent {

    private DataFormatter dataFormat;

    public CyfeGantt(DataFormatter dataFormat) {
            this.dataFormat = dataFormat;
        }

    public static CyfeGantt fromDataFormat(DataFormatter dataFormat) {
        return new CyfeGantt(dataFormat);
    }

    @Override
    public String response() {
        return null;
    }
}
