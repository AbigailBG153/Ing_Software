import { Component, OnInit } from '@angular/core';
import { SpecialistResponseDTO } from '../../../specialist/interfaces/meetingResponse.interface';
import { SpecialistService } from '../../../specialist/service/specialist.service';

@Component({
  selector: 'app-list-top-specialits',
  templateUrl: './list-top-specialits.component.html',
  styleUrls: ['./list-top-specialits.component.css']
})
export class ListTopSpecialitsComponent implements OnInit {
  topNutricionistas: SpecialistResponseDTO[] = [];
  topEntrenadores: SpecialistResponseDTO[] = [];
  selectedSpecialistId: number = 0;

  constructor(private specialistService: SpecialistService) {}

  ngOnInit(): void {
    // Obtener los nutricionistas y entrenadores con sus puntuaciones
    this.specialistService.getSpecialistsByOccupation('NUTRICIONISTA').subscribe((data: SpecialistResponseDTO[]) => {
      // Ordenar por score de mayor a menor
      data.sort((a, b) => (b.score || 0) - (a.score || 0));
      // Tomar los primeros 3
      this.topNutricionistas = data.slice(0, 3);
      console.log('Perfil del nutricionista:', this.topNutricionistas);
    });

    this.specialistService.getSpecialistsByOccupation('ENTRENADOR').subscribe((data: SpecialistResponseDTO[]) => {
      // Ordenar por score de mayor a menor
      data.sort((a, b) => (b.score || 0) - (a.score || 0));
      // Tomar los primeros 3
      this.topEntrenadores = data.slice(0, 3);
      console.log('Perfil del entrenador:', this.topEntrenadores);
    });
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
}
