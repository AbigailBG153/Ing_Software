import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

export const authGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  // Verificar si el usuario está autenticado
  if (authService.auth()?.user.role==='CUSTOMER' || authService.auth()?.user.role==='SPECIALIST') {
    return true; // Usuario autenticado, permitir acceso a la ruta
  }

  // Usuario no autenticado, redirigir al componente de inicio de sesión
  router.navigate(['']);
  return false;
};
