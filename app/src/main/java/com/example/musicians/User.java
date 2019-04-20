package com.example.musicians;

public class User extends UserList{
    String firstname;
    String lastname;
    String mobile;
    String city;
    String email;
    String aboutme;
    String birthday;
    String uid;

    public User () {}

    public User(String firstname, String lastname, String mobile, String city, String email, String aboutme, String birthday, String uid) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.mobile = mobile;
        this.city = city;
        this.email = email;
        this.aboutme = aboutme;
        this.birthday = birthday;
        this.uid = uid;
    }
    public String  getFirstname() {
        return firstname;
    }

    public String  getLastname() {
        return lastname;
    }
}



