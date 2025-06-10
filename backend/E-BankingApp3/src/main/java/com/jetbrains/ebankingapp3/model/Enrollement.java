package com.jetbrains.ebankingapp3.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="enrollement")
public class Enrollement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "enrollement_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agent_id", nullable = false)
    private Agent agent;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_enrollement", nullable = false)
    private LocalDateTime dateEnrollement;

    @Column(nullable = false, unique = true)
    private String reference;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutEnrollement statut; // EN_ATTENTE, VALIDE, REJETE
    @Column(nullable = false)
    @ElementCollection
    private List<String> documents;

    public Agent getAgent() {
       return agent;
    }

   public void setAgent(Agent agent) {
       this.agent = agent;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public LocalDateTime getDateEnrollement() {
        return dateEnrollement;
    }

    public void setDateEnrollement(LocalDateTime dateEnrollement) {
        this.dateEnrollement = dateEnrollement;
    }

    public List<String> getDocuments() {
        return documents;
    }

    public void setDocuments(List<String> documents) {
        this.documents = documents;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public StatutEnrollement getStatut() {
        return statut;
    }

    public void setStatut(StatutEnrollement statut) {
        this.statut = statut;
    }
}

