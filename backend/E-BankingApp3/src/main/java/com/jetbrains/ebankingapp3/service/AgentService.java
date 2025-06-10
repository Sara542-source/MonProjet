package com.jetbrains.ebankingapp3.service;

import com.jetbrains.ebankingapp3.model.Agent;
import com.jetbrains.ebankingapp3.model.Role;

import java.util.List;
import java.util.Optional;

public interface AgentService {
    List<Agent> getAllAgents();
    Optional<Agent> getAgentById(Long id);
    Optional<Agent> getAgentByMatricule(String matricule);
    Optional<Agent> getAgentByEmail(String email);
    Optional<Agent> getAgentByLogin(String login);
    Agent createAgent(Agent agent);
    Optional<Agent> updateAgent(Long id, Agent agentDetails);
    boolean deleteAgent(Long id);
    boolean agentExists(Long id);
    boolean matriculeExists(String matricule);
    boolean emailExists(String email);
    boolean loginExists(String login);
    List<Agent> getAgentsByBanque(Long banqueId);
    List<Agent> getAgentsByRole(Role role);
    Optional<Agent> authenticateAgent(String login, String password);
}
