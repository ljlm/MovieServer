package com.movie.services;

import com.movie.tools.errors.InvalidRoleException;

/**
 * This service validates the role to be admin.
 */

public class RoleValidator {
    public static int ADMIN = 0 ;
    public static int USER = 1;


    public static void validateAdmin(int role) throws InvalidRoleException {
        if ( ADMIN != role){
            throw new InvalidRoleException("Unauthorized");
        }

    }
}
