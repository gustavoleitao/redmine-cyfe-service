package br.com.logique.cyfeservice.service;

import br.com.logique.cyfeservice.components.TableData;

import java.util.List;
import java.util.function.BiFunction;

/**
 * Created by Yuri on 10/06/2016.
 */
public class TableDataFunction implements BiFunction<List<String>, List<String>, TableData> {

    /**
     * Takes the data necessary for the table widget response and puts in a single object.
     * @param issuesInExecutionList
     * @param header
     * @return TableData object with all the data for the response.
     */
    public TableData apply(List<String> issuesInExecutionList, List<String> header) {
        TableData.Builder builder = new TableData.Builder(header, issuesInExecutionList);
        return builder.build();
    }

}