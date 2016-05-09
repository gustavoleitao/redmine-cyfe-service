package br.com.logique.cyfeservice;

/**
 * Created by Gustavo on 05/05/2016.
 */
public class Application {

    private static Application instance = new Application();

    private Application() {
    }

    public static Application getInstance(){
        return instance;
    }

    public String getURI(){
        return "http://209.208.90.122/redmine";
    }

    public String getKey(){
        return "029b49eaa97e110f0ce5d8149cb4d622365eba58";
    }

}
