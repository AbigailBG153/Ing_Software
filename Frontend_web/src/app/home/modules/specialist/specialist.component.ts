import { Component, OnInit } from '@angular/core';
import { SpecialistService } from './service/specialist.service';
import { SpecialistResponseDTO } from './interfaces/meetingResponse.interface';

@Component({
    selector: 'app-specialist',
    templateUrl: './specialist.component.html',
    styleUrls: ['./specialist.component.css']
})
export class SpecialistComponent implements OnInit {
  
    allSpecialists: SpecialistResponseDTO[] = [];
    filteredSpecialists: SpecialistResponseDTO[] = [];
    filterVisible: boolean = false;
    selectedSpecialistId: number = 0;
    inputAge: number=0;
  
    constructor(private specialistService: SpecialistService) {}
  
    ngOnInit() {
      this.getAllSpecialists();
    }
  
    getAllSpecialists() {
      this.specialistService.getAllSpecialist().subscribe(
        (specialists: SpecialistResponseDTO[]) => {
          this.allSpecialists = specialists;
          this.filteredSpecialists = specialists; // Mostrar todos los especialistas por defecto
        },
        (error) => {
          console.error('Error al obtener todos los especialistas:', error);
        }
      );
    }
  
    applyFilterAndCloseModal(filterType: string, ...args: any[]): void {
        switch (filterType) {
          case 'occupation':
            const occupation = args[0];
            this.specialistService.getSpecialistProfilesByOccupation(occupation).subscribe(
              (specialists: SpecialistResponseDTO[]) => {
                this.filteredSpecialists = specialists;
              },
              (error) => {
                console.error('Error al filtrar por ocupación:', error);
              }
            );
            break;
          case 'age':
            const age = args[0];
            this.specialistService.getSpecialistProfilesByAge(age).subscribe(
              (specialists: SpecialistResponseDTO[]) => {
                this.filteredSpecialists = specialists;
              },
              (error) => {
                console.error('Error al filtrar por edad:', error);
              }
            );
            break;
          case 'ageRange':
            const minAge = args[0];
            const maxAge = args[1];
            this.specialistService.getSpecialistProfilesByAgeRange(minAge, maxAge).subscribe(
              (specialists: SpecialistResponseDTO[]) => {
                this.filteredSpecialists = specialists;
              },
              (error) => {
                console.error('Error al filtrar por rango de edad:', error);
              }
            );
            break;
          case 'score':
            const score = args[0];
            this.specialistService.getSpecialistProfilesByScore(score).subscribe(
              (specialists: SpecialistResponseDTO[]) => {
                this.filteredSpecialists = specialists;
              },
              (error) => {
                console.error('Error al filtrar por score:', error);
              }
            );
            break;
          default:
            break;
        }
        this.filterVisible = false; // Cierra el modal después de aplicar el filtro
    }
    
    toggleFilter(): void {
        this.filterVisible = !this.filterVisible;
    }
    
    clearFilters(): void {
        this.filteredSpecialists = this.allSpecialists; // Restablece los especialistas filtrados
        this.toggleFilter(); // Cierra el modal de filtro
    }

    getInputValue(inputId: string): number | null {
        const inputValue = (document.getElementById(inputId) as HTMLInputElement)?.value;
        if (inputValue) {
          return parseInt(inputValue, 10);
        }
        return null;
    }
    
  
    // Generar estrellas basadas en la puntuación
  generateStars(score: number): string[] {
    const fullStarsCount = Math.floor(score); // Estrellas llenas
    const decimal = score - fullStarsCount; // Parte decimal para la estrella parcial

    const stars = [];
    for (let i = 1; i <= 5; i++) {
      if (i <= fullStarsCount) {
        stars.push('full'); // Agrega una estrella llena
      } else if (i === Math.ceil(score) && decimal >= 0.5) {
        stars.push('half'); // Si la puntuación termina con 0.5 o más, completa la estrella
      } else {
        stars.push('empty'); // Agrega una estrella vacía
      }
    }
    return stars;
  }
  
    open(specialistId: number): void {
      this.selectedSpecialistId = specialistId; 
      console.log('id:' + this.selectedSpecialistId);
      const meetingContainer: HTMLElement | null = document.querySelector('.container-meeting');
      if (meetingContainer) {
        meetingContainer.style.display = 'block';
        document.body.style.overflow = 'hidden';
      }
    }
  
}