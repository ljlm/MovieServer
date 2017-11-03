package com.movie.tools;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by lionelm on 1/7/2017.
 */
public class ActiveUser {
    private String username;
    private int userId;
    private int role;

    public ActiveUser(String username, int userId, int role){
        this.username=username;
        this.userId=userId;
        this.role=role;
    }

    public static ActiveUser getActiveUserData (ServletRequest servletRequest){
        HttpSession session = ((HttpServletRequest) servletRequest).getSession(true);
        ActiveUser activeUser = (ActiveUser) session.getAttribute("activeUser");
        return activeUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getUserId() {
        return userId;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
