package br.com.logique.cyfeservice.service;

import br.com.logique.cyfeservice.model.domain.StatusIssue;
import br.com.logique.cyfeservice.util.DateUtil;
import com.taskadapter.redmineapi.IssueManager;
import com.taskadapter.redmineapi.RedmineException;
import com.taskadapter.redmineapi.RedmineManager;
import com.taskadapter.redmineapi.RedmineManagerFactory;
import com.taskadapter.redmineapi.bean.Issue;
import com.taskadapter.redmineapi.bean.IssueStatus;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
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

    private Iterator<Issue> getClosedIssuesByProject(Long xMonthsAgo, Integer idProject) throws RedmineException {
        LocalDateTime localDate = LocalDateTime.now().minusMonths(xMonthsAgo);
        Date startDate = Date.from(localDate.atZone(ZoneId.systemDefault()).toInstant());
        RedmineManager mgr = RedmineManagerFactory.createWithApiKey(uri, apiAccessKey);
        IssueManager issueManager = mgr.getIssueManager();
        Map<String, String> paramters = new HashMap<>();
        paramters.put("project_id", String.valueOf(idProject));
        paramters.put("status_id", "closed");
        if (startDate != null) {
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

    public List<String> issuesInExecutionByProjectId(Integer idProject) throws RedmineException {
        RedmineManager mgr = RedmineManagerFactory.createWithApiKey(uri, apiAccessKey);
        IssueManager issueManager = mgr.getIssueManager();
        Map<String, String> parameters = new HashMap<>();
        parameters.put("project_id", String.valueOf(idProject));
        parameters.put("status_id", StatusIssue.IN_EXECUTION.getIdStr());
        Iterator<Issue> iterator = new IteratorIssues.Builder().withParameters(parameters).build(issueManager);
        List<String> issuesInExecutionByProject = new ArrayList<>();
        while (iterator.hasNext()) {
            Issue issue = iterator.next();
            issuesInExecutionByProject.add(issue.getSubject());
        }
        return issuesInExecutionByProject;
    }

//    public Map<YearMonth, Double> closedIssuesByMonthInLastXMonths(Long xMonthsAgo, Integer projectId) throws RedmineException {
//        Iterator<Issue> iterator = getClosedIssues(xMonthsAgo, projectId);
//        Map<YearMonth, Double> closedIssuesByMonthMap = new TreeMap<>();
//        Double closedIssuesInMonth;
//        while (iterator.hasNext()) {
//            Issue issue = iterator.next();
//            LocalDateTime issueDateTime = LocalDateTime.ofInstant(issue.getUpdatedOn().toInstant(), ZoneId.systemDefault());
//            YearMonth yearMonth = YearMonth.from(issueDateTime);
//            if (closedIssuesByMonthMap.get(yearMonth) == null) {
//                closedIssuesByMonthMap.put(yearMonth, 0D);
//            }
//            closedIssuesInMonth = closedIssuesByMonthMap.get(yearMonth);
//            closedIssuesInMonth++;
//            closedIssuesByMonthMap.put(yearMonth, closedIssuesInMonth);
//        }
//        return closedIssuesByMonthMap;
//    }

    public Map<String, Double> closedIssuesInTimeInterval(Long xDaysAgo, Integer projectId) throws RedmineException {
        double closedIssues;
        Map<String, Double> closedIssuesMap = new TreeMap<>();
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Iterator<Issue> iterator = getClosedIssuesInTimeInterval(xDaysAgo, projectId);
        while (iterator.hasNext()) {
            Issue issue = iterator.next();
            if (issue.getParentId() == null) {
                String issueClosedDate = dateFormat.format(issue.getClosedOn());
                if (closedIssuesMap.get(issueClosedDate) == null) {
                    closedIssuesMap.put(issueClosedDate, 0D);
                }
                closedIssues = closedIssuesMap.get(issueClosedDate);
                closedIssues++;
                closedIssuesMap.put(issueClosedDate, closedIssues);
            }
        }
        return closedIssuesMap;
    }

    /**
     * Return total spent hours
     * @param issuesId issues id
     * @return spent hour
     * @throws RedmineException
     */
    public Float getSpentHour(Integer... issuesId) throws RedmineException {
        float result = 0;
        for (Integer issueId : issuesId) {
            result += getSpentHour(issueId);
        }
        return result;
    }

    private Float getSpentHour(Integer issueId) throws RedmineException {
        RedmineManager mgr = RedmineManagerFactory.createWithApiKey(uri, apiAccessKey);
        IssueManager issueManager = mgr.getIssueManager();
        Issue issue = issueManager.getIssueById(issueId);
        return issue.getSpentHours();
    }

    private Iterator<Issue> getClosedIssues(Long xMonthsAgo, Integer projectId) {
        LocalDateTime localDate = LocalDateTime.now().minusMonths(xMonthsAgo);
        Date startDate = Date.from(localDate.atZone(ZoneId.systemDefault()).toInstant());
        RedmineManager mgr = RedmineManagerFactory.createWithApiKey(uri, apiAccessKey);
        IssueManager issueManager = mgr.getIssueManager();
        Map<String, String> parameters = new HashMap<>();
        parameters.put("project_id", String.valueOf(projectId));
        parameters.put("status_id", "closed");
        parameters.put("created_on", ">=" + DateUtil.toRedmineFormat(startDate));
        return new IteratorIssues.Builder().withParameters(parameters).build(issueManager);
    }

    private Iterator<Issue> getClosedIssuesInTimeInterval(Long xDaysAgo, Integer projectId) {
        LocalDateTime xDaysAgoLocalDate;
        Date startDate, endDate;
        xDaysAgoLocalDate = LocalDateTime.now().minusDays(xDaysAgo);
        startDate = Date.from(xDaysAgoLocalDate.atZone(ZoneId.systemDefault()).toInstant());
        endDate = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
        RedmineManager mgr = RedmineManagerFactory.createWithApiKey(uri, apiAccessKey);
        IssueManager issueManager = mgr.getIssueManager();
        Map<String, String> parameters = new HashMap<>();
        parameters.put("project_id", String.valueOf(projectId));
        parameters.put("status_id", "closed");
        parameters.put("closed_on", "><" + DateUtil.toRedmineFormat(startDate) + "|" + DateUtil.toRedmineFormat(endDate));
        return new IteratorIssues.Builder().withParameters(parameters).build(issueManager);
    }

    public Map<String, Double> workedHoursInTimeInterval(Long xDaysAgo, Integer projectId) throws RedmineException {
        double workedHours;
        Map<String, Double> workedHoursMap = new TreeMap<>();
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Iterator<Issue> iterator = getClosedIssuesInTimeInterval(xDaysAgo, projectId);
        while (iterator.hasNext()) {
            Issue issue = iterator.next();
            if (issue.getParentId() == null) {
                Date issueClosedDate = issue.getClosedOn();
                if (workedHoursMap.get(dateFormat.format(issueClosedDate)) == null) {
                    workedHoursMap.put(dateFormat.format(issueClosedDate), 0D);
                }
                workedHours = workedHoursMap.get(dateFormat.format(issueClosedDate));
                workedHours += getSpentHour(issue.getId());
                workedHoursMap.put(dateFormat.format(issueClosedDate), workedHours);
            }
        }
        return workedHoursMap;
    }

    public Map<String, Double> workedHoursByPerson(Long xDaysAgo, Integer projectId) throws RedmineException {
        double workedHours;
        Map<String, Double> workedHoursByPersonMap = new TreeMap<>();
        Iterator<Issue> iterator = getClosedIssuesInTimeInterval(xDaysAgo, projectId);
        while (iterator.hasNext()) {
            Issue issue = iterator.next();
            if (issue.getParentId() == null) {
                String assignee = issue.getAssignee().getFullName();
                if (workedHoursByPersonMap.get(assignee) == null) {
                    workedHoursByPersonMap.put(assignee, 0D);
                }
                workedHours = workedHoursByPersonMap.get(assignee);
                workedHours += getSpentHour(issue.getId());
                workedHoursByPersonMap.put(assignee, workedHours);
            }
        }
        return workedHoursByPersonMap;
    }

//    public Map<YearMonth, Double> workedHoursByMonthInLastXMonths(Long xMonthsAgo, Integer projectId) throws RedmineException {
//        Iterator<Issue> iterator = getClosedIssues(xMonthsAgo, projectId);
//        Map<YearMonth, Double> hoursWorkedByMonth = new TreeMap<>();
//        Double hoursWorkedInMonth;
//        while (iterator.hasNext()) {
//            Issue issue = iterator.next();
//            YearMonth yearMonth = YearMonth.from(LocalDateTime.ofInstant(issue.getUpdatedOn().toInstant(), ZoneId.systemDefault()));
//            if (hoursWorkedByMonth.get(yearMonth) == null) {
//                hoursWorkedByMonth.put(yearMonth, 0D);
//            }
//            hoursWorkedInMonth = hoursWorkedByMonth.get(yearMonth);
//            //hoursWorkedInMonth += DateUtil.diffHour(issue.getCreatedOn(), issue.getUpdatedOn());
//            hoursWorkedInMonth += getSpentHour(issue.getId());
//            hoursWorkedByMonth.put(yearMonth, hoursWorkedInMonth);
//        }
//        return hoursWorkedByMonth;
//    }

    public int openedIssuesByProjectId(Integer idProject) throws RedmineException {
        int sum = 0;
        Iterator<Issue> iterator = getOpenedIssuesByProject(idProject);
        while (iterator.hasNext()) {
            Issue issue = iterator.next();
            if (issue.getParentId() == null){
                sum++;
            }
        }
        return sum;
    }

    public int totalClosedIssuesByProjectIdInTimeInterval(Long xDaysAgo, Integer idProject) throws RedmineException {
        Iterator<Issue> iterator = getClosedIssuesInTimeInterval(xDaysAgo, idProject);
        int sum = 0;
        while (iterator.hasNext()) {
            Issue issue = iterator.next();
            if (issue.getParentId() == null) {
                sum++;
            }
        }
        return sum;
    }

    public int openedIssuesForMoreThanXHoursByProject(Integer idProject, double xHours) throws RedmineException {
        Iterator<Issue> iterator = getOpenedIssuesByProject(idProject);
        int sum = 0;
        while (iterator.hasNext()) {
            Issue issue = iterator.next();
            if (issue.getCreatedOn() != null && issue.getClosedOn() != null) {
                if (DateUtil.diffHour(issue.getCreatedOn(), issue.getClosedOn()) > xHours && issue.getParentId() == null)
                    sum++;
            }
        }
        return sum;
    }

    public double durationAvgClosedIssuesByProject(Long xDaysAgo, Integer idProject) throws RedmineException {
        Iterator<Issue> iterator = getClosedIssuesInTimeInterval(xDaysAgo, idProject);
        int qnt = 0;
        double accDuration = 0;
        while (iterator.hasNext()) {
            Issue issue = iterator.next();
            if (issue.getCreatedOn() != null && issue.getClosedOn() != null && issue.getParentId() == null) {
                accDuration += DateUtil.diffHour(issue.getCreatedOn(), issue.getClosedOn());
                qnt++;
            }
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
