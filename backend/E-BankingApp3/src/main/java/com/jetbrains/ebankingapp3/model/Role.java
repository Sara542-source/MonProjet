package com.jetbrains.ebankingapp3.model;

public enum Role {
    ADMIN_BANQUE, AGENT , AGENT_BANQUE, AGENT_CLIENTELE;
    public static Role fromString(String statut) {
        if (statut == null) return null;
        for (Role sc : Role.values()) {
            if (sc.name().equalsIgnoreCase(statut)) {
                return sc;
            }
        }
        return null; // or throw exception
    }
}

