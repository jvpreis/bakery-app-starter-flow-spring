package com.vaadin.starter.bakery.backend.data;

/**
 * Utility class for defining user roles in the bakery application.
 * <p>
 * Provides constants for each role and a method to retrieve all available roles.
 * Roles include BARISTA, BAKER, and ADMIN. The ADMIN role implicitly allows access to all views.
 * </p>
 */
public class Role {
    /** Role for users who operate as baristas. */
    public static final String BARISTA = "barista";
    /** Role for users who operate as bakers. */
    public static final String BAKER = "baker";
    /**
     * Role for administrators. This role implicitly allows access to all views.
     */
    public static final String ADMIN = "admin";

    /**
     * Private constructor to prevent instantiation. This class contains only static members.
     */
    private Role() {
        // Static methods and fields only
    }

    /**
     * Returns an array containing all defined roles.
     * @return an array of all role names
     */
    public static String[] getAllRoles() {
        return new String[] { BARISTA, BAKER, ADMIN };
    }

}
