package com.afrikaizen.kaizenmed.models;

/**
 * Created by Steve on 08/08/2015.
 */
public class PatientsResults {
    public static class JSONObject{
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
}
