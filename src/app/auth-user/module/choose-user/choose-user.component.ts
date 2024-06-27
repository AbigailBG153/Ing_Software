import { Component } from '@angular/core';
import { Router } from '@angular/router';
@Component({
  selector: 'app-choose-user',
  templateUrl: './choose-user.component.html',
  styleUrl: './choose-user.component.css'
})
export class ChooseUserComponent {
  isRegisterActive = false;

  toggleRegister(isRegister: boolean) {
    this.isRegisterActive = isRegister;
  }
}
