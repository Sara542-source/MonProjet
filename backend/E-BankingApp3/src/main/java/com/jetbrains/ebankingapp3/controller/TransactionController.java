package com.jetbrains.ebankingapp3.controller;

import com.jetbrains.ebankingapp3.dto.VirementRequest;
import com.jetbrains.ebankingapp3.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin(origins = "http://localhost:4200") // adapte si besoin
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;
    // Endpoint pour faire un virement
    @PostMapping("/virement")
    public ResponseEntity<String> makeVirement(@RequestBody VirementRequest request) {
        try {
            transactionService.makeVirement(request);
            return ResponseEntity.ok("Virement effectué avec succès.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erreur : " + e.getMessage());
        }
    }
}
