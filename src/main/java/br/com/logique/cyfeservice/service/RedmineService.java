package br.com.logique.cyfeservice.service;

import br.com.logique.cyfeservice.model.business.DecimalUtil;
import br.com.logique.cyfeservice.model.domain.StatusIssue;
import br.com.logique.cyfeservice.util.DateUtil;
import com.taskadapter.redmineapi.IssueManager;
import com.taskadapter.redmineapi.RedmineException;
import com.taskadapter.redmineapi.RedmineManager;
import com.taskadapter.redmineapi.RedmineManagerFactory;
import com.taskadapter.redmineapi.bean.Issue;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

/**
 * Manages the requisitions to Redmine.
 *
 * Created by Gustavo on 05/05/2016.
 */
public class RedmineService {

    private String uri;
    private String apiAccessKey;

    private RedmineService(String uri, String apiAccessKey) {
        this.uri = uri;
        this.apiAccessKey = apiAccessKey;
    }

    /**
     * Create an iterator for the opened issues of a single project
     * @param projectId
     * @return Iterator for the opened issues issues collection
     * @throws RedmineException
     */
    private Iterator<Issue> getOpenedIssuesByProject(Integer projectId) throws RedmineException {
        RedmineManager mgr = RedmineManagerFactory.createWithApiKey(uri, apiAccessKey);
        IssueManager issueManager = mgr.getIssueManager();
        Map<String, String> paramters = new HashMap<>();
        paramters.put("project_id", String.valueOf(projectId));
        paramters.put("status_id", "open");
        return new IteratorIssues.Builder().withParameters(paramters).build(issueManager);
    }

    /**
     * Create a map of all the issues in execution description of a single project
     * @param projectId
     * @return Map of issues in execution description
     * @throws RedmineException
     */
    public Map<String, String> issuesInExecutionByProjectId(Integer projectId) throws RedmineException {
        RedmineManager mgr = RedmineManagerFactory.createWithApiKey(uri, apiAccessKey);
        IssueManager issueManager = mgr.getIssueManager();
        Map<String, String> parameters = new HashMap<>();
        parameters.put("project_id", String.valueOf(projectId));
        parameters.put("status_id", StatusIssue.IN_EXECUTION.getIdStr());
        Iterator<Issue> iterator = new IteratorIssues.Builder().withParameters(parameters).build(issueManager);
        Map<String, String> issuesInExecutionByProjectMap = new TreeMap<>();
        while (iterator.hasNext()) {
            Issue issue = iterator.next();
            if (issue.getParentId() == null) {
                String issueId = issue.getId().toString();
                issuesInExecutionByProjectMap.putIfAbsent(issueId, "0");
                issuesInExecutionByProjectMap.put(issueId, issue.getSubject());
            }
        }
        return issuesInExecutionByProjectMap;
    }

