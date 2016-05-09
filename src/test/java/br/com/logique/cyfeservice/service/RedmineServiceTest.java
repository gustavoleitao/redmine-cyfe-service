package br.com.logique.cyfeservice.service;

import com.taskadapter.redmineapi.RedmineException;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Gustavo on 05/05/2016.
 */
public class RedmineServiceTest {

    @Test
    public void testOpenedIssuesByProjectId() throws Exception {
        RedmineService redmineService = RedmineServiceFactory
                .createRedmineService("029b49eaa97e110f0ce5d8149cb4d622365eba58", "http://209.208.90.122/redmine");
        int openedIssues = redmineService.openedIssuesByProjectId(42);
        System.out.println(openedIssues);
    }



    @Test
    public void durationAvgClosedIssueByProject() throws Exception {
        RedmineService redmineService = RedmineServiceFactory
                .createRedmineService("029b49eaa97e110f0ce5d8149cb4d622365eba58", "http://209.208.90.122/redmine");
        double duration = redmineService.durationAvgClosedIssueByProject(42);
        System.out.println(duration);
    }

    @Test
    public void durationAvgClosedIssueByProject2() throws Exception {
        RedmineService redmineService = RedmineServiceFactory
                .createRedmineService("029b49eaa97e110f0ce5d8149cb4d622365eba58", "http://209.208.90.122/redmine");
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.DAY_OF_MONTH, -10);
        double duration = redmineService.durationAvgClosedIssueByProject(42, calendar.getTime());
    }

}