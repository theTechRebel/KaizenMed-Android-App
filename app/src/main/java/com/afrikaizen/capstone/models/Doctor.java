package com.afrikaizen.capstone.models;

/**
 * Created by Steve on 07/08/2015.
 */
public class Doctor {
    public static class JSONObject{
        private String id;
        private String name;
        private String surname;
        private String gender;
        private String notes;

        public String getId() {
            return id;
        }

        public void setId(String id) {
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

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getNotes() {
            return notes;
        }

        public void setNotes(String notes) {
            this.notes = notes;
        }

    }

    public static class Error{
        private String error;

        public Error(String error){
            this.error = error;
        }

        public void setError(String error) {
            this.error = error;
        }

        public String getError() {
            return error;
        }
    }

    public static class Data{
        private String doctorsName;
        private String passWord;

        public Data(String doctorsName, String passWord) {
            this.doctorsName = doctorsName;
            this.passWord = passWord;
        }

        public String getDoctorsName() {
            return doctorsName;
        }

        public void setDoctorsName(String doctorsName) {
            this.doctorsName = doctorsName;
        }

        public String getPassWord() {
            return passWord;
        }

        public void setPassWord(String passWord) {
            this.passWord = passWord;
        }
    }
}
