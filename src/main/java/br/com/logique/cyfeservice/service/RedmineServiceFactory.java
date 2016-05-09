package br.com.logique.cyfeservice.service;

/**
 * Created by Gustavo on 17/04/2016.
 */
public class RedmineServiceFactory {

    private static String REDMINE_KEY = System.getProperty("redmine.key", "");
    private static String REDMINE_URL = System.getProperty("redmine.url", "");

    public static RedmineService createRedmineService() {
        return new RedmineService.BuilderRedmineService()
                .withApiAccessKey(REDMINE_KEY)
                .withURI(REDMINE_URL)
                .build();
    }

    public static RedmineService createRedmineService(String key, String uri) {
        return new RedmineService.BuilderRedmineService()
                .withApiAccessKey(key)
                .withURI(uri)
                .build();
    }



}
