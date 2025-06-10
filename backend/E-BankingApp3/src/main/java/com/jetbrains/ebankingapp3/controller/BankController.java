package com.jetbrains.ebankingapp3.controller;

import com.jetbrains.ebankingapp3.model.Bank;
import com.jetbrains.ebankingapp3.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/banks")
public class BankController {

    private final BankService bankService;

    @Autowired
    public BankController(BankService bankService) {
        this.bankService = bankService;
    }

    // Create a new bank
    @PostMapping
    public ResponseEntity<Bank> createBank(@RequestBody Bank bank) {
        try {
            Bank createdBank = bankService.createBank(bank);
            return new ResponseEntity<>(createdBank, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Get all banks
    @GetMapping
    public ResponseEntity<List<Bank>> getAllBanks() {
        List<Bank> banks = bankService.getAllBanks();
        return new ResponseEntity<>(banks, HttpStatus.OK);
    }

    // Get bank by ID
    @GetMapping("/{id}")
    public ResponseEntity<Bank> getBankById(@PathVariable Long id) {
        Optional<Bank> bank = bankService.getBankById(id);
        return bank.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Get bank by code
    @GetMapping("/code/{code}")
    public ResponseEntity<Bank> getBankByCode(@PathVariable String code) {
        Optional<Bank> bank = bankService.getBankByCode(code);
        return bank.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Update bank
    @PutMapping("/{id}")
    public ResponseEntity<Bank> updateBank(@PathVariable Long id, @RequestBody Bank bankDetails) {
        try {
            Optional<Bank> updatedBank = bankService.updateBank(id, bankDetails);
            return updatedBank.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Delete bank
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBank(@PathVariable Long id) {
        if (bankService.deleteBank(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Check if bank code exists
    @GetMapping("/exists/code/{code}")
    public ResponseEntity<Boolean> checkBankCodeExists(@PathVariable String code) {
        boolean exists = bankService.existsByCode(code);
        return new ResponseEntity<>(exists, HttpStatus.OK);
    }

    // Check if bank exists by ID
    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> checkBankExists(@PathVariable Long id) {
        boolean exists = bankService.existsById(id);
        return new ResponseEntity<>(exists, HttpStatus.OK);
    }
}