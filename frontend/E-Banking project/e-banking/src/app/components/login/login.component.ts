import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  loginForm: FormGroup;
  errorMessage: string = '';
  isLoading: boolean = false;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {
    this.loginForm = this.fb.group({
      login: ['', [Validators.required, Validators.minLength(3)]],
      password: ['', [Validators.required, Validators.minLength(6)]]
    });
  }

  onSubmit(): void {
    if (this.loginForm.valid && !this.isLoading) {
      this.isLoading = true;
      this.errorMessage = '';

      const { login, password } = this.loginForm.value;

      this.authService.login(login, password).subscribe({
        next: () => {
          // Set token immediately after login success
          localStorage.setItem('auth_token', '111111');
          // Fetch user, then navigate
          this.authService.fetchCurrentUser().subscribe({
            next: (user) => {
              // 👉 Stocke le clientId dans le localStorage
              localStorage.setItem('clientId', user.clientId.toString());
              this.isLoading = false;
              this.router.navigate(['/dashboard']);
            },
            error: (err) => {
              this.handleLoginError(err);
              this.isLoading = false;
            }
          });
        },
        error: (err) => {
          this.handleLoginError(err);
          this.isLoading = false;
        }
      });
    }
  }

  private handleLoginError(err: any): void {
    this.errorMessage = 'Invalid login credentials';
    console.error('Login error:', err);
  }

  get login() {
    return this.loginForm.get('login');
  }

  get password() {
    return this.loginForm.get('password');
  }
}