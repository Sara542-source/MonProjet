//package com.jetbrains.ebankingapp3.service;
//
//import com.jetbrains.ebankingapp3.dto.WebAuthnRegistrationRequest;
//import com.jetbrains.ebankingapp3.dto.WebAuthnAuthenticationRequest;
//import com.jetbrains.ebankingapp3.model.Client;
//import com.webauthn4j.WebAuthnManager;
//import com.webauthn4j.authenticator.Authenticator;
//import com.webauthn4j.authenticator.AuthenticatorImpl;
//import com.webauthn4j.data.*;
//import com.webauthn4j.data.client.Origin;
//import com.webauthn4j.data.client.challenge.Challenge;
//import com.webauthn4j.data.client.challenge.DefaultChallenge;
//import com.webauthn4j.data.attestation.statement.COSEAlgorithmIdentifier;
//import com.webauthn4j.server.ServerProperty;
//import com.webauthn4j.util.Base64UrlUtil;
//import org.springframework.stereotype.Service;
//
//import java.util.Collections;
//import java.util.UUID;
//
//@Service
//public class WebAuthnService {
//
//    private final WebAuthnManager webAuthnManager;
//    private final ClientService clientService;
//
//    public WebAuthnService(ClientService clientService) {
//        this.clientService = clientService;
//        this.webAuthnManager = WebAuthnManager.createNonStrictWebAuthnManager();
//    }
//
//    public PublicKeyCredentialCreationOptions startRegistration(String clientId) {
//        Client client = clientService.getClientById(clientId);
//        if (client == null) {
//            throw new RuntimeException("Client not found");
//        }
//
//        Challenge challenge = new DefaultChallenge();
//
//        PublicKeyCredentialCreationOptions options = new PublicKeyCredentialCreationOptions(
//                new PublicKeyCredentialRpEntity("eBankingApp", "eBankingApp"),
//                new PublicKeyCredentialUserEntity(
//                        Base64UrlUtil.encodeToString(client.getClientId().getBytes()),
//                        client.getLogin(),
//                        client.getFname() + " " + client.getLname()
//                ),
//                challenge,
//                Collections.singletonList(
//                        new PublicKeyCredentialParameters(
//                                PublicKeyCredentialType.PUBLIC_KEY,
//                                COSEAlgorithmIdentifier.ES256
//                        )
//                )
//        );
//
//        return options;
//    }
//
//    public void finishRegistration(WebAuthnRegistrationRequest request) {
//        // Implémentez la validation de l'inscription WebAuthn
//    }
//
//    public boolean verifyAuthentication(WebAuthnAuthenticationRequest request) {
//        // Implémentez la vérification de l'authentification WebAuthn
//        return true; // Temporaire
//    }
//}