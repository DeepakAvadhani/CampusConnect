package com.example.campusconnect.User.UI.Profile;

public class UserHelper {
    private String name,regno,course,sem,email;

    public UserHelper() {
    }

    public UserHelper(String name, String regno, String course, String sem, String email) {
        this.name = name;
        this.regno = regno;
        this.course = course;
        this.sem = sem;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegno() {
        return regno;
    }

    public void setRegno(String regno) {
        this.regno = regno;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getSem() {
        return sem;
    }

    public void setSem(String sem) {
        this.sem = sem;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
