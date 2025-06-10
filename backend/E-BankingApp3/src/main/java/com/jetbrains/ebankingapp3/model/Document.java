//package com.jetbrains.ebankingapp3.model;
//
//import jakarta.persistence.*;
//
//import java.util.Date;
//
//@Entity
//@Table(name="documnt")
//public class Document {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "document_id")
//    private Long id;
//
//    @Column(nullable = false)
//    private String nom;
//
//    @Column(nullable = false)
//    private String type;
//
//    @Column(name = "chemin_fichier", nullable = false)
//    private String cheminFichier;
//
//    @Temporal(TemporalType.TIMESTAMP)
//    @Column(name = "date_depot", nullable = false)
//    private Date dateDepot;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "enrollement_id", nullable = false)
//    private Enrollement enrollement;
//
//    public String getCheminFichier() {
//        return cheminFichier;
//    }
//
//    public void setCheminFichier(String cheminFichier) {
//        this.cheminFichier = cheminFichier;
//    }
//
//    public Date getDateDepot() {
//        return dateDepot;
//    }
//
//    public void setDateDepot(Date dateDepot) {
//        this.dateDepot = dateDepot;
//    }
//
//    public Enrollement getEnrollement() {
//        return enrollement;
//    }
//
//    public void setEnrollement(Enrollement enrollement) {
//        this.enrollement = enrollement;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getNom() {
//        return nom;
//    }
//
//    public void setNom(String nom) {
//        this.nom = nom;
//    }
//
//    public String getType() {
//        return type;
//    }
//
//    public void setType(String type) {
//        this.type = type;
//    }
//}
//
