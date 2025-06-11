import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable ,tap, switchMap, of } from 'rxjs';
import { Client } from '../model/client.model';
import { Router } from '@angular/router';
import { throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:9097/E_BankingApp3_war_exploded/auth';
  private currentClient: Client | null = null;

  constructor(private http: HttpClient, private router: Router) { }
  login(login: string, password: string): Observable<any> {
  return this.http.post(`${this.apiUrl}/login`, null, {
    params: { login, password },
    withCredentials: true,
    observe: 'response'
  }).pipe(
    switchMap(response => {
      if (response.status === 200) {
        // Optional: fetch current user after login for local storage/update
        return this.fetchCurrentUser();
      } else {
        return throwError(() => new Error('Login failed.'));
      }
    }),
    catchError(err => {
      alert(err.message || 'Une erreur est survenue.');
      return throwError(() => err);
    })
  );
}


 // remove comments to make login with verification of sms ( u can send sms by also removing comments in the authController's login() in the backend anf fill in the auth field in Twilioo)

 
  /*
  login(login: string, password: string): Observable<any> {
  return this.http.post(`${this.apiUrl}/login`, null, {
    params: { login, password },
    withCredentials: true,
    observe: 'response'
  }).pipe(uth
    switchMap(response => {
      if (response.status === 200) {
        const code = window.prompt('Enter the code sent to you:');
        if (!code) {
          return throwError(() => new Error('You must enter the code to continue.'));
        }
        return this.fetchCurrentUser().pipe(
          switchMap(client => {
            if (!client || !client.clientId) {
              return throwError(() => new Error('Unable to retrieve your user ID for 2FA.'));
            }
            return this.http.post(
              `http://localhost:9097/E_BankingApp3_war_exploded/client/authenticate/${client.clientId}`,
              null,
              { params: { code }, withCredentials: true, observe: 'response' }
            ).pipe(
              switchMap(authResp => {
                if (authResp.status === 200) {
                  return this.fetchCurrentUser();
                }
                return throwError(() => new Error('Code incorrect. Please try again.'));
              })
            );
          })
        );
      } else {
        return throwError(() => new Error('Login failed.'));
      }
    }),
    catchError(err => {
      alert(err.message || 'Une erreur est survenue.');
      return throwError(() => err);
    })
  );
}*/

  fetchCurrentUser(): Observable<Client> {
    return this.http.get<Client>(`${this.apiUrl}/current-user`, {
      withCredentials: true
    }).pipe(
      tap(client => {
        this.currentClient = client;
        localStorage.setItem('currentClient', JSON.stringify(client));
      })
    );
  }

  logout(): Observable<any> {
    return this.http.post(`${this.apiUrl}/logout`, null, {
      withCredentials: true
    }).pipe(
      tap(() => {
        this.clearAuthData();
        this.router.navigate(['/login']);
      })
    );
  }

  private clearAuthData(): void {
    this.currentClient = null;
    localStorage.removeItem('currentClient');
  }

  getCurrentClient(): Client | null {
  if (!this.currentClient) {
    if (typeof window !== 'undefined' && localStorage) { // <-- Vérification ici
      const storedClient = localStorage.getItem('currentClient');
      if (storedClient) {
        this.currentClient = JSON.parse(storedClient);
      }
    }
  }
  return this.currentClient;
}

  isAuthenticated(): Observable<boolean> {
    return new Observable(observer => {
      this.http.get(`${this.apiUrl}/current-user`, {
        withCredentials: true
      }).subscribe({
        next: (client: any) => {
          this.currentClient = client;
          localStorage.setItem('currentClient', JSON.stringify(client));
          observer.next(true);
          observer.complete();
        },
        error: () => {
          this.clearAuthData();
          observer.next(false);
          observer.complete();
        }
      });
    });
  }
}