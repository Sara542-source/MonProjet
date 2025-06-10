package com.jetbrains.ebankingapp3.repository;

import com.jetbrains.ebankingapp3.dto.VirementRequest;
import com.jetbrains.ebankingapp3.model.Account;
import com.jetbrains.ebankingapp3.model.Client;
import com.jetbrains.ebankingapp3.model.Transaction;
import com.jetbrains.ebankingapp3.model.TypeTransaction;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Repository
@Transactional
public class TransactionDaoImpl implements TransactionDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void saveTransaction(Transaction transaction) {
        sessionFactory.getCurrentSession().persist(transaction);

    }

    @Override
    public void makeVirement(VirementRequest request) {
        Session session = sessionFactory.getCurrentSession();

        Account sender = session.get(Account.class, request.getSenderAccountId());
        Account receiver = session.get(Account.class, request.getReceiverAccountId());

        if (sender == null || receiver == null) {
            throw new RuntimeException("Compte émetteur ou destinataire introuvable.");
        }

        Client senderClient = sender.getClient();

        // Vérification du mot de passe
        if (!senderClient.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("Mot de passe incorrect.");
        }

        if (sender.getBalance() < request.getAmount()) {
            throw new RuntimeException("Solde insuffisant.");
        }

        sender.setBalance(sender.getBalance() - request.getAmount());
        receiver.setBalance(receiver.getBalance() + request.getAmount());

        Transaction transaction = new Transaction();
        transaction.setAmount(request.getAmount());
        transaction.setType("VIREMENT");
        transaction.setTypeTransaction(TypeTransaction.valueOf(request.getTypeTransaction().toUpperCase()));
        transaction.setDate(LocalDateTime.now());
        transaction.setSenderAccount(sender);
        transaction.setReceiverAccount(receiver);

        session.persist(transaction);
        session.merge(sender);
        session.merge(receiver);
    }
}
