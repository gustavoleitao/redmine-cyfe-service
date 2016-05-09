package br.com.logique.cyfeservice.service;

import br.com.logique.cyfeservice.util.DateUtil;
import com.taskadapter.redmineapi.IssueManager;
import com.taskadapter.redmineapi.RedmineException;
import com.taskadapter.redmineapi.RedmineManager;
import com.taskadapter.redmineapi.RedmineManagerFactory;
import com.taskadapter.redmineapi.bean.Issue;
import com.taskadapter.redmineapi.bean.IssueStatus;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Gustavo on 05/05/2016.
 */
public class RedmineService {

    private String uri;
    private String apiAccessKey;

    private RedmineService(String uri, String apiAccessKey) {
        this.uri = uri;
        this.apiAccessKey = apiAccessKey;
    }

    private Set<Integer> getCloseableStatus() throws RedmineException {
        RedmineManager mgr = RedmineManagerFactory.createWithApiKey(uri, apiAccessKey);
        IssueManager issueManager = mgr.getIssueManager();
        List<IssueStatus> allStatus = issueManager.getStatuses();
        Set<Integer> closeableStatus = allStatus.stream()
                .filter(status -> status.isClosed())
                .map(s -> s.getId()).collect(Collectors.toSet());

        return closeableStatus;
    }

    private Iterator<Issue> getClosedIssuesByProject(Integer idProject, Date startDate) throws RedmineException {
        RedmineManager mgr = RedmineManagerFactory.createWithApiKey(uri, apiAccessKey);
        IssueManager issueManager = mgr.getIssueManager();
        Map<String, String> paramters = new HashMap<>();
        paramters.put("project_id", String.valueOf(idProject));
        paramters.put("status_id", "closed");
        if (startDate != null){
            paramters.put("created_on", ">=" + DateUtil.toRedmineFormat(startDate));
        }
        return new IteratorIssues.Builder().withParameters(paramters).build(issueManager);
    }

    private Iterator<Issue> getOpenedIssuesByProject(Integer idProject) throws RedmineException {
        RedmineManager mgr = RedmineManagerFactory.createWithApiKey(uri, apiAccessKey);
        IssueManager issueManager = mgr.getIssueManager();
        Map<String, String> paramters = new HashMap<>();
        paramters.put("project_id", String.valueOf(idProject));
        paramters.put("status_id", "open");
        return new IteratorIssues.Builder().withParameters(paramters).build(issueManager);
    }

    public int openedIssuesByProjectId(Integer idProject) throws RedmineException {
        int sum = 0;
        Iterator<Issue> iterator = getOpenedIssuesByProject(idProject);
        while (iterator.hasNext()) {
            iterator.next();
            sum++;
        }
        return sum;
    }

    public double durationAvgClosedIssueByProject(Integer idProject) throws RedmineException {
        Iterator<Issue> iterator = getClosedIssuesByProject(idProject, null);
        int qnt = 0;
        double accDuration = 0;
        while (iterator.hasNext()) {
            Issue issue = iterator.next();
            accDuration += DateUtil.diffHour(issue.getCreatedOn(), issue.getUpdatedOn());
            qnt++;
        }
        double avg = 0;
        if (qnt > 0) {
            avg = accDuration / qnt;
        }
        return avg;
    }

    public double durationAvgClosedIssueByProject(Integer idProject, Date startDate) throws RedmineException {
        Iterator<Issue> iterator = getClosedIssuesByProject(idProject, startDate);
        int qnt = 0;
        double accDuration = 0;
        while (iterator.hasNext()) {
            Issue issue = iterator.next();
            accDuration += DateUtil.diffHour(issue.getCreatedOn(), issue.getUpdatedOn());
            qnt++;
        }
        double avg = 0;
        if (qnt > 0) {
            avg = accDuration / qnt;
        }
        return avg;
    }

    public static class BuilderRedmineService {

        private String uri;
        private String apiAccessKey;

        public BuilderRedmineService withURI(String uri) {
            this.uri = uri;
            return this;
        }

        public BuilderRedmineService withApiAccessKey(String apiAccessKey) {
            this.apiAccessKey = apiAccessKey;
            return this;
        }

        public RedmineService build() {
            return new RedmineService(uri, apiAccessKey);
        }

    }


}
