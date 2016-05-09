package br.com.logique.cyfeservice.service;

import com.taskadapter.redmineapi.IssueManager;
import com.taskadapter.redmineapi.RedmineException;
import com.taskadapter.redmineapi.bean.Issue;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Gustavo on 06/05/2016.
 */
public class IteratorIssues implements Iterator<Issue> {

    private IssueManager issueManager;

    private Map<String, String> parameters;

    private List<Issue> nexts;

    private IteratorIssues(IssueManager issueManager, Map<String, String> parameters, int limit) {
        this.issueManager = issueManager;
        this.parameters = parameters;
        this.limit = limit;
    }

    private int index = 0;

    private int limit = 100;

    private int offset = 0;

    @Override
    public boolean hasNext() {
        if (nexts == null || index >= nexts.size()) {
            index = 0;
            try {
                parameters.put("limit", String.valueOf(limit));
                nexts = issueManager.getIssues(parameters);
                parameters.put("offset", String.valueOf(offset+=limit));
            } catch (RedmineException e) {
                e.printStackTrace();
            }
        }
        return nexts.size() > 0 && index < nexts.size();
    }

    @Override
    public Issue next() {
        return nexts.get(index++);
    }

    public static class Builder {

        private int limit = 100;

        private IssueManager issueManager;

        private Map<String, String> parameters = new HashMap<>();

        private String apiAccessKey;

        public Builder withLimit(int limit) {
            this.limit = limit;
            return this;
        }

        public Builder withParameters(Map<String, String> parameters){
            this.parameters = parameters;
            return this;
        }

        public IteratorIssues build(IssueManager issueManager) {
            return new IteratorIssues(issueManager, parameters, limit);
        }

    }

}
