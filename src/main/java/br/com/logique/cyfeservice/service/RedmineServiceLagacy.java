package br.com.logique.cyfeservice.service;

/**
 * Created by Yuri on 20/04/2016.
 */
public class RedmineServiceLagacy {

    public double faturamentoAnual() {
        return Math.random()*1000;
    }

    public float[] faturamentoAnualList() {
        float faturamentoAnualList[];
        faturamentoAnualList = new float[5];
        for (int i = 0; i < 5; i++) {
            faturamentoAnualList[i] = (float)faturamentoAnual();
        }
        return faturamentoAnualList;
    }
}
