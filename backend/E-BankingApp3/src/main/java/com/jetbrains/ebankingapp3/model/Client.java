package com.jetbrains.ebankingapp3.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="client")

public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id")
    private int clientId ;

    @Column(name= "login")
    private String login ;
    @Column(name = "password")
    private String password ;
    @Column(name = "prenom")
    private String fname;
    @Column(name = "nom")
    private String lname;
    @Column(name = "email")
    private String email;
    @Column(name = "telephone")
    private String phone ;
    @Column(name = "cin")
    private String cin ;
    @Column(name="gender")
    private String gender ;
    @Column(name="date_naissance")
    private Date dateNaissance ;
    @Column(name="adresse")
    private String adresse ;
    @Column(name="profession")
    private String profession ;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="date_enregistrement")
    private LocalDateTime dateEnregistrment ;
    @Column(name="statut")
    private String statut;
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Account> accounts;
    //@Column(name = "webauthn_credentials", columnDefinition = "TEXT")
   // private String webauthnCredentials;
    @ManyToOne
    @JoinColumn(name="bank_id") // nom de la colonne clé étrangère dans la table Agent
    private Bank bank;

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public LocalDateTime getDateEnregistrment() {
        return dateEnregistrment;
    }

    public void setDateEnregistrment(LocalDateTime dateEnregistrment) {
        this.dateEnregistrment = dateEnregistrment;
    }

    public Date getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }



    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }


    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Client() {}
    public Client(String login, String password) {
        this.login = login;
        this.password = password;

    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
