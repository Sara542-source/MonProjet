package com.jetbrains.ebankingapp3.auth;

import com.jetbrains.ebankingapp3.model.Client;
import com.jetbrains.ebankingapp3.repository.ClientDao;
import com.jetbrains.ebankingapp3.service.ClientService;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final ClientDao clientDao;
    private final SessionManager sessionManager;

    public AuthService(ClientDao clientDao, SessionManager sessionManager) {
        this.clientDao = clientDao;
        this.sessionManager = sessionManager;
    }

    public String authenticate(String login, String password) {
        Client client = clientDao.findByLoginAndPassword(login, password);
        if (client != null) {
            return sessionManager.createSession(client);
        }
        return null;
    }

    public Client getClientFromSession(String sessionId) {
        return sessionManager.getClientFromSession(sessionId);
    }

    public void logout(String sessionId) {
        sessionManager.invalidateSession(sessionId);
    }
}