package com.jetbrains.ebankingapp3.service;

import com.jetbrains.ebankingapp3.auth.AuthService;
import com.jetbrains.ebankingapp3.model.Account;
import com.jetbrains.ebankingapp3.model.Client;
import com.jetbrains.ebankingapp3.model.Transaction;
import com.jetbrains.ebankingapp3.repository.ClientDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class ClientServiceImpl implements ClientService {
    private final ClientDao clientDao;
    private final AuthService authService;


    public ClientServiceImpl(ClientDao clientDao, AuthService authService) {

        this.clientDao = clientDao;
        this.authService = authService;
    }

    @Override
    public Client login(String login, String password) {
        String sessionId = authService.authenticate(login, password);
        if (sessionId != null) {
            return authService.getClientFromSession(sessionId);
        }
        return null;
    }


    @Override
    public Client updateClient(int id, Client updatedClient) {
        return clientDao.updateClient(id, updatedClient);
    }

    @Override
    public Client getClientById(int id) {
        return clientDao.findClientById(id);
    }
    @Override
    public double getAccountBalance(int clientId, int id) {
        return clientDao.getAccountBalance(clientId, id);
    }
    @Override
    public List<Account> getAccountsByClientId(int clientId) {
        return clientDao.getAccountsByClientId(clientId);
    }
    @Override
    public List<Transaction> getTransactionsByClientId(int clientId) {
        return clientDao.getTransactionsByClientId(clientId);
    }



}
