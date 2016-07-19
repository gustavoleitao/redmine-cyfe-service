package br.com.logique.cyfeservice.controller;

import br.com.logique.cyfeservice.Application;
import br.com.logique.cyfeservice.components.*;
import br.com.logique.cyfeservice.service.DataFormatFunction;
import br.com.logique.cyfeservice.service.RedmineService;
import br.com.logique.cyfeservice.service.RedmineServiceFactory;
import br.com.logique.easyspark.annotations.Controller;
import br.com.logique.easyspark.annotations.Path;
import com.taskadapter.redmineapi.RedmineException;

import java.util.Map;

/**
 * Controller responsible for centralizing support requisitions.
 *
 * @author Gustavo Leit�o
 */
@Controller
public class RedmineController {

    Application application = Application.getInstance();

    /**
     * Takes the requisition from the Cyfe widget and returns a string with the number of opened issues of a single
     * project.
     * @param projectId
     * @return String with opened issues in the format recognized by the Cyfe number widget.
     * @throws RedmineException
     */
    @Path(":controller/openedIssues/:projectId")
    public String openedIssuesNumber(Integer projectId) throws RedmineException {
        RedmineService redmineService = RedmineServiceFactory
                .createRedmineService(application.getKey(), application.getURI());
        int openedIssues = redmineService.openedIssuesByProjectId(projectId);
        return CyfeNumber.fromData(openedIssues, "Opened Issues").response();
    }

    /**
     * Takes the requisition from the Cyfe widget and returns a string with the number of closed issues of a single
     * project in a time interval.
     * @param xDaysAgo Start of the interval
     * @param projectId
     * @return String with closed issues in the format recognized by the Cyfe number widget.
     * @throws RedmineException
     */
    @Path(":controller/totalclosedissuesbyprojectidintimeinterval/:xDaysAgo/:projectId")
    public String totalClosedIssuesNumber(Long xDaysAgo, Integer projectId) throws RedmineException {
        RedmineService redmineService = RedmineServiceFactory
                .createRedmineService(application.getKey(), application.getURI());
        int closedIssues = redmineService.totalClosedIssuesByProjectIdInTimeInterval(xDaysAgo, projectId);
        return CyfeNumber.fromData(closedIssues, "Closed Issues").response();
    }

    /**
     * Takes the requisition from the Cyfe widget and returns a string with the issues average closing time of a single
     * project.
     * @param xDaysAgo Start of the interval
     * @param projectId
     * @return String with issues average closing time in the format recognized by the Cyfe number widget.
     * @throws RedmineException
     */
    @Path(":controller/timeavg/:xDaysAgo/:projectId")
    public String averageClosingTimeNumber(Long xDaysAgo, Integer projectId) throws RedmineException {
        RedmineService redmineService = RedmineServiceFactory
                .createRedmineService(application.getKey(), application.getURI());
        double avDuration = redmineService.durationAvgClosedIssuesByProject(xDaysAgo, projectId);
        return CyfeNumber.fromData(avDuration, "Issues Average Closing Time (DD:HH:MM)(:)").response();
    }

    /**
     * Takes the requisition from the Cyfe widget and returns a string with the opened issues for more than xHours of a
     * single project.
     * @param projectId
     * @param xHours Threshold for the time an issue is open
     * @return String with total number of opened issues for more than xHours in the format recognized by the Cyfe
     * number widget.
     * @throws RedmineException
     */
    @Path(":controller/openedIssuesForMoreThanXHours/:projectId/:xHours")
    public String openedIssuesForMoreThanXHoursNumber(Integer projectId, double xHours) throws RedmineException {
        RedmineService redmineService = RedmineServiceFactory
                .createRedmineService(application.getKey(), application.getURI());
        int openedIssuesForMoreThanXHours = redmineService.openedIssuesForMoreThanXHoursByProject(projectId, xHours);
        return CyfeNumber.fromData(openedIssuesForMoreThanXHours, "Opened Issues For More Than 8 Hours").response();
    }

