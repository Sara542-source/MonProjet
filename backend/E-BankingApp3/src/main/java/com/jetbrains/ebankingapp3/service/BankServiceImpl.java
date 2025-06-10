package com.jetbrains.ebankingapp3.service;

import com.jetbrains.ebankingapp3.model.Bank;
import com.jetbrains.ebankingapp3.repository.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BankServiceImpl implements BankService {

    private final BankRepository bankRepository;

    @Autowired
    public BankServiceImpl(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    @Override
    public List<Bank> getAllBanks() {
        return bankRepository.findAll();
    }

    @Override
    public Optional<Bank> getBankById(Long id) {
        return bankRepository.findById(id);
    }

    @Override
    public Optional<Bank> getBankByCode(String code) {
        return bankRepository.findByCode(code);
    }

    @Override
    @Transactional
    public Bank createBank(Bank bank) {
        // Validate bank code uniqueness
        if (bankRepository.existsByCode(bank.getCode())) {
            throw new IllegalArgumentException("Bank code already exists");
        }

        // Additional validation can be added here
        if (bank.getName() == null || bank.getName().isEmpty()) {
            throw new IllegalArgumentException("Bank name cannot be empty");
        }

        return bankRepository.save(bank);
    }

    @Override
    @Transactional
    public Optional<Bank> updateBank(Long id, Bank bankDetails) {
        return bankRepository.findById(id).map(existingBank -> {
            // Check if code is being changed and if new code already exists
            if (!existingBank.getCode().equals(bankDetails.getCode()) &&
                    bankRepository.existsByCode(bankDetails.getCode())) {
                throw new IllegalArgumentException("Bank code already exists");
            }

            // Update fields if they are provided in bankDetails
            if (bankDetails.getCode() != null) {
                existingBank.setCode(bankDetails.getCode());
            }
            if (bankDetails.getName() != null) {
                existingBank.setName(bankDetails.getName());
            }
            if (bankDetails.getAddress() != null) {
                existingBank.setAddress(bankDetails.getAddress());
            }
            if (bankDetails.getPhone() != null) {
                existingBank.setPhone(bankDetails.getPhone());
            }
            if (bankDetails.getEmail() != null) {
                existingBank.setEmail(bankDetails.getEmail());
            }

            return bankRepository.save(existingBank);
        });
    }

    @Override
    public boolean existsById(Long id) {
        return bankRepository.existsById(id);
    }

    @Override
    public boolean existsByCode(String code) {
        return bankRepository.existsByCode(code);
    }

    @Override
    @Transactional
    public boolean deleteBank(Long id) {
        if (bankRepository.existsById(id)) {
            bankRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
