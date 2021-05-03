package com.strawhatdev.backend.models;

public enum Permission {
    POST_READ("post:read"),
    POST_WRITE("post:write"),
    USER_READ("user:read"),
    USER_WRITE("user:write");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }

}
