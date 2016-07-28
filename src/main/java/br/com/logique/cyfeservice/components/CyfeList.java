package br.com.logique.cyfeservice.components;

import br.com.logique.cyfeservice.model.business.DataFormatter;

/**
 * Create the response in the widget format.
 *
 * Created by Yuri on 25/04/2016.
 */
public class CyfeList implements CyfeComponent{

    private DataFormatter dataFormat;

    private CyfeList(DataFormatter dataFormat) {
        this.dataFormat = dataFormat;
    }

    public static CyfeList fromCyfeList(DataFormatter dataFormat) {
        return new CyfeList(dataFormat);
    }

    @Override
    public String response() {
        StringBuilder builder = new StringBuilder();
        builder.append(dataFormat.responseHeader())
                .append(dataFormat.responseDataValues())
                .append("\n").append("Color,#00bfff");
        return builder.toString();
    }
}
