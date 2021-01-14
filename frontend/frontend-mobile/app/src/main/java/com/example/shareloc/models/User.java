package com.example.shareloc.models;

public class User {

    private String email;
    private String lastname;
    private String firstname;

    private static User instance = null;

    private User() {}

    public static User getInstance() {
        if(instance == null) {
            instance = new User();
        }
        return instance;
    }

    public void deleteUser() {
        instance = null;
    }

    public void setUser(String email, String lastname, String firstname) {
        setEmail(email);
        setLastname(lastname);
        setFirstname(firstname);
    }

    public String getEmail() {
        return email;
    }
    public String getLastname() {
        return lastname;
    }
    public String getFirstname() {
        return firstname;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    @Override
    public String toString() {
        return "Bienvenue " + firstname + " " + lastname;
    }
}
