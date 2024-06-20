import { Component, OnInit } from '@angular/core';
import { SpecialistService } from './service/specialist.service';
import { SpecialistResponseDTO } from './interfaces/meetingResponse.interface';

@Component({
  selector: 'app-specialist',
  templateUrl: './specialist.component.html',
  styleUrls: ['./specialist.component.css']
})
export class SpecialistComponent implements OnInit {
  
  lastFiveSpecialists: SpecialistResponseDTO[] = [];
  selectedSpecialistId: number = 0;

  constructor(private specialistService: SpecialistService) {}

  ngOnInit(): void {
    this.specialistService.getAllSpecialist().subscribe(
      specialists => {
        // Ordenar los especialistas por ID de manera descendente (asumiendo que specId es el ID)
        specialists.sort((a, b) => b.specId - a.specId);
        // Tomar los primeros 5 especialistas
        this.lastFiveSpecialists = specialists.slice(0, 5);
      },
      error => {
        console.error('Error fetching specialists', error);
      }
    );
  }  
  open(specialistId: number): void {
    //event.preventDefault();
    this.selectedSpecialistId = specialistId; 
    console.log('id:'+this.selectedSpecialistId);
    const meetingContainer: HTMLElement | null = document.querySelector('.container-meeting');
    if (meetingContainer) {
      meetingContainer.style.display = 'block';
      document.body.style.overflow = 'hidden';
    }
  }

}
