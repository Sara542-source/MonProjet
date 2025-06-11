import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CurrencyPipe } from '@angular/common';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { VirementService } from '../services/virement.service';
import { AccountService } from '../services/account.service';

@Component({
  selector: 'app-virement',
  standalone: true,
  imports: [FormsModule, CurrencyPipe, CommonModule, HttpClientModule],
  templateUrl: './virement.component.html',
  styleUrls: ['./virement.component.css']
})
export class VirementComponent implements OnInit {
  virement = {
    senderAccountId: null as number | null,
    receiverAccountId: null as number | null,
    amount: null as number | null,
    type: null as string | null,
  };
  motDePasse: string = '';
  provisionalPassword: string = 'saraslim123' ;
  message: string | null = null;

  showModal = false;
  showRecapModal = false;
  showPasswordModal = false;

  comptes: any[] = []; // <- Liste dynamique

  userPassword: string = '';

  constructor(
    private virementService: VirementService,
    private accountService: AccountService
  ) {}

  ngOnInit(): void {
  // Récupère l'id du client connecté depuis le localStorage
  const clientIdStr = localStorage.getItem('clientId');
  console.log('Valeur brute du clientId dans localStorage :', clientIdStr);

  const clientId = Number(clientIdStr);
  console.log('Valeur numérique du clientId :', clientId);

  if (clientId && !isNaN(clientId)) {
    this.accountService.getAccountsByClientId(clientId).subscribe({
      next: (accounts) => {
        console.log("Réponse comptes API :", accounts);
        this.comptes = accounts.map(account => ({
          id: account.id,
          rib: account.accountNumber,
          nom: account.accountType,
          solde: account.balance
        }));
      },
      error: (err) => {
        this.message = "Erreur lors de la récupération des comptes.";
        console.error('Erreur HTTP récupération comptes :', err);
      }
    });
  } else {
    this.message = "Aucun client connecté.";
    console.warn('clientId absent ou invalide dans le localStorage');
  }
}
  openCompteModal() {
    this.showModal = true;
  }

  closeCompteModal() {
    this.showModal = false;
  }

  selectCompte(compte: any) {
    this.virement.senderAccountId = compte.id;
    this.closeCompteModal();
  }

  submitVirement() {
    if (
      this.virement.senderAccountId &&
      this.virement.receiverAccountId &&
      this.virement.amount &&
      this.virement.amount > 0 &&
      this.virement.type
    ) {
      this.showRecapModal = true; // Ouvre la popup récap
      this.message = null;
    } else {
      this.message = "Veuillez remplir tous les champs correctement.";
    }
  }

  ouvrirRecapitulatif() {
    if (
      this.virement.senderAccountId &&
      this.virement.receiverAccountId &&
      this.virement.amount &&
      this.virement.amount > 0 &&
      this.virement.type
    ) {
      this.showRecapModal = true;
    } else {
      this.message = "Veuillez remplir tous les champs correctement.";
    }
  }

  modifier() {
    this.showRecapModal = false;
  }

  continuer() {
    this.showRecapModal = false;
    this.message = `✅ Virement de ${this.virement.amount}€ du compte ${this.virement.senderAccountId} vers le compte ${this.virement.receiverAccountId} de type ${this.virement.type} confirmé.`;
  }

  confirmRecap() {
    this.showRecapModal = false;
    this.showPasswordModal = true;
  }

  useFingerprint() {
    alert("Empreinte digitale non disponible dans cette démo.");
    this.finaliserVirement();
  }

  submitPassword() {
    //if (this.userPassword === this.provisionalPassword) {
    //  this.finaliserVirement();
   // } else {
   //   alert("Mot de passe incorrect.");
   // }
    this.finaliserVirement();
  }

  finaliserVirement() {
    this.showPasswordModal = false;

    const requestBody = {
      senderAccountId: this.virement.senderAccountId,
      receiverAccountId: this.virement.receiverAccountId,
      amount: this.virement.amount,
      password: this.userPassword,
      typeTransaction: this.virement.type
    };

    this.virementService.envoyerVirement(requestBody).subscribe({
      next: (res) => {
        this.message = res;
        alert('✅ ' + res);
      },
      error: (err) => {
        this.message = err.error;
        alert('❌ ' + err.error);
      }
    });
  }
}