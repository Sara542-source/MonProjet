package com.jetbrains.ebankingapp3.dto;

public class WebAuthnRegistrationRequest {
    private String clientId;
    private String registrationData;

    // Getters et Setters
    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getRegistrationData() {
        return registrationData;
    }

    public void setRegistrationData(String registrationData) {
        this.registrationData = registrationData;
    }
}