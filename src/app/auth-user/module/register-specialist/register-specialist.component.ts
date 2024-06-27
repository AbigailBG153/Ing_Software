import { Component ,ViewEncapsulation} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { SingUpSpecialistService } from '../services/sing-up-specialist.service';
import { SingupSpecialistRequest } from '../interfaces/singup-specialist-request';
@Component({
  selector: 'app-register-specialist',
  templateUrl: './register-specialist.component.html',
  styleUrl: './register-specialist.component.css',
  encapsulation: ViewEncapsulation.None

})
export class RegisterSpecialistComponent {
  form: FormGroup;
    passwordVisible = false;

    constructor(
      private fb: FormBuilder,
      private router: Router,
      private singUpSpecialistService: SingUpSpecialistService
    ){
      this.form = this.fb.group({
        name: ['', [Validators.required]],
        phoneNumber: ['', [Validators.required]],
        ocupation: ['', [Validators.required]],
        age: ['',[Validators.required]],
        score: ['',[Validators.required, Validators.max(5)]],
        email: ['', [Validators.required, Validators.email]],
        password: ['', [Validators.required, Validators.minLength(4)]],
      })
    }

    controlHasError(control: string, error: string) {
      return this.form.controls[control].hasError(error);
    }
    
    signup() {
      if (this.form.invalid) {
        this.form.markAllAsTouched();
      }
      
      const signUpData: SingupSpecialistRequest = this.form.value as SingupSpecialistRequest;
      signUpData.role = 'SPECIALIST';
      signUpData.score = 4;
      this.singUpSpecialistService.signup(signUpData).subscribe({
        next: (response ) => {
          this.router.navigate(['home']);
        },
        error: (error) => {
          console.error('Error en el registro:', error);
        }
      });
    }
}
