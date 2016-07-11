package br.com.logique.cyfeservice.controller;

import br.com.logique.cyfeservice.Application;
import br.com.logique.cyfeservice.components.*;
import br.com.logique.cyfeservice.service.DataFormatFunction;
import br.com.logique.cyfeservice.service.RedmineService;
import br.com.logique.cyfeservice.service.RedmineServiceFactory;
import br.com.logique.cyfeservice.service.TableDataFunction;
import br.com.logique.easyspark.annotations.Controller;
import br.com.logique.easyspark.annotations.Path;
import com.taskadapter.redmineapi.RedmineException;

import java.util.List;
import java.util.Map;

/**
 * Controlador respons�vel por centralizar requisi��es para o suporte
 *
 * @author Gustavo Leit�o
 */
@Controller
public class RedmineController {

    Application application = Application.getInstance();

    @Path(":controller/openedIssues/:projectId")
    public String openedIssuesNumber(Integer projectId) throws RedmineException {
        RedmineService redmineService = RedmineServiceFactory
                .createRedmineService(application.getKey(), application.getURI());
        int openedIssues = redmineService.openedIssuesByProjectId(projectId);
        return CyfeNumber.fromData(openedIssues, "Opened Issues").response();
    }

    @Path(":controller/totalclosedissuesbyprojectidintimeinterval/:xDaysAgo/:projectId")
    public String totalClosedIssuesNumber(Long xDaysAgo, Integer projectId) throws RedmineException {
        RedmineService redmineService = RedmineServiceFactory
                .createRedmineService(application.getKey(), application.getURI());
        int closedIssues = redmineService.totalClosedIssuesByProjectIdInTimeInterval(xDaysAgo, projectId);
        return CyfeNumber.fromData(closedIssues, "Closed Issues").response();
    }

    @Path(":controller/timeavg/:xDaysAgo/:projectId")
    public String averageClosingTimeNumber(Long xDaysAgo, Integer projectId) throws RedmineException {
        RedmineService redmineService = RedmineServiceFactory
                .createRedmineService(application.getKey(), application.getURI());
        double avDuration = redmineService.durationAvgClosedIssuesByProject(xDaysAgo, projectId);
        return CyfeNumber.fromData(avDuration, "Issues Average Closing Time (DD:HH:MM)(:)").response();
    }

    @Path(":controller/openedIssuesForMoreThanXHours/:projectId/:XHours")
    public String openedIssuesForMoreThanXHoursNumber(Integer projectId, double XHours) throws RedmineException {
        RedmineService redmineService = RedmineServiceFactory
                .createRedmineService(application.getKey(), application.getURI());
        int openedIssuesForMoreThanXHours = redmineService.openedIssuesForMoreThanXHoursByProject(projectId, XHours);
        return CyfeNumber.fromData(openedIssuesForMoreThanXHours, "Opened Issues For More Than 8 Hours").response();
    }

//    @Path(":controller/closedIssuesByMonthInLastXmonths/:XmonthsAgo/:projectId")
//    public String closedIssuesByMonthInLastXmonthsChart(long XmonthsAgo, Integer projectId) throws RedmineException {
//        RedmineService redmineService = RedmineServiceFactory
//                .createRedmineService(application.getKey(), application.getURI());
//        Map<YearMonth, Double> closedIssuesByMonth = redmineService.closedIssuesByMonthInLastXMonths(XmonthsAgo, projectId);
//        DataFormatFunction chartDataFunction = new DataFormatFunction();
//                PreviousPeriodComparison.from(closedIssuesByMonth));
//        chartData.responseMostRecentValue();
//        return CyfeChart.fromDataFormat(chartData).response();
//    }

//    @Path(":controller/hoursworkedbymonthinlastxmonths/:XmonthsAgo/:projectId")
//    public String workedHoursByMonthInLastXMonths(long XmonthsAgo, Integer projectId) throws RedmineException {
//        RedmineService redmineService = RedmineServiceFactory
//                .createRedmineService(application.getKey(), application.getURI());
//        Map<YearMonth, Double> hoursWorkedByMonth = redmineService.hoursWorkedByMonthInLastXMonths(XmonthsAgo, projectId);
//        DataFormatFunction chartDataFunction = new DataFormatFunction();
//                PreviousPeriodComparison.from(hoursWorkedByMonth));
//        return CyfeChart.fromDataFormat(chartData).response();
//    }

    @Path(":controller/closedissuesintimeinterval/:xDaysAgo/:projectId")
    public String closedIssuesInTimeInterval(long xDaysAgo, Integer projectId) throws RedmineException {
        RedmineService redmineService = RedmineServiceFactory
                .createRedmineService(application.getKey(), application.getURI());
        Map<String, Double> closedIssuesMap = redmineService.closedIssuesInTimeInterval(xDaysAgo, projectId);
        DataFormatFunction dataFormatFunction = new DataFormatFunction();
        DataFormat dataFormat = dataFormatFunction.apply(closedIssuesMap, CreateHeader.from("Date", "Closed Issues"),
                PreviousPeriodComparison.from(closedIssuesMap));
        return CyfeChart.fromDataFormat(dataFormat).response();
    }

    @Path(":controller/workedhoursintimeinterval/:xDaysAgo/:projectId")
    public String workedHoursInTimeInterval(long xDaysAgo, Integer projectId) throws RedmineException {
        RedmineService redmineService = RedmineServiceFactory
                .createRedmineService(application.getKey(), application.getURI());
        Map<String, Double> workedHoursMap = redmineService.workedHoursInTimeInterval(xDaysAgo, projectId);
        DataFormatFunction dataFormatFunction = new DataFormatFunction();
        DataFormat dataFormat = dataFormatFunction.apply(workedHoursMap, CreateHeader.from("Date","Worked Hours"),
                PreviousPeriodComparison.from(workedHoursMap));
        return CyfeChart.fromDataFormat(dataFormat).response();
    }

    @Path(":controller/issuesinexecutionbyprojectid/:projectId")
    public String issuesInExecutionByProjectIdTable(Integer projectId) throws RedmineException {
        RedmineService redmineService = RedmineServiceFactory
                .createRedmineService(application.getKey(), application.getURI());
        List<String> issuesInExecutionList = redmineService.issuesInExecutionByProjectId(projectId);
        TableDataFunction tableDataFunction = new TableDataFunction();
        TableData tableData = tableDataFunction.apply(issuesInExecutionList, CreateHeader.from("Issues In Execution"));
        return CyfeTable.fromCyfeTable(tableData).response();
    }

    @Path(":controller/workedhoursbyperson/:xDaysAgo/:projectId")
    public String workedHoursByPersonList(long xDaysAgo, Integer projectId) throws RedmineException {
        RedmineService redmineService = RedmineServiceFactory
                .createRedmineService(application.getKey(), application.getURI());
        Map<String, Double> workedHoursByPersonList = redmineService.workedHoursByPerson(xDaysAgo, projectId);
        DataFormatFunction dataFormatFunction = new DataFormatFunction();
        DataFormat dataFormat = dataFormatFunction.apply(workedHoursByPersonList, CreateHeader.from("Employee", "Worked Hours"),
                PreviousPeriodComparison.from(workedHoursByPersonList));
        return CyfeList.fromCyfeList(dataFormat).response();
    }

}
