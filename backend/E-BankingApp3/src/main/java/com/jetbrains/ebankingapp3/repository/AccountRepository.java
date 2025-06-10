package com.jetbrains.ebankingapp3.repository;

import com.jetbrains.ebankingapp3.model.Account;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

//pour banck et agent
public interface AccountRepository {
    List<Account> findAll();
    Optional<Account> findById(Long accountId);
    Optional<Account> findByAccountNumber(String accountNumber);
    List<Account> findByUserId(Long userId);
    List<Account> findByBranchId(Long branchId);
    Account save(Account account);
    void deleteById(Long accountId);
    boolean existsById(Long accountId);
    boolean existsByAccountNumber(String accountNumber);
    List<Account> findByUserIdAndStatus(Long userId, String status);
    List<Account> findByUserIdAndAccountType(Long userId, String accountType);

    //@Query("SELECT a.accountType as type, COUNT(a) as count FROM Account a GROUP BY a.accountType")
    List<AccountTypeCount> countAccountsByType();

    interface AccountTypeCount {
        String getType() throws SQLException;
        long getCount() throws SQLException;
    }

    void updateBalance(Long accountId, Double newBalance);


}
