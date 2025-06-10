package com.jetbrains.ebankingapp3.model;


public enum StatutEnrollement {
    EN_ATTENTE, VALIDE, REJETE;
    public static StatutEnrollement fromString(String statut) {
        if (statut == null) return null;
        for (StatutEnrollement sc : StatutEnrollement.values()) {
            if (sc.name().equalsIgnoreCase(statut)) {
                return sc;
            }
        }
        return null; // or throw exception
    }
}

