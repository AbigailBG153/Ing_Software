import { Router } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../services/auth.service';
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  form: FormGroup;

  constructor(
    private authService: AuthService,
    private fb: FormBuilder,
    private router: Router,
  ) {
    this.form = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required],
    });
  }
  ngOnInit(): void {}

  login(): void {
    if (this.form.invalid) {
      return;
    }

    const credentials={
      email: this.form.value.email,
      password: this.form.value.password
    }

    this.authService.login(credentials).subscribe({
      next: (response) => {       
        this.router.navigate(['/home']); // Redirigir a la página de inicio
      },
      
      error: (err) => {
        console.error('Error en el inicio de sesión:', err.message);
      },
    });
  }

  controlHasError(control: string, error: string) {
    return this.form.controls[control].hasError(error);
  }

  navigateToRegister() {
    this.router.navigate(['/choose-user'])
  }
}
