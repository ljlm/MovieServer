package com.movie.Tools;

/**
 * Created by lionelm on 1/7/2017.
 */
public class User {
    private String username;
    private String firstname;
    private String lastname;

    public User (String username, String firstname, String lastname){
        this.username=username;
        this.firstname=firstname;
        this.lastname=lastname;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
}
