package com.example.consumerestapijava.models;

public enum ERole {
    ROLE_USER("ROLE_USER"),
    ROLE_MODERATOR("ROLE_MODERATOR"),
    ROLE_ADMIN("ROLE_ADMIN");

    public final String label;

    private ERole(String label) {
        this.label = label;
    }

    public static ERole valueOfLabel(String label) {
        for (ERole e : values()) {
            if (e.label.equals(label)) {
                return e;
            }
        }
        return null;
    }
}
