package com.afrikaizen.capstone.models;

import java.io.Serializable;

/**
 * Created by Steve on 08/08/2015.
 */
public class PatientsResults {
    public static class JSONObject implements Serializable {
        String name;
        String surname;
        String condition;
        String treated;
        String results;

        public JSONObject(String name, String surname, String condition, String treated, String results) {
            this.name = name;
            this.surname = surname;
            this.condition = condition;
            this.treated = treated;
            this.results = results;
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

    public static class Data {
        String ward;
        String name;

        public Data(String ward, String name) {
            this.ward = ward;
            this.name = name;
        }

        public String getWard() {
            return ward;
        }

        public void setWard(String ward) {
            this.ward = ward;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class Error {
        String error;

        public Error(String error) {
            this.error = error;
        }

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }
    }
}
