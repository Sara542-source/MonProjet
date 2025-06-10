package com.jetbrains.ebankingapp3.service;

import com.jetbrains.ebankingapp3.model.Account;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface AccountService {
    List<Account> getAllAccounts();
    Optional<Account> getAccountById(Long accountId);
    Optional<Account> getAccountByNumber(String accountNumber);
    List<Account> getAccountsByUser(Long userId);
    List<Account> getAccountsByBranch(Long branchId);
    Account createAccount(Account account);
    Optional<Account> updateAccount(Long accountId, Account accountDetails);
    boolean deleteAccount(Long accountId);
    boolean accountExists(Long accountId);
    boolean accountNumberExists(String accountNumber);
    List<Account> getActiveAccountsByUser(Long userId);
    List<Account> getAccountsByUserAndType(Long userId, String accountType);
    Double getAccountBalance(Long accountId);
    boolean transferFunds(Long sourceAccountId, Long targetAccountId, Double amount);
}
