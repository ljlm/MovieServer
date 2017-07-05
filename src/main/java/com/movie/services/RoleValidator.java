package com.movie.services;

/**
 * Created by lionelm on 7/5/2017.
 */
public class RoleValidator {
    public static int ADMIN = 0 ;
    public static int USER = 1;


    public static void validateAdmin(int role) throws Exception {
        if ( ADMIN != role){
            throw new Exception("Unauthorized");
        }

    }

    public static void validateUser (int role) throws Exception {
        if ( USER < role){
            throw new Exception("Unauthorized");
        }
    }

}
