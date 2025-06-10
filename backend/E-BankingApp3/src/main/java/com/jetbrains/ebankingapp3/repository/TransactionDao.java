package com.jetbrains.ebankingapp3.repository;

import com.jetbrains.ebankingapp3.dto.VirementRequest;
import com.jetbrains.ebankingapp3.model.Client;
import com.jetbrains.ebankingapp3.model.Transaction;

public interface TransactionDao {
    void saveTransaction(Transaction transaction);
    void makeVirement(VirementRequest request);

}
