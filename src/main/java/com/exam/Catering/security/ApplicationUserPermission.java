package com.exam.Catering.security;

public enum ApplicationUserPermission {

    ADMIN_READ("admin:read"),
    ADMIN_WRITE("admin:write"),
    CLIENT_READ("client:read"),
    CLIENT_WRITE("client:write");

    private final String permission;

    ApplicationUserPermission(String permission) {

        this.permission = permission;
    }

    public String getPermission() {

        return permission;
    }
}

