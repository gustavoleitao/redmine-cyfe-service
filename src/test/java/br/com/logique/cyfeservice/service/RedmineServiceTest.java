package br.com.logique.cyfeservice.service;

import com.taskadapter.redmineapi.RedmineException;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * Created by Gustavo on 05/05/2016.
 */
public class RedmineServiceTest {

    String applicationKey = "029b49eaa97e110f0ce5d8149cb4d622365eba58";
    String applicationUri = "http://209.208.90.122/redmine";

    @Test
    public void OpenedIssuesByProjectId() throws Exception {
        RedmineService redmineService = RedmineServiceFactory
                .createRedmineService(applicationKey, applicationUri);
        int openedIssues = redmineService.openedIssuesByProjectId(55);
        System.out.println(openedIssues);
    }

    @Test
    public void ClosedIssuesByProjectId() throws Exception {
        RedmineService redmineService = RedmineServiceFactory
                .createRedmineService(applicationKey, applicationUri);
        int closedIssues = redmineService.totalClosedIssuesByProjectIdInTimeInterval(5L, 42);
        System.out.println(closedIssues);
    }

    @Test
    public void durationAvgClosedIssuesByProject() throws Exception {
        RedmineService redmineService = RedmineServiceFactory
                .createRedmineService(applicationKey, applicationUri);
        double duration = redmineService.durationAvgClosedIssuesByProject(5L, 42);
        System.out.println(duration);
    }

    @Test
    public void openedIssuesForMoreThanXHoursByProject() throws Exception {
        RedmineService redmineService = RedmineServiceFactory
                .createRedmineService(applicationKey, applicationUri);
        int openedIssues = redmineService.openedIssuesForMoreThanXHoursByProject(55, 24);
        System.out.println(openedIssues);
    }

    @Test
    public void issuesInExecutionByProjectId() throws Exception {
        RedmineService redmineService = RedmineServiceFactory
                .createRedmineService(applicationKey, applicationUri);
        List<String> issuesInExecutionList = redmineService.issuesInExecutionByProjectId(52);
        Assert.assertNotNull(issuesInExecutionList);
        System.out.println(issuesInExecutionList.toString());
    }

    @Test
    public void getSpentHour() throws RedmineException {
        RedmineService redmineService = RedmineServiceFactory
                .createRedmineService(applicationKey, applicationUri);
        Float spentHours = redmineService.getSpentHour(4032, 4173);
        Assert.assertNotNull("Não pode ser nulo", spentHours);
        Assert.assertNotEquals("Deve ser diferente de zero", 0, spentHours);
    }

    @Test
    public void workedHoursInTimeInterval() throws RedmineException {
        RedmineService redmineService = RedmineServiceFactory
                .createRedmineService(applicationKey, applicationUri);
        Map<String, Double> workedHours = redmineService.workedHoursInTimeInterval(5L, 42);
        System.out.println(workedHours);
    }

    @Test
    public void closedIssuesInTimeInterval() throws RedmineException {
        RedmineService redmineService = RedmineServiceFactory
                .createRedmineService(applicationKey, applicationUri);
        Map<String, Double> closedIssues = redmineService.closedIssuesInTimeInterval(5L, 42);
        System.out.println(closedIssues);
    }

    @Test
    public void totalClosedIssuesByProjectIdInTimeInterval() throws RedmineException {
        RedmineService redmineService = RedmineServiceFactory
                .createRedmineService(applicationKey, applicationUri);
        int totalClosedIssues = redmineService.totalClosedIssuesByProjectIdInTimeInterval(5L, 42);
        System.out.println(totalClosedIssues);
    }

    @Test
    public void workedHoursByPerson() throws RedmineException {
        RedmineService redmineService = RedmineServiceFactory
                .createRedmineService(applicationKey, applicationUri);
        Map<String, Double> workedHoursByPersonMap = redmineService.workedHoursByPerson(5L, 42);
        System.out.println(workedHoursByPersonMap);
    }

}