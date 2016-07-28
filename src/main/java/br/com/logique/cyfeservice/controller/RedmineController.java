package br.com.logique.cyfeservice.controller;

import br.com.logique.cyfeservice.Application;
import br.com.logique.cyfeservice.components.*;
import br.com.logique.cyfeservice.model.business.CreateHeader;
import br.com.logique.cyfeservice.model.business.DataFormatter;
import br.com.logique.cyfeservice.model.business.PreviousPeriodComparison;
import br.com.logique.cyfeservice.model.business.DataFormatFunction;
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
        return CyfeNumber.fromData(openedIssues, "Tarefas abertas").response();
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
        return CyfeNumber.fromData(closedIssues, "Tarefas fechadas").response();
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
        double avDuration = redmineService.durationAvgClosedIssuesByProject(xDaysAgo, null, projectId);
        return CyfeNumber.fromData(avDuration, "Tempo médio de fechamento das tarefas (DD:HH:MM)(:)").response();
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
        return CyfeNumber.fromData(openedIssuesForMoreThanXHours, "Tarefas abertas por mais de 8 horas").response();
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
        DataFormatter dataFormatter = dataFormatFunction.applyStringValueFormat(closedIssuesMap, CreateHeader.from("Date", "Tarefas fechadas"),
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
        DataFormatter dataFormatter = dataFormatFunction.applyStringValueFormat(workedHoursMap, CreateHeader.from("Date","Horas trabalhadas"),
                PreviousPeriodComparison.from(workedHoursMap));
        return CyfeChart.fromDataFormat(dataFormatter).response();
    }

    /**
     * Takes the requisition from the Cyfe widget and returns a string with the issues in execution ID and description of a
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
                CreateHeader.from("ID tarefa(*)", "Descrição"), "0");
        return CyfeTable.fromCyfeTable(dataFormatter).response();
    }

    /**
     * Takes the requisition from the Cyfe widget and returns a string with the waiting issues ID, description and waiting time of a
     * single project.
     * @param projectId
     * @return String with waiting issues in the format recognized by the Cyfe table widget.
     * @throws RedmineException
     */
    @Path(":controller/waitingissuesbyprojectid/:projectId")
    public String waitingIssuesByProjectId(Integer projectId) throws RedmineException {
        RedmineService redmineService = RedmineServiceFactory
                .createRedmineService(application.getKey(), application.getURI());
        Map<String, String> waitingIssuesMap = redmineService.waitingIssuesByProjectId(projectId);
        DataFormatFunction dataFormatFunction = new DataFormatFunction();
        DataFormatter dataFormatter = dataFormatFunction.applyStringStringFormat(waitingIssuesMap,
                CreateHeader.from("ID tarefa(*)", "Descrição", "Tempo de espera (DD:HH)"), "0");
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
    public String workedHoursByPersonList(Long xDaysAgo, Integer projectId) throws RedmineException {
        RedmineService redmineService = RedmineServiceFactory
                .createRedmineService(application.getKey(), application.getURI());
        Map<String, Double> workedHoursByPersonList = redmineService.workedHoursByPerson(xDaysAgo, projectId);
        DataFormatFunction dataFormatFunction = new DataFormatFunction();
        DataFormatter dataFormatter = dataFormatFunction.applyStringValueFormat(workedHoursByPersonList,
                CreateHeader.from("Empregado", "Horas trabalhadas"), PreviousPeriodComparison.from(workedHoursByPersonList));
        return CyfeList.fromCyfeList(dataFormatter).response();
    }

    /**
     * Takes the requisition from the Cyfe widget and returns a string with the average issue closing time of a time
     * period for each day.
     * @param xDaysAgo
     * @param eachDayInterval
     * @param projectId
     * @return String with issue average closing times in the format recognized by the Cyfe list widget.
     * @throws RedmineException
     */
    @Path(":controller/averageclosingtimebyproject/:xDaysAgo/:eachDayInterval/:projectId")
    public String averageClosingTimeChart(Long xDaysAgo, Long eachDayInterval, Integer projectId) throws RedmineException {
        RedmineService redmineService = RedmineServiceFactory
                .createRedmineService(application.getKey(), application.getURI());
        Map<String, Double> averageClosingTimeMap = redmineService.averageClosingTimeByProjectInTimeInterval(xDaysAgo, eachDayInterval, projectId);
        DataFormatFunction dataFormatFunction = new DataFormatFunction();
        DataFormatter dataFormatter = dataFormatFunction.applyStringValueFormat(averageClosingTimeMap,
                CreateHeader.from("Date", "Tempo médio de fechamento (Horas)"), "0");
        return CyfeChart.fromDataFormat(dataFormatter).response();
    }

}