    /**
     * Takes the requisition from the Cyfe widget and returns a string with the closed issues of a single project in a
     * set time interval.
     * @param xDaysAgo Start of the interval
     * @param projectId
     * @return String with closed issues by day in the format recognized by the Cyfe chart widget.
     * @throws RedmineException
     */
    @Path(":controller/closedissuesintimeinterval/:xDaysAgo/:projectId")
    public String closedIssuesInTimeInterval(long xDaysAgo, Integer projectId) throws RedmineException {
        RedmineService redmineService = RedmineServiceFactory
                .createRedmineService(application.getKey(), application.getURI());
        Map<String, Double> closedIssuesMap = redmineService.closedIssuesInTimeInterval(xDaysAgo, projectId);
        DataFormatFunction dataFormatFunction = new DataFormatFunction();
        DataFormatter dataFormatter = dataFormatFunction.applyStringValueFormat(closedIssuesMap, CreateHeader.from("Date", "Closed Issues"),
                PreviousPeriodComparison.from(closedIssuesMap));
        return CyfeChart.fromDataFormat(dataFormatter).response();
    }

    /**
     * Takes the requisition from the Cyfe widget and returns a string with the worked hours of a single project in a
     * set time interval.
     * @param xDaysAgo Start of the interval
     * @param projectId
     * @return String with worked hours by day in the format recognized by the Cyfe chart widget.
     * @throws RedmineException
     */
    @Path(":controller/workedhoursintimeinterval/:xDaysAgo/:projectId")
    public String workedHoursInTimeInterval(long xDaysAgo, Integer projectId) throws RedmineException {
        RedmineService redmineService = RedmineServiceFactory
                .createRedmineService(application.getKey(), application.getURI());
        Map<String, Double> workedHoursMap = redmineService.workedHoursInTimeInterval(xDaysAgo, projectId);
        DataFormatFunction dataFormatFunction = new DataFormatFunction();
        DataFormatter dataFormatter = dataFormatFunction.applyStringValueFormat(workedHoursMap, CreateHeader.from("Date","Worked Hours"),
                PreviousPeriodComparison.from(workedHoursMap));
        return CyfeChart.fromDataFormat(dataFormatter).response();
    }

    /**
     * Takes the requisition from the Cyfe widget and returns a string with the issues in execution description of a
     * single project.
     * @param projectId
     * @return String with issues in execution in the format recognized by the Cyfe table widget.
     * @throws RedmineException
     */
    @Path(":controller/issuesinexecutionbyprojectid/:projectId")
    public String issuesInExecutionByProjectIdTable(Integer projectId) throws RedmineException {
        RedmineService redmineService = RedmineServiceFactory
                .createRedmineService(application.getKey(), application.getURI());
        Map<String, String> issuesInExecutionMap = redmineService.issuesInExecutionByProjectId(projectId);
        DataFormatFunction dataFormatFunction = new DataFormatFunction();
        DataFormatter dataFormatter = dataFormatFunction.applyStringStringFormat(issuesInExecutionMap,
                CreateHeader.from("Issue ID(*)", "Issue Subject"), "0");
        return CyfeTable.fromCyfeTable(dataFormatter).response();
    }

    /**
     * Takes the requisition from the Cyfe widget and returns a string with the worked hours by person of a single
     * project.
     * @param xDaysAgo Start of the interval
     * @param projectId
     * @return String with worked hours by person in the format recognized by the Cyfe list widget.
     * @throws RedmineException
     */
    @Path(":controller/workedhoursbyperson/:xDaysAgo/:projectId")
    public String workedHoursByPersonList(long xDaysAgo, Integer projectId) throws RedmineException {
        RedmineService redmineService = RedmineServiceFactory
                .createRedmineService(application.getKey(), application.getURI());
        Map<String, Double> workedHoursByPersonList = redmineService.workedHoursByPerson(xDaysAgo, projectId);
        DataFormatFunction dataFormatFunction = new DataFormatFunction();
        DataFormatter dataFormatter = dataFormatFunction.applyStringValueFormat(workedHoursByPersonList,
                CreateHeader.from("Employee", "Worked Hours"), PreviousPeriodComparison.from(workedHoursByPersonList));
        return CyfeList.fromCyfeList(dataFormatter).response();
    }

}
