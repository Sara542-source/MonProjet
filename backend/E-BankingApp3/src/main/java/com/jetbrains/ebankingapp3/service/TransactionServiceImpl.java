package com.jetbrains.ebankingapp3.service;

import com.jetbrains.ebankingapp3.dto.VirementRequest;
import com.jetbrains.ebankingapp3.model.Transaction;
import com.jetbrains.ebankingapp3.repository.TransactionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionDao transactionDao;

    public void makeVirement(VirementRequest request) {
        transactionDao.makeVirement(request);
    }
    public void saveTransaction(Transaction transaction) {
        transactionDao.saveTransaction(transaction);
    }
}
