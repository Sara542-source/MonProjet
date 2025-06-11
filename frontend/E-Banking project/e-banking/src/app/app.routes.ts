import { Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { ClientComponent } from './components/client/client.component';
import { AuthGuard } from './guards/auth.guard';
import { DashboardComponent } from './dashboard/dashboard.component';
import { StatsComponent } from './stats/stats.component';
import { ProfileComponent } from './profile/profile.component';
import { VirementComponent } from './virement/virement.component';
import { HomeComponent } from './home/home.component';


// Exportez le tableau routes avec 'export const'
export const routes: Routes = [
{ path: 'login', component: LoginComponent },
  {
    path: 'dashboard',
    component: DashboardComponent,
    canActivate: [AuthGuard], // Only allow access if authenticated
    children: [
      { path: 'stats', component: StatsComponent },
      { path: 'profile', component: ProfileComponent },
      { path: 'virement', component: VirementComponent },
      { path: 'home', component: HomeComponent  },
      { path: '', component: HomeComponent  }

    ]
  },
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: '**', redirectTo: '/login' }
];