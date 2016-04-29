package br.com.logique.cyfeservice;

import br.com.logique.cyfeservice.components.*;
import br.com.logique.cyfeservice.service.RedmineService;

/**
 * Created by Yuri on 20/04/2016.
 */
public class CyfeManager {

    private RedmineService redmineService;

    public CyfeManager() {
        this.redmineService = new RedmineService();
    }

    public String getFaturamentoGauge() {
        double faturamentoAnual = redmineService.faturamentoAnual();
        CyfeComponent cyfeComponent = Gauge.of(faturamentoAnual, 1000d, "Faturamento Anual($)");
        return cyfeComponent.response();
    }

    public String getFaturamentoNumber() {
        double faturamentoAnual = redmineService.faturamentoAnual();
        CyfeComponent cyfeComponent = br.com.logique.cyfeservice.components.Number.of(faturamentoAnual, "Ações($)");
        return cyfeComponent.response();
    }

    public String getFaturamentoPie() {
        float[] faturamentoAnualList = redmineService.faturamentoAnualList();
        CyfeComponent cyfeComponent = Pie.of(faturamentoAnualList);
        return cyfeComponent.response();
    }

    public String getFaturamentoFunnel() {
        float[] faturamentoAnualList = redmineService.faturamentoAnualList();
        CyfeComponent cyfeComponent = Funnel.of(faturamentoAnualList);
        return cyfeComponent.response();
    }

    public String getFaturamnetoList() {
        float[] faturamentoAnualList = redmineService.faturamentoAnualList();
        CyfeComponent cyfeComponent = List.of(faturamentoAnualList);
        return cyfeComponent.response();
    }

    public String getFaturamentoTable() {
        float[] faturamentoAnualList1 = redmineService.faturamentoAnualList();
        float[] faturamentoAnualList2 = redmineService.faturamentoAnualList();
        CyfeComponent cyfeComponent = Table.of(faturamentoAnualList1, faturamentoAnualList2);
        return cyfeComponent.response();
    }
}
