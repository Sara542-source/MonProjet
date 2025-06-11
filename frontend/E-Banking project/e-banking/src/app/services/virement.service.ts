import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class VirementService {

  private apiUrl = 'http://localhost:9097/E_BankingApp3_war_exploded/transactions/virement';

  constructor(private http: HttpClient) {}

  envoyerVirement(data: any): Observable<any> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json'
    });

    return this.http.post(this.apiUrl, data, { headers, responseType: 'text' });
  }

  getAccountsByClientId(clientId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/${clientId}/accounts`);
  }
}
