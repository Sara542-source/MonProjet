package com.jetbrains.ebankingapp3.service;

import com.jetbrains.ebankingapp3.model.Account;
import com.jetbrains.ebankingapp3.model.Client;
import com.jetbrains.ebankingapp3.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final ClientService clientRepository ;

@Autowired
    public AccountServiceImpl(AccountRepository accountRepository , ClientService clientRepository ) {
        this.accountRepository = accountRepository;
        this.clientRepository = clientRepository;
    }
    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }
    @Override
    public Optional<Account> getAccountById(Long accountId) {
        return accountRepository.findById(accountId);
    }
    @Override
    public Optional<Account> getAccountByNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber);
    }
    @Override
    public List<Account> getAccountsByUser(Long userId) {
        return accountRepository.findByUserId(userId);
    }
    @Override
    public List<Account> getAccountsByBranch(Long branchId) {
        return accountRepository.findByBranchId(branchId);
    }
    @Override
    public Account createAccount(Account account) {
        // Vérifie que le client est renseigné et que son id est valide (id > 0)
        if (account.getClient() == null || account.getClient().getClientId() <= 0) {
            throw new IllegalArgumentException("Client must be specified");
        }

        int clientId = account.getClient().getClientId();

        // Recherche du client existant en base
        Client client = clientRepository.getClientById(clientId);
        if (client == null) {
            throw new IllegalArgumentException("Client not found");
        }

        // Lie le client trouvé (persistant) à l'account
        account.setClient(client);

        // Sauvegarde du compte
        return accountRepository.save(account);
    }
    @Override
    public Optional<Account> updateAccount(Long accountId, Account accountDetails) {
        Optional<Account> optionalAccount = accountRepository.findById(accountId);
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            account.setAccountNumber(accountDetails.getAccountNumber());
            account.setAccountType(accountDetails.getAccountType());
            account.setBalance(accountDetails.getBalance());
            account.setStatus(accountDetails.getStatus());
            account.setClient(accountDetails.getClient());
            account.setBranchId(accountDetails.getBranchId());
            account.setLastUpdated(java.time.LocalDateTime.now());
            return Optional.of(accountRepository.save(account));
        } else {
            return Optional.empty();
        }
    }
    @Override
    public boolean deleteAccount(Long accountId) {
        if (accountRepository.existsById(accountId)) {
            accountRepository.deleteById(accountId);
            return true;
        }
        return false;
    }
    @Override
    public boolean accountExists(Long accountId) {
        return accountRepository.existsById(accountId);
    }
    @Override
    public boolean accountNumberExists(String accountNumber) {
        return accountRepository.existsByAccountNumber(accountNumber);
    }
    @Override
    public List<Account> getActiveAccountsByUser(Long userId) {
        return accountRepository.findByUserIdAndStatus(userId, "ACTIVE");
    }
    @Override
    public List<Account> getAccountsByUserAndType(Long userId, String accountType) {
        return accountRepository.findByUserIdAndAccountType(userId, accountType);
    }
    @Override
    public Double getAccountBalance(Long accountId) {
        Optional<Account> account = accountRepository.findById(accountId);
        return account.map(acc -> acc.getBalance() != null ? acc.getBalance() : 0.0).orElse(0.0);
    }
@Override
    public boolean transferFunds(Long sourceAccountId, Long targetAccountId, Double amount) {
        if (amount == null || amount <= 0.0) {
            throw new IllegalArgumentException("Transfer amount must be positive");
        }

        Account sourceAccount = accountRepository.findById(sourceAccountId)
                .orElseThrow(() -> new IllegalArgumentException("Source account not found"));

        Account targetAccount = accountRepository.findById(targetAccountId)
                .orElseThrow(() -> new IllegalArgumentException("Target account not found"));

        // Gestion des nulls pour balance et overdraft
        Double sourceBalance = sourceAccount.getBalance() != null ? sourceAccount.getBalance() : 0.0;
        Double sourceOverdraft = sourceAccount.getOverdraftLimit() != null ? sourceAccount.getOverdraftLimit() : 0.0;
        Double availableBalance = sourceBalance + sourceOverdraft;

        if (availableBalance < amount) {
            throw new IllegalArgumentException("Insufficient funds for transfer");
        }

        // Check same currency
        if (!sourceAccount.getCurrencyCode().equals(targetAccount.getCurrencyCode())) {
            throw new IllegalArgumentException("Cannot transfer between different currencies");
        }

        // Perform transfer
        sourceAccount.setBalance(sourceBalance - amount);

        Double targetBalance = targetAccount.getBalance() != null ? targetAccount.getBalance() : 0.0;
        targetAccount.setBalance(targetBalance + amount);

        // Update last modified
        sourceAccount.setLastUpdated(java.time.LocalDateTime.now());
        targetAccount.setLastUpdated(java.time.LocalDateTime.now());

        accountRepository.save(sourceAccount);
        accountRepository.save(targetAccount);

        return true;
    }


}
