import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Account {
  id: number;
  accountNumber: string;
  balance: number;
  accountType: string;
  creationDate: number[];
  // Ajoute d'autres champs si nécessaire
}

@Injectable({ providedIn: 'root' })
export class AccountService {
  private apiUrl = 'http://localhost:9097/E_BankingApp3_war_exploded/client';

  constructor(private http: HttpClient) { }

  getAccountsByClientId(clientId: number): Observable<Account[]> {
    return this.http.get<Account[]>(`${this.apiUrl}/${clientId}/accounts`);
  }
}