package br.com.logique.cyfeservice.components;

/**
 * Created by Yuri on 25/04/2016.
 */
public class CyfeTable implements CyfeComponent{

    private TableData tableData;

    public CyfeTable(TableData tableData) {
       this.tableData = tableData;
   }

    public static CyfeTable fromCyfeTable(TableData tableData) {
        return new CyfeTable(tableData);
    }

    @Override
    public String response() {
        StringBuilder builder = new StringBuilder();
        builder.append(tableData.responseHeader())
                .append(tableData.responseIssuesIds());
        return builder.toString();
    }
}
