package br.com.logique.cyfeservice.components;

/**
 * Created by Yuri on 20/04/2016.
 */
public class Gauge implements CyfeComponent {

    private double valor;
    private double max;
    private String title;

    private Gauge(double valor, double max, String title) {
        this.valor = valor;
        this.max = max;
        this.title = title;
    }

    public static Gauge of(double valor, double max, String title) {
        return new Gauge(valor, max, title);
    }

    @Override
    public String response() {
        return title + "," + "Target" + "\n" + valor + "," + max;
    }
}