package com.jetbrains.ebankingapp3.repository;

import com.jetbrains.ebankingapp3.model.Bank;

import java.util.List;
import java.util.Optional;

public interface BankRepository {
    List<Bank> findAll();
    Optional<Bank> findById(Long id);
    Optional<Bank> findByCode(String code);
    Bank save(Bank bank);
    void deleteById(Long id);
    boolean existsById(Long id);
    boolean existsByCode(String code);
}