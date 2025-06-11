import { Component, OnInit } from '@angular/core';
import { AuthService } from '../services/auth.service';
import { TransactionService, Transaction } from '../services/transaction.service';
import { Client } from '../model/client.model';
import { CommonModule } from '@angular/common';


@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css'],
  standalone: true,
  imports: [CommonModule]
})
export class ProfileComponent implements OnInit {
  client: Client | null = null;
  transactions: Transaction[] = [];
  loading = true;
  error: string | null = null;

  constructor(
    private authService: AuthService,
    private transactionService: TransactionService
  ) {}

  ngOnInit(): void {
    this.client = this.authService.getCurrentClient();
    
    if (this.client && this.client.clientId) {
      this.transactionService.getTransactionsByClientId(this.client.clientId).subscribe({
        next: (data) => {
          this.transactions = data;
          this.loading = false;
        },
        error: (err) => {
          this.error = "Erreur lors de la récupération des transactions.";
          this.loading = false;
        }
      });
    } else {
      this.error = "Aucun client connecté.";
      this.loading = false;
    }
  }
}