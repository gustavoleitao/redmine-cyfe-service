package br.com.logique.cyfeservice;

import br.com.logique.easyspark.SparkEngine;

/**
 * Created by Yuri on 20/04/2016.
 */
public class SparkManager {

    public void bootstrap() {
        SparkEngine engine = new SparkEngine.Builder()
                .withBasePackage("br.com.logique")
                .build();
        engine.setUp();
    }
}
