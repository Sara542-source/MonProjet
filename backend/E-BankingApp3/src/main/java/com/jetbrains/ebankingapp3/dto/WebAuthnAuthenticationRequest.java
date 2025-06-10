package com.jetbrains.ebankingapp3.dto;

public class WebAuthnAuthenticationRequest {
    private String clientId;
    private String authenticationData;

    // Getters et Setters
    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getAuthenticationData() {
        return authenticationData;
    }

    public void setAuthenticationData(String authenticationData) {
        this.authenticationData = authenticationData;
    }
}