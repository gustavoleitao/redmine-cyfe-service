package br.com.logique.cyfeservice.components;

import br.com.logique.cyfeservice.model.business.DataFormatter;

/**
 * Create the response in the widget format.
 *
 * Created by Yuri on 25/04/2016.
 */
public class CyfeTable implements CyfeComponent{

    private DataFormatter dataFormat;

    public CyfeTable(DataFormatter dataFormat) {
       this.dataFormat = dataFormat;
   }

    public static CyfeTable fromCyfeTable(DataFormatter dataFormat) {
        return new CyfeTable(dataFormat);
    }

    @Override
    public String response() {
        StringBuilder builder = new StringBuilder();
        builder.append(dataFormat.responseHeader())
                .append(dataFormat.responseDataStrings());
        return builder.toString();
    }
}
