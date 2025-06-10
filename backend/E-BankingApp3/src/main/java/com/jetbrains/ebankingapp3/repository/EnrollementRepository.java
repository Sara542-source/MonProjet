package com.jetbrains.ebankingapp3.repository;

import com.jetbrains.ebankingapp3.model.Enrollement;
import com.jetbrains.ebankingapp3.model.StatutEnrollement;

import java.util.List;
import java.util.Optional;

public interface EnrollementRepository {
    List<Enrollement> findAll();
    Optional<Enrollement> findById(Long id);
    List<Enrollement> findByClientId(Long clientId);
    List<Enrollement> findByAgentId(Long agentId);
    List<Enrollement> findByStatut(StatutEnrollement statut);
    Enrollement save(Enrollement enrollement);
    void deleteById(Long id);
    boolean existsById(Long id);
    boolean existsByClientIdAndStatut(Long clientId, StatutEnrollement statut);
    Optional<Enrollement> findByReference(String reference);

    List<Enrollement> findTop5ByOrderByDateEnrollementDesc();

    List<Enrollement> findRecent(int limit);
}