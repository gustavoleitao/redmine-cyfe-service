package br.com.logique.cyfeservice.controller;

import br.com.logique.cyfeservice.CyfeManager;
import br.com.logique.easyspark.annotations.Controller;
import br.com.logique.easyspark.annotations.Path;

/**
 * Created by Yuri on 20/04/2016.
 */
@Controller
public class RedmineController {

    private CyfeManager cyfeManager = new CyfeManager();

    @Path("/faturamento-gauge/")
    public String faturamentoGauge() {
        return cyfeManager.getFaturamentoGauge();
    }

    @Path("/faturamento-number/")
    public String faturamentoNumber() {
        return cyfeManager.getFaturamentoNumber();
    }

    @Path("/faturamento-pie/")
    public String faturamentoPie() {
        return cyfeManager.getFaturamentoPie();
    }

    @Path("/faturamento-funnel/")
    public String faturamentoFunnel() {
        return cyfeManager.getFaturamentoFunnel();
    }

    @Path("/faturamento-list/")
    public String faturamentoList() {
       return cyfeManager.getFaturamnetoList();
    }

    @Path("/faturamento-table/")
    public String faturamentoTable() {
        return cyfeManager.getFaturamentoTable();
    }

}
