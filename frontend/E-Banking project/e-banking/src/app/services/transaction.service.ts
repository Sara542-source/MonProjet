import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Transaction {
  transactionId: number;
  amount: number;
  type: string;
  date: string | number[]; // à adapter selon le format réel
  senderAccount: any; // typage à adapter
  receiverAccount: any;
  typeTransaction: string;
}

@Injectable({ providedIn: 'root' })
export class TransactionService {
  private apiUrl = 'http://localhost:9097/E_BankingApp3_war_exploded/client';

  constructor(private http: HttpClient) {}

  getTransactionsByClientId(clientId: number): Observable<Transaction[]> {
    return this.http.get<Transaction[]>(`${this.apiUrl}/${clientId}/transactions`);
  }
}