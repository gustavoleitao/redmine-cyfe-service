package br.com.logique.cyfeservice.components;

/**
 * Created by Yuri on 25/04/2016.
 */
public class CyfeList implements CyfeComponent{

    private DataFormat dataFormat;

    private CyfeList(DataFormat dataFormat) {
        this.dataFormat = dataFormat;
    }

    public static CyfeList fromCyfeList(DataFormat dataFormat) {
        return new CyfeList(dataFormat);
    }

    @Override
    public String response() {
        StringBuilder builder = new StringBuilder();
        builder.append(dataFormat.responseHeader())
                .append(dataFormat.responseDataValues())
                .append("\n").append("Color,#00ff7f");
        return builder.toString();
    }
}
