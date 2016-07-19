package br.com.logique.cyfeservice.components;

/**
 * Create the response in the widget format.
 *
 * Created by Yuri on 03/05/2016.
 */
public class CyfeChart implements CyfeComponent {

    private DataFormatter dataFormat;

    public CyfeChart(DataFormatter dataFormat) {
        this.dataFormat = dataFormat;
    }

    public static CyfeChart fromDataFormat(DataFormatter dataFormat) {
        return new CyfeChart(dataFormat);
    }

    @Override
    public String response() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(dataFormat.responseHeader());
        stringBuilder.append(dataFormat.responseDataValues());
        stringBuilder.append("Color,#00ff7f").append("\n");
        stringBuilder.append("Type,column").append("\n");
        stringBuilder.append("LabelShow,1").append("\n");
        stringBuilder.append("Total,").append(dataFormat.responseMostRecentValue());
        return stringBuilder.toString();
    }
}
