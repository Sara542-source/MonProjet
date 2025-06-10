package com.jetbrains.ebankingapp3.controller;

import com.jetbrains.ebankingapp3.model.Account;
import com.jetbrains.ebankingapp3.model.Client;
import com.jetbrains.ebankingapp3.model.Transaction;
import com.jetbrains.ebankingapp3.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/client")
@CrossOrigin(origins = "http://localhost:4200")
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    /*@PostMapping("/login")
    public ResponseEntity<Client> login(@RequestParam("login") String login, @RequestParam("password") String password) {
        Client client = clientService.login(login, password);
        if (client != null) {
            // Authentification réussie, retourner le client authentifié
            return ResponseEntity.ok(client);
        } else {
            // Échec de l'authentification, retourner une réponse avec statut Unauthorized (401)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

    }*/

    @PutMapping("/update/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable("id") int id, @RequestBody Client updatedClient) {
        Client client = clientService.updateClient(id, updatedClient);
        if (client != null) {
            return ResponseEntity.ok(client);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable("id") int id) {
        Client client = clientService.getClientById(id);
        if (client != null) {
            return ResponseEntity.ok(client);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{clientId}/accounts/{id}/balance")
    public ResponseEntity<Double> getAccountBalance(@PathVariable("clientId") int clientId, @PathVariable("id") int id) {
        double balance = clientService.getAccountBalance(clientId, id);
        return ResponseEntity.ok(balance);
    }

    @GetMapping("/{clientId}/accounts")
    public ResponseEntity<List<Account>> getAccountsByClientId(@PathVariable("clientId") int clientId) {
        List<Account> accounts = clientService.getAccountsByClientId(clientId);
        if (accounts != null && !accounts.isEmpty()) {
            return ResponseEntity.ok(accounts);
        } else {
            return ResponseEntity.noContent().build();
        }
    }


    @GetMapping("/{clientId}/transactions")
    public ResponseEntity<List<Transaction>> getTransactionsByClientId(@PathVariable("clientId") int clientId) {
        List<Transaction> transactions = clientService.getTransactionsByClientId(clientId);
        return ResponseEntity.ok(transactions); // 200 OK, même si vide
    }

//    /// Fatima
//    @GetMapping("/authenticate/{id}")
//    public ResponseEntity<Void> authenticateFirst(@PathVariable("id") int id) {
//        if(clientService.sendCodeToClient(id)) return ResponseEntity.ok().build();
//        else return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//    }
//    @PostMapping("/authenticate/{id}")
//    public ResponseEntity<Void> authenticateSecond(@PathVariable("id") int id,@RequestParam("code") String code){
//        if(clientService.verifyCodeSentToClient(id,code)) {
//            System.out.println("the code is right");
//            return ResponseEntity.ok().build();
//        }
//        else return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//    }
}
