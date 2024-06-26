// register-customer.component.ts
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { SingupCustomerRequest } from '../interfaces/singup-customer-request';
import { SignUpCustomerService } from '../services/sign-up-customer.service';
@Component({
  selector: 'app-register-customer',
  templateUrl: './register-customer.component.html',
  styleUrls: ['./register-customer.component.css']
})
export class RegisterCustomerComponent {
    
    form: FormGroup;
    passwordVisible = false;

    constructor(
      private fb: FormBuilder,
      private router: Router,
      private signUpCustomerService :SignUpCustomerService
    ){
      this.form = this.fb.group({
        name: ['', [Validators.required]],
        phoneNumber: ['', [Validators.required]],
        age: ['',[Validators.required]],
        weight: ['',[Validators.required, Validators.max(300.5)]],
        height: ['', [Validators.required, Validators.max(2.10)]],
        alergies: ['', [Validators.required]],
        dietType: ['',[Validators.required]],
        dietRestriction: ['', Validators.required],
        email: ['', [Validators.required, Validators.email]],
        password: ['', [Validators.required, Validators.minLength(4)]],
      })
    }

    controlHasError(control: string, error: string) {
      return this.form.controls[control].hasError(error);
    }
    
    togglePasswordVisibility() {
      this.passwordVisible = !this.passwordVisible;
    }

    signup() {
      if (this.form.invalid) {
        this.form.markAllAsTouched();
        return;
      }
      
      const signUpData: SingupCustomerRequest = this.form.value as SingupCustomerRequest;
      signUpData.role = 'CUSTOMER';
      this.signUpCustomerService.signup(signUpData).subscribe({
        next: (response ) => {
          this.router.navigate(['login']);
        },
        error: (error) => {
          console.error('Error en el registro:', error);
        }
      });

    }
}
