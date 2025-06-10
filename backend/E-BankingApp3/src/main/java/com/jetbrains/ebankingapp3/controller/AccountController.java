package com.jetbrains.ebankingapp3.controller;


import com.jetbrains.ebankingapp3.model.Account;
import com.jetbrains.ebankingapp3.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    // Get all accounts (for bank administration)
    @GetMapping
    public ResponseEntity<List<Account>> getAllAccounts() {
        List<Account> accounts = accountService.getAllAccounts();
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    // Get account by ID
    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable("id") Long id) {
        return accountService.getAccountById(id)
                .map(account -> new ResponseEntity<>(account, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    // Get account by account number
    @GetMapping("/number/{accountNumber}")
    public ResponseEntity<Account> getAccountByNumber(@PathVariable("accountNumber") String accountNumber) {
        return accountService.getAccountByNumber(accountNumber)
                .map(account -> new ResponseEntity<>(account, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    // Get accounts for a specific user (for client interface)
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Account>> getAccountsByUser(@PathVariable("userId") Long userId) {
        List<Account> accounts = accountService.getAccountsByUser(userId);
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    // Get accounts by branch (for bank administration)
    @GetMapping("/branch/{branchId}")
    public ResponseEntity<List<Account>> getAccountsByBranch(@PathVariable("branchId") Long branchId) {
        List<Account> accounts = accountService.getAccountsByBranch(branchId);
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }
    // Create new account
    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        try {
            Account createdAccount = accountService.createAccount(account);
            System.out.println("account created");
            return new ResponseEntity<>(createdAccount, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            System.out.println("error account: " + e.getMessage());
            e.printStackTrace(); // <-- Ajoute cette ligne pour la stacktrace complète
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    // Update account
    @PutMapping("/{id}")
    public ResponseEntity<Account> updateAccount(@PathVariable("id") Long id, @RequestBody Account accountDetails) {
        try {
            return accountService.updateAccount(id, accountDetails)
                    .map(updatedAccount -> new ResponseEntity<>(updatedAccount, HttpStatus.OK))
                    .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Delete account
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable("id") Long id) {
        if (accountService.deleteAccount(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    // Get account balance
    @GetMapping("/{id}/balance")
    public ResponseEntity<Double> getAccountBalance(@PathVariable("id") Long id) {
        try {
           Double balance = accountService.getAccountBalance(id);
            return new ResponseEntity<>(balance, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Transfer funds between accounts
    @PostMapping("/transfer")
    public ResponseEntity<Void> transferFunds(
            @RequestParam Long sourceAccountId,
            @RequestParam Long targetAccountId,
            @RequestParam Double amount) {
        try {
            if (accountService.transferFunds(sourceAccountId, targetAccountId, amount)) {
                return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    // Get active accounts for user
    @GetMapping("/user/{userId}/active")
    public ResponseEntity<List<Account>> getActiveAccountsByUser(@PathVariable("userId") Long userId) {
        List<Account> accounts = accountService.getActiveAccountsByUser(userId);
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    // Get accounts by type for user
    @GetMapping("/user/{userId}/type/{accountType}")
    public ResponseEntity<List<Account>> getAccountsByUserAndType(
            @PathVariable("userId") Long userId,
            @PathVariable("accountType") String accountType) {
        List<Account> accounts = accountService.getAccountsByUserAndType(userId, accountType);
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

}
