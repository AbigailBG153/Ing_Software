import { Component, OnInit } from '@angular/core';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { TrainingService } from './service/training.service';
import { TrainingResponseDTO } from './interfaces/training.interface';

@Component({
  selector: 'app-training',
  templateUrl: './training.component.html',
  styleUrls: ['./training.component.css']
})
export class TrainingComponent implements OnInit {

  exercises: TrainingResponseDTO[] = [];
  topRatedExercises: TrainingResponseDTO[] = [];
  weekDays: string[] = ['Lunes', 'Martes', 'Miércoles', 'Jueves', 'Viernes', 'Sábado', 'Domingo'];
  selectedDay: string = '';
  selectedExercise: TrainingResponseDTO | null = null;
  exercisesTitle: string = 'Ejercicios';
  showFilterMenu: boolean = false;
  kcalOptions: number[] = [50, 100, 150, 200, 250, 300, 350, 400, 450, 500, 550];
  durationOptions: number[] = [15, 20, 30, 45, 60];

  constructor(private trainingService: TrainingService, private sanitizer: DomSanitizer) { }

  ngOnInit(): void {
    this.loadInitialExercises();
  }

  loadInitialExercises(): void {
    this.trainingService.getAllTrainings().subscribe(data => {
      this.exercises = data.slice(0, 5); // Mostrar solo los primeros 5 ejercicios
      this.topRatedExercises = data.sort((a, b) => b.qualification - a.qualification).slice(0, 5); // Los 5 ejercicios con más calificación
    });
  }

  chooseExercise(exercise: TrainingResponseDTO): void {
    this.selectedExercise = exercise;
  }

  selectDay(day: string): void {
    this.selectedDay = day;
    this.exercisesTitle = `Ejercicios del ${day}`;
    this.trainingService.getAllTrainings().subscribe(data => {
      this.exercises = this.getRandomExercises(data, 4);
    });
  }

  getRandomExercises(exercises: TrainingResponseDTO[], count: number): TrainingResponseDTO[] {
    const shuffled = exercises.sort(() => 0.5 - Math.random());
    return shuffled.slice(0, count);
  }

  toggleFilterMenu(): void {
    this.showFilterMenu = !this.showFilterMenu;
  }

  filterByType(type: string): void {
    this.trainingService.getTrainingsByType(type).subscribe(data => {
      this.exercises = data;
      this.exercisesTitle = `Ejercicios de tipo ${type}`;
    });
  }

  filterByKcal(kcal: number): void {
    this.trainingService.getTrainingsByKcal(kcal).subscribe(data => {
      this.exercises = data;
      this.exercisesTitle = `Ejercicios de ${kcal} Kcal`;
    });
  }

  filterByGoal(goal: string): void {
    this.trainingService.getTrainingsByGoal(goal).subscribe(data => {
      this.exercises = data;
      this.exercisesTitle = `Ejercicios para ${goal}`;
    });
  }

  filterByDuration(duration: number): void {
    this.trainingService.getTrainingsByDuration(duration).subscribe(data => {
      this.exercises = data;
      this.exercisesTitle = `Ejercicios de ${duration} mins`;
    });
  }

  closeExercise(): void {
    this.selectedExercise = null;
  }

  getSanitizedUrl(video: string): SafeResourceUrl {
    return this.sanitizer.bypassSecurityTrustResourceUrl(video);
  }

  getImageForType(type: string): string {
    switch (type) {
      case 'Cardio':
        return 'IMG/Entrenamiento/Cardio.jpeg';
      case 'Fuerza':
        return 'IMG/Entrenamiento/Fuerza.jpeg';
      case 'Flexibilidad':
        return 'IMG/Entrenamiento/Flexibilidad.png';
      default:
        return 'assets/images/default.jpg';
    }
  }
}
