package com.jetbrains.ebankingapp3.repository;

import com.jetbrains.ebankingapp3.model.Account;
import com.jetbrains.ebankingapp3.model.Client;
import com.jetbrains.ebankingapp3.model.Transaction;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class ClientDaoImpl implements ClientDao {
    private final SessionFactory sessionFactory;

    // Injection via constructeur
    public ClientDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public Client findByLoginAndPassword(String login, String password) {
        Session session = sessionFactory.getCurrentSession();// cette ligne nécessite que la méthode soit exécutée dans un contexte transactionnel, sinon tu auras une erreur
        String hql = "FROM Client c WHERE c.login = :login AND c.password = :password";
        return session.createQuery(hql, Client.class)
                .setParameter("login", login)
                .setParameter("password", password)
                .uniqueResult();
    }

    @Override
    public Client updateClient(int id, Client updatedClient) {
        Session session = sessionFactory.getCurrentSession();
        Client existingClient = session.get(Client.class, id);
        if (existingClient != null) {
            updatedClient.setClientId(id);
            session.merge(updatedClient);
            return updatedClient;
        }
        return null;
    }
    @Override
    public Client findClientById(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Client.class, id);
    }


@Override

    public List<Account> getAccountsByClientId(int clientId) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM Account a WHERE a.client.clientId = :clientId";
        return session.createQuery(hql, Account.class)
                .setParameter("clientId", clientId)
                .getResultList();
    }
@Override
    public List<Transaction> getTransactionsByClientId(int clientId) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "SELECT t FROM Transaction t WHERE t.senderAccount.client.clientId = :clientId";
        return session.createQuery(hql, Transaction.class)
                .setParameter("clientId", clientId)
                .getResultList();
    }
    @Override
    public double getAccountBalance(int clientId, int id) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "SELECT a.balance FROM Account a WHERE a.client.clientId = :clientId AND a.id = :id";
        Double balance = session.createQuery(hql, Double.class)
                .setParameter("clientId", clientId)
                .setParameter("id", id)
                .uniqueResult();
        return balance != null ? balance : 0.0;
    }
}
