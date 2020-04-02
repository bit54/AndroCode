package com.example.dboperations;

public class User {

    public int sid;
    public String name, email, mobilenumber,dob,gender;

    public User()
    {

    }

    public User(int sid, String name, String email, String mobilenumber, String dob, String gender) {
        this.sid = sid;
        this.name = name;
        this.email = email;
        this.mobilenumber = mobilenumber;
        this.dob = dob;
        this.gender = gender;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobilenumber() {
        return mobilenumber;
    }

    public void setMobilenumber(String mobilenumber) {
        this.mobilenumber = mobilenumber;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
