package com.jetbrains.ebankingapp3.service;

import com.jetbrains.ebankingapp3.dto.VirementRequest;
import com.jetbrains.ebankingapp3.model.Transaction;

public interface TransactionService {
    void makeVirement(VirementRequest request) ;
    //saveTransaction est correcte pour enregistrer une transaction simple dans la base, par exemple un dépôt ou un retrait isolé.
    void saveTransaction(Transaction transaction);
}
