import { Component, Inject } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { Client } from '../../model/client.model';
@Component({
  selector: 'app-client',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './client.component.html',
  styleUrls: ['./client.component.css']
})
export class ClientComponent {
  
currentClient: Client | null = null; // Initialisation à null

  constructor(private authService: AuthService) {}

  ngOnInit(): void {
    // Récupération du client après l'initialisation
    this.currentClient = this.authService.getCurrentClient();
  }
  

  logout(): void {
    this.authService.logout().subscribe(() => {
      // Redirection gérée dans le AuthService
    });
  }
}