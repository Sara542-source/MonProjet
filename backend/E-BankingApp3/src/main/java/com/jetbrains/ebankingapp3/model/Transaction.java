package com.jetbrains.ebankingapp3.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private int transactionId;

    private double amount;
    private String type;
    private LocalDateTime date;
    private String nom ;
    // on peux utilliser ici les methodes agile et traditionnelle
// Une transaction est liée à un seul compte (émetteur ou récepteur).Mais un compte peut être lié à plusieurs transactions comme émetteur ou récepteur.
    @ManyToOne //Une transaction a un seul compte émetteur (senderAccount),mais un compte émetteur peut être utilisé dans plusieurs transactions.
    @JoinColumn(name = "source_account_id")//sender_account_id
    private Account senderAccount;

    @ManyToOne
    @JoinColumn(name = "target_account_id")//receiver_account_id
    private Account receiverAccount;
//Pour les autres types (ex: dépôt), tu peux continuer d'utiliser ça
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_transaction")
    private TypeTransaction typeTransaction;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public TypeTransaction getTypeTransaction() {
        return typeTransaction;
    }

    public void setTypeTransaction(TypeTransaction typeTransaction) {
        this.typeTransaction = typeTransaction;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Account getReceiverAccount() {
        return receiverAccount;
    }

    public void setReceiverAccount(Account receiverAccount) {
        this.receiverAccount = receiverAccount;
    }

    public Account getSenderAccount() {
        return senderAccount;
    }

    public void setSenderAccount(Account senderAccount) {
        this.senderAccount = senderAccount;
    }
    //La clé étrangère assure que tu ne peux pas insérer dans transaction.sender_account_id une valeur d’ID qui n’existe pas dans la table account.
}
