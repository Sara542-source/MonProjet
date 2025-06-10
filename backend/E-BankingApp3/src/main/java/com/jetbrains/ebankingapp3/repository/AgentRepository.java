package com.jetbrains.ebankingapp3.repository;

import com.jetbrains.ebankingapp3.model.Agent;
import com.jetbrains.ebankingapp3.model.Role;

import java.util.List;
import java.util.Optional;

public interface AgentRepository {
    List<Agent> findAll();
    Optional<Agent> findById(Long id);
    Optional<Agent> findByMatricule(String matricule);
    Optional<Agent> findByEmail(String email);
    Optional<Agent> findByLogin(String login);
    Agent save(Agent agent);
    void deleteById(Long id);
    boolean existsById(Long id);
    boolean existsByMatricule(String matricule);
    boolean existsByEmail(String email);
    boolean existsByLogin(String login);
    List<Agent> findByBankId(Long bankId);
    List<Agent> findByRole(Role role);
}