    /**
     * Create a map of all the waiting issues description and waiting time of a single project
     * @param projectId
     * @return Map of issues description and waiting time
     * @throws RedmineException
     */
    public Map<String, String> waitingIssuesByProjectId(Integer projectId) throws RedmineException {
        RedmineManager mgr = RedmineManagerFactory.createWithApiKey(uri, apiAccessKey);
        IssueManager issueManager = mgr.getIssueManager();
        Map<String, String> parameters = new HashMap<>();
        parameters.put("project_id", String.valueOf(projectId));
        parameters.put("status_id", StatusIssue.WAITING.getIdStr());
        Iterator<Issue> iterator = new IteratorIssues.Builder().withParameters(parameters).build(issueManager);
        Map<String, String> waitingIssuesByProjectMap = new TreeMap<>();
        while (iterator.hasNext()) {
            Issue issue = iterator.next();
            if (issue.getParentId() == null) {
                String issueId = issue.getId().toString();
                String subjectAndTime = issue.getSubject() + "," + DateUtil.hoursToDays(DateUtil.diffHour(issue.getUpdatedOn(),
                        Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant())));
                waitingIssuesByProjectMap.putIfAbsent(issueId, "0");
                waitingIssuesByProjectMap.put(issueId, subjectAndTime);
            }
        }
        return waitingIssuesByProjectMap;
    }

    /**
     * Sums the number of closed issues in a day of a single project from all days of a set time interval and puts in
     * an ordered map of total closed issues by day
     * @param xDaysAgo Start of the interval
     * @param projectId
     * @return Ordered map of closed issues by day
     * @throws RedmineException
     */
    public Map<String, Double> closedIssuesInTimeInterval(Long xDaysAgo, Integer projectId) throws RedmineException {
        double closedIssues;
        Map<String, Double> closedIssuesMap = new TreeMap<>();
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Iterator<Issue> iterator = getClosedIssuesInTimeInterval(xDaysAgo, projectId);
        while (iterator.hasNext()) {
            Issue issue = iterator.next();
            if (issue.getParentId() == null) {
                String issueClosedDate = dateFormat.format(issue.getClosedOn());
                closedIssuesMap.putIfAbsent(issueClosedDate, 0D);
                closedIssues = closedIssuesMap.get(issueClosedDate);
                closedIssues++;
                closedIssuesMap.put(issueClosedDate, closedIssues);
            }
        }
        return closedIssuesMap;
    }

    /**
     * Sums spent hours from issues received
     * @param issuesId
     * @return Total spent hour
     * @throws RedmineException
     */
    public Float getSpentHour(Integer... issuesId) throws RedmineException {
        float result = 0;
        for (Integer issueId : issuesId) {
            result += getSpentHour(issueId);
        }
        return result;
    }

    /**
     * Get issue spent hours based on issue ID
     * @param issueId
     * @return Spent hour
     * @throws RedmineException
     */
    private Float getSpentHour(Integer issueId) throws RedmineException {
        RedmineManager mgr = RedmineManagerFactory.createWithApiKey(uri, apiAccessKey);
        IssueManager issueManager = mgr.getIssueManager();
        Issue issue = issueManager.getIssueById(issueId);
        return issue.getSpentHours();
    }

    /**
     * Create an iterator for the closed issues of a single project in a time interval
     * @param xDaysAgo Start of the interval
     * @param projectId
     * @return Iterator for the closed issues collection
     * @throws RedmineException
     */
    private Iterator<Issue> getClosedIssuesInTimeInterval(Long xDaysAgo, Integer projectId) throws RedmineException {
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

    /**
     * Create an iterator for all the issues of a single project in a time interval
     * @param xDaysAgo Start of the interval
     * @param projectId
     * @return Iterator for the issues collection
     * @throws RedmineException
     */
    private Iterator<Issue> getIssuesInTimeInterval(Long xDaysAgo, Integer projectId) throws RedmineException {
        LocalDateTime xDaysAgoLocalDate;
        Date startDate, endDate;
        xDaysAgoLocalDate = LocalDateTime.now().minusDays(xDaysAgo);
        startDate = Date.from(xDaysAgoLocalDate.atZone(ZoneId.systemDefault()).toInstant());
        endDate = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
        RedmineManager mgr = RedmineManagerFactory.createWithApiKey(uri, apiAccessKey);
        IssueManager issueManager = mgr.getIssueManager();
        Map<String, String> parameters = new HashMap<>();
        parameters.put("project_id", String.valueOf(projectId));
        parameters.put("status_id", "*");
        parameters.put("closed_on", "><" + DateUtil.toRedmineFormat(startDate) + "|" + DateUtil.toRedmineFormat(endDate));
        return new IteratorIssues.Builder().withParameters(parameters).build(issueManager);
    }

    /**
     * Create an iterator for all the issues of a single project in a set time interval
     * @param xDaysAgo
     * @param localDateTimeEndDate
     * @param projectId
     * @return Iterator for the issues collection
     * @throws RedmineException
     */
    private Iterator<Issue> getClosedIssuesInSetInterval(Long xDaysAgo, LocalDateTime localDateTimeEndDate, Integer projectId) throws RedmineException {
        Date startDate, endDate;
        startDate = Date.from(localDateTimeEndDate.minusDays(xDaysAgo).atZone(ZoneId.systemDefault()).toInstant());
        endDate = Date.from(localDateTimeEndDate.atZone(ZoneId.systemDefault()).toInstant());
        RedmineManager mgr = RedmineManagerFactory.createWithApiKey(uri, apiAccessKey);
        IssueManager issueManager = mgr.getIssueManager();
        Map<String, String> parameters = new HashMap<>();
        parameters.put("project_id", String.valueOf(projectId));
        parameters.put("status_id", "closed");
        parameters.put("closed_on", "><" + DateUtil.toRedmineFormat(startDate) + "|" + DateUtil.toRedmineFormat(endDate));
        return new IteratorIssues.Builder().withParameters(parameters).build(issueManager);
    }

    /**
     * Sums the number of worked hours of a single project in a day from all days of a set time interval and puts in
     * an ordered map of total worked hours by day
     * @param xDaysAgo Start of the interval
     * @param projectId
     * @return Ordered map of worked hours by day
     * @throws RedmineException
     */
    public Map<String, Double> workedHoursInTimeInterval(Long xDaysAgo, Integer projectId) throws RedmineException {
        double workedHours;
        Map<String, Double> workedHoursMap = new TreeMap<>();
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Iterator<Issue> iterator = getClosedIssuesInTimeInterval(xDaysAgo, projectId);
        while (iterator.hasNext()) {
            Issue issue = iterator.next();
            if (issue.getParentId() == null) {
                Date issueClosedDate = issue.getClosedOn();
                workedHoursMap.putIfAbsent(dateFormat.format(issueClosedDate), 0D);
                workedHours = workedHoursMap.get(dateFormat.format(issueClosedDate));
                workedHours += getSpentHour(issue.getId());
                workedHoursMap.put(dateFormat.format(issueClosedDate), workedHours);
            }
        }
        return workedHoursMap;
    }

    /**
     * Sums the number of worked hours of an employee in a project from all days of a set time interval and puts in an
     * ordered map of total worked hours by person
     * @param xDaysAgo Start of the interval
     * @param projectId
     * @return Ordered map of worked hours by person
     * @throws RedmineException
     */
    public Map<String, Double> workedHoursByPerson(Long xDaysAgo, Integer projectId) throws RedmineException {
        double workedHours;
        Map<String, Double> workedHoursByPersonMap = new TreeMap<>();
        Iterator<Issue> iterator = getIssuesInTimeInterval(xDaysAgo, projectId);
        while (iterator.hasNext()) {
            Issue issue = iterator.next();
            if (issue.getParentId() == null) {
                String assignee = issue.getAssignee().getFullName();
                workedHoursByPersonMap.putIfAbsent(assignee, 0D);
                workedHours = workedHoursByPersonMap.get(assignee);
                workedHours += getSpentHour(issue.getId());
                workedHoursByPersonMap.put(assignee, workedHours);
            }
        }
        return workedHoursByPersonMap;
    }

    /**
     * Calculates the issue average closing time of a set time period and puts in each day of another time period.
     * Example: If xDaysAgo = 5 and eachDayInterval = 5 then for the date 20160131 it would calculate the mean of the
     * issues closing time of the days 31, 30, 29, 28 and 27, and store it with the key 20160131 of the ordered map.
     * The date 20160130 would take the mean of the days 30, 29, 28, 27 and 26, and so on.
     * @param xDaysAgo
     * @param eachDayInterval
     * @param projectId
     * @return Ordered map of issue average closing time of a time period for each day.
     * @throws RedmineException
     */
    public Map<String, Double> averageClosingTimeByProjectInTimeInterval(Long xDaysAgo, Long eachDayInterval, Integer projectId) throws RedmineException {
        LocalDateTime endDate = LocalDateTime.now();
        LocalDateTime startDate = endDate.minusDays(xDaysAgo);
        Map<String, Double> averageClosingTimeMap = new TreeMap<>();
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String formattedEndDate;
        Double averageClosingTime;
        for (; endDate.isAfter(startDate); endDate = endDate.minusDays(1)) {
            formattedEndDate = dateFormat.format(Date.from(endDate.atZone(ZoneId.systemDefault()).toInstant()));
            averageClosingTimeMap.putIfAbsent(formattedEndDate, 0D);
            averageClosingTime = DecimalUtil.roundToDouble(durationAvgClosedIssuesByProject(eachDayInterval, endDate, projectId), 2);
            averageClosingTimeMap.put(formattedEndDate, averageClosingTime);
        }
        return averageClosingTimeMap;
    }

    /**
     * Sums the number of opened issues of a single project
     * @param projectId
     * @return Total number of opened issues
     * @throws RedmineException
     */
    public int openedIssuesByProjectId(Integer projectId) throws RedmineException {
        int sum = 0;
        Iterator<Issue> iterator = getOpenedIssuesByProject(projectId);
        while (iterator.hasNext()) {
            Issue issue = iterator.next();
            if (issue.getParentId() == null){
                sum++;
            }
        }
        return sum;
    }

    /**
     * Sums the number of closed issues of a single project ina set time interval
     * @param xDaysAgo Start of the interval
     * @param projectId
     * @return Total number of closed issues
     * @throws RedmineException
     */
    public int totalClosedIssuesByProjectIdInTimeInterval(Long xDaysAgo, Integer projectId) throws RedmineException {
        Iterator<Issue> iterator = getClosedIssuesInTimeInterval(xDaysAgo, projectId);
        int sum = 0;
        while (iterator.hasNext()) {
            Issue issue = iterator.next();
            if (issue.getParentId() == null) {
                sum++;
            }
        }
        return sum;
    }

    /**
     * Sums the number of issues that are open for more than xHours of a single project
     * @param projectId
     * @param xHours Threshold for the time an issue is open
     * @return Total number of opened issues for more than xHours
     * @throws RedmineException
     */
    public int openedIssuesForMoreThanXHoursByProject(Integer projectId, double xHours) throws RedmineException {
        Iterator<Issue> iterator = getOpenedIssuesByProject(projectId);
        int sum = 0;
        while (iterator.hasNext()) {
            Issue issue = iterator.next();
            if (issue.getCreatedOn() != null) {
                if (DateUtil.diffHour(issue.getCreatedOn(),
                        Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant())) > xHours
                        && issue.getParentId() == null)
                    sum++;
            }
        }
        return sum;
    }

    /**
     * Calculate the mean of issues closing time of a single project in a set time interval
     * @param xDaysAgo Start of the interval
     * @param projectId
     * @return Mean of issues closing time in a set interval
     * @throws RedmineException
     */
    public double durationAvgClosedIssuesByProject(Long xDaysAgo, LocalDateTime endDate, Integer projectId) throws RedmineException {
        Iterator<Issue> iterator;
        if (endDate != null) {
            iterator = getClosedIssuesInSetInterval(xDaysAgo, endDate, projectId);
        } else {
            iterator = getClosedIssuesInTimeInterval(xDaysAgo, projectId);
        }
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
