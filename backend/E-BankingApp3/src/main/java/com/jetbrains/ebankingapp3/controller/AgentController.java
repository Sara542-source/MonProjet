package com.jetbrains.ebankingapp3.controller;

import com.jetbrains.ebankingapp3.model.Agent;
import com.jetbrains.ebankingapp3.model.Role;
import com.jetbrains.ebankingapp3.service.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/agents")
public class AgentController {
    private final AgentService agentService;

    @Autowired
    public AgentController(AgentService agentService) {
        this.agentService = agentService;
    }

    // Get all agents
    @GetMapping
    public ResponseEntity<List<Agent>> getAllAgents() {
        List<Agent> agents = agentService.getAllAgents();
        return new ResponseEntity<>(agents, HttpStatus.OK);
    }

    // Get agent by ID
    @GetMapping("/{id}")
    public ResponseEntity<Agent> getAgentById(@PathVariable("id") Long id) {
        return agentService.getAgentById(id)
                .map(agent -> new ResponseEntity<>(agent, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Get agent by matricule
    @GetMapping("/matricule/{matricule}")
    public ResponseEntity<Agent> getAgentByMatricule(@PathVariable("matricule") String matricule) {
        return agentService.getAgentByMatricule(matricule)
                .map(agent -> new ResponseEntity<>(agent, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Get agents by bank
    @GetMapping("/banque/{banqueId}")
    public ResponseEntity<List<Agent>> getAgentsByBanque(@PathVariable("banqueId") Long banqueId) {
        List<Agent> agents = agentService.getAgentsByBanque(banqueId);
        return new ResponseEntity<>(agents, HttpStatus.OK);
    }

    // Get agents by role
    @GetMapping("/role/{role}")
    public ResponseEntity<List<Agent>> getAgentsByRole(@PathVariable("role") Role role) {
        List<Agent> agents = agentService.getAgentsByRole(role);
        return new ResponseEntity<>(agents, HttpStatus.OK);
    }

    // Create new agent
    @PostMapping
    public ResponseEntity<Agent> createAgent(@RequestBody Agent agent) {
        try {
            Agent createdAgent = agentService.createAgent(agent);
            return new ResponseEntity<>(createdAgent, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Update agent
    @PutMapping("/{id}")
    public ResponseEntity<Agent> updateAgent(@PathVariable("id") Long id, @RequestBody Agent agentDetails) {
        try {
            return agentService.updateAgent(id, agentDetails)
                    .map(updatedAgent -> new ResponseEntity<>(updatedAgent, HttpStatus.OK))
                    .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Delete agent
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAgent(@PathVariable("id") Long id) {
        if (agentService.deleteAgent(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

// Agent authentication
   /* @PostMapping("/authenticate")
    public ResponseEntity<Boolean> authenticateAgent(
            @RequestParam String login,
            @RequestParam String password) {
        boolean isAuthenticated = agentService.authenticateAgent(login, password);
        return new ResponseEntity<>(isAuthenticated, isAuthenticated ? HttpStatus.OK : HttpStatus.UNAUTHORIZED);
    }*/

    // Check if matricule exists
    @GetMapping("/exists/matricule/{matricule}")
    public ResponseEntity<Boolean> matriculeExists(@PathVariable("matricule") String matricule) {
        boolean exists = agentService.matriculeExists(matricule);
        return new ResponseEntity<>(exists, HttpStatus.OK);
    }

    // Check if email exists
    @GetMapping("/exists/email/{email}")
    public ResponseEntity<Boolean> emailExists(@PathVariable("email") String email) {
        boolean exists = agentService.emailExists(email);
        return new ResponseEntity<>(exists, HttpStatus.OK);
    }

    // Check if login exists
    @GetMapping("/exists/login/{login}")
    public ResponseEntity<Boolean> loginExists(@PathVariable("login") String login) {
        boolean exists = agentService.loginExists(login);
        return new ResponseEntity<>(exists, HttpStatus.OK);
    }
}