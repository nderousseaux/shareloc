package com.example.shareloc.models;

// Classe User simple
public class UserBis {

    private String email;
    private String lastname;
    private String firstname;

    public UserBis(String email, String lastname, String firstname) {
        this.email = email;
        this.lastname = lastname;
        this.firstname = firstname;
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
        return "Email " + email + " / firstname " + firstname + " / lastname " + lastname;
    }
}
