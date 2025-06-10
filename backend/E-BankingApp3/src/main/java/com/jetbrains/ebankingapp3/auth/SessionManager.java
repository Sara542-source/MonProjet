package com.jetbrains.ebankingapp3.auth;

import com.jetbrains.ebankingapp3.model.Client;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

@Component
public class SessionManager {
    private final Map<String, Client> activeSessions = new ConcurrentHashMap<>();
    private final Map<Integer, String> clientToSessionMap = new ConcurrentHashMap<>();

    public String createSession(Client client) {
        String sessionId = generateSessionId();
        activeSessions.put(sessionId, client);
        clientToSessionMap.put(client.getClientId(), sessionId);
        return sessionId;
    }

    public Client getClientFromSession(String sessionId) {
        return activeSessions.get(sessionId);
    }

    public void invalidateSession(String sessionId) {
        Client client = activeSessions.get(sessionId);
        if (client != null) {
            clientToSessionMap.remove(client.getClientId());
        }
        activeSessions.remove(sessionId);
    }

    public void invalidateSessionByClientId(int clientId) {
        String sessionId = clientToSessionMap.get(clientId);
        if (sessionId != null) {
            activeSessions.remove(sessionId);
            clientToSessionMap.remove(clientId);
        }
    }

    private String generateSessionId() {
        return java.util.UUID.randomUUID().toString();
    }
}