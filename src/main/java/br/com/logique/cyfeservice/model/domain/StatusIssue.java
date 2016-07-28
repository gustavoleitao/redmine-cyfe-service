package br.com.logique.cyfeservice.model.domain;

/**
 * Created by Yuri on 19/05/2016.
 */
public enum StatusIssue {

    NEW(1), IN_EXECUTION(2), WAITING(11), CLOSED(10);

    private Integer id;

    StatusIssue(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getIdStr() {
        return id.toString();
    }
}
