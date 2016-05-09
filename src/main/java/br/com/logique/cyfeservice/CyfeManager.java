package br.com.logique.cyfeservice;

import br.com.logique.cyfeservice.components.*;
import br.com.logique.cyfeservice.service.RedmineServiceLagacy;

/**
 * Manager all actions to Cyfe dashboard.
 *
 * @author Yuri Aguiar
 */
public class CyfeManager {

    private RedmineServiceLagacy redmineServiceLagacy;

    public CyfeManager() {
        this.redmineServiceLagacy = new RedmineServiceLagacy();
    }

    public String getFaturamentoGauge() {
        double faturamentoAnual = redmineServiceLagacy.faturamentoAnual();
        CyfeComponent cyfeComponent = Gauge.of(faturamentoAnual, 1000d, "Faturamento Anual($)");
        return cyfeComponent.response();
    }

    public String getFaturamentoNumber() {
        double faturamentoAnual = redmineServiceLagacy.faturamentoAnual();
        CyfeComponent cyfeComponent = br.com.logique.cyfeservice.components.Number.of(faturamentoAnual, "Ações($)");
        return cyfeComponent.response();
    }

    public String getFaturamentoPie() {
        float[] faturamentoAnualList = redmineServiceLagacy.faturamentoAnualList();
        CyfeComponent cyfeComponent = Pie.of(faturamentoAnualList);
        return cyfeComponent.response();
    }

    public String getFaturamentoFunnel() {
        float[] faturamentoAnualList = redmineServiceLagacy.faturamentoAnualList();
        CyfeComponent cyfeComponent = Funnel.of(faturamentoAnualList);
        return cyfeComponent.response();
    }

    public String getFaturamnetoList() {
        float[] faturamentoAnualList = redmineServiceLagacy.faturamentoAnualList();
        CyfeComponent cyfeComponent = List.of(faturamentoAnualList);
        return cyfeComponent.response();
    }

    public String getFaturamentoTable() {
        float[] faturamentoAnualList1 = redmineServiceLagacy.faturamentoAnualList();
        float[] faturamentoAnualList2 = redmineServiceLagacy.faturamentoAnualList();
        CyfeComponent cyfeComponent = Table.of(faturamentoAnualList1, faturamentoAnualList2);
        return cyfeComponent.response();
    }
}
