package com.afrikaizen.capstone.models;

import io.realm.RealmObject;

/**
 * Created by Steve on 10/08/2015.
 */
public class Results extends RealmObject {

    private int id;
    private String name;
    private String surname;
    private String condition;
    private String treated;
    private String results;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getTreated() {
        return treated;
    }

    public void setTreated(String treated) {
        this.treated = treated;
    }

    public String getResults() {
        return results;
    }

    public void setResults(String results) {
        this.results = results;
    }
}