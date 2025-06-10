package com.jetbrains.ebankingapp3.repository;

import com.jetbrains.ebankingapp3.model.Account;
import com.jetbrains.ebankingapp3.model.Client;
import com.jetbrains.ebankingapp3.model.Transaction;

import java.util.List;

public interface ClientDao {
    Client findByLoginAndPassword(String login, String password);
    Client updateClient(int id, Client updatedClient);
    Client findClientById(int id);
    double getAccountBalance(int clientId, int id);
    List<Account> getAccountsByClientId(int clientId) ;
    List<Transaction> getTransactionsByClientId(int clientId);
}
