import { Component, OnInit } from '@angular/core';
import { AccountService, Account } from '../services/account.service';
import { AuthService } from '../services/auth.service';
import { CommonModule } from '@angular/common'; 

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
  standalone: true,
imports: [CommonModule]
})
export class HomeComponent implements OnInit {
  accounts: Account[] = [];
  errorMessage = '';

  constructor(
    private accountService: AccountService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    const client = this.authService.getCurrentClient();
    if (client && client.clientId) {
      this.accountService.getAccountsByClientId(client.clientId).subscribe({
        next: (accounts) => this.accounts = accounts,
        error: (err) => this.errorMessage = 'Erreur lors du chargement des comptes'
      });
    } else {
      this.errorMessage = "Impossible de récupérer l'utilisateur connecté";
    }
  }
}