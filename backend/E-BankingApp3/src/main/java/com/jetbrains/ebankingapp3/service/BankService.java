package com.jetbrains.ebankingapp3.service;

import com.jetbrains.ebankingapp3.model.Bank;

import java.util.List;
import java.util.Optional;

public interface BankService {
    List<Bank> getAllBanks();
    Optional<Bank> getBankById(Long id);
    Optional<Bank> getBankByCode(String code);
    Bank createBank(Bank bank);
    Optional<Bank> updateBank(Long id, Bank bankDetails);
    boolean deleteBank(Long id);
    boolean existsById(Long id);
    boolean existsByCode(String code);
}