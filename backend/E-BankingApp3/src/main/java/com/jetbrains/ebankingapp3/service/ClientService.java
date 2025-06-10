package com.jetbrains.ebankingapp3.service;

import com.jetbrains.ebankingapp3.model.Account;
import com.jetbrains.ebankingapp3.model.Client;
import com.jetbrains.ebankingapp3.model.Transaction;

import java.util.List;

public interface ClientService {
    Client login(String login, String password);
    Client updateClient(int id, Client updatedClient);
    Client getClientById(int id);
    double getAccountBalance(int clientId, int id);
    List<Account> getAccountsByClientId(int clientId) ;
    List<Transaction> getTransactionsByClientId(int clientId);
   //boolean sendCodeToClient(int id);
    //boolean verifyCodeSentToClient(int id, String code);
}
