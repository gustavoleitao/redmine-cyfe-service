package br.com.logique.cyfeservice.components;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yuri on 09/06/2016.
 */
public class TableData {

    public static final String END_LINE = "\n";
    public static final String SEPARATOR = ",";

    private List<String> header;
    private List<String> issuesDescription;

    private TableData(List<String> header, List<String> issuesDescription) {
        this.header = header;
        this.issuesDescription = issuesDescription;
    }

    public String responseHeader() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < header.size(); i++) {
            builder.append(header.get(i));
            if (i < header.size() - 1) {
                builder.append(SEPARATOR);
            }
        }
        return builder.append(END_LINE).toString();
    }

    public String responseIssuesIds() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < issuesDescription.size(); i++) {
            builder.append(issuesDescription.get(i));
            if (i < issuesDescription.size() - 1) {
                builder.append(END_LINE);
            }
        }
        return builder.append(END_LINE).toString();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("header", header)
                .append("issuesDescription", issuesDescription)
                .toString();
    }

    public static class Builder {

        private List<String> header;
        private List<String> issuesDescription = new ArrayList<>();

        public Builder(){}

        public Builder(List<String> header, List<String> issuesDescription) {
            this.header = header;
            this.issuesDescription = issuesDescription;
        }

        public TableData build() {
            return new TableData(header, issuesDescription);
        }
    }
}

