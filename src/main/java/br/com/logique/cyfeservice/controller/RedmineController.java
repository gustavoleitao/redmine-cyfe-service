package br.com.logique.cyfeservice.controller;

import br.com.logique.cyfeservice.Application;
import br.com.logique.cyfeservice.components.Number;
import br.com.logique.cyfeservice.service.RedmineService;
import br.com.logique.cyfeservice.service.RedmineServiceFactory;
import br.com.logique.easyspark.annotations.Controller;
import br.com.logique.easyspark.annotations.Path;
import com.taskadapter.redmineapi.RedmineException;

/**
 * Controlador responsável por centralizar requisições para o suporte
 *
 * @author Gustavo Leitão
 */
@Controller
public class RedmineController {

    Application application = Application.getInstance();

    @Path(":controller/openedIssues/:projectId")
    public String openedIssuesNumber(Integer projectId) throws RedmineException {
        RedmineService redmineService = RedmineServiceFactory
                .createRedmineService(application.getKey(), application.getURI());
        int openedIssues = redmineService.openedIssuesByProjectId(projectId);
        return Number.of(openedIssues, "Suportes em abertos").response();
    }

    public String tempoMedioFechamentoNumber() throws RedmineException {
        RedmineService redmineService = RedmineServiceFactory
                .createRedmineService(application.getKey(), application.getURI());
        double avDuration = redmineService.durationAvgClosedIssueByProject(42);
        return Number.of(avDuration, "Média duração").response();
    }

}
