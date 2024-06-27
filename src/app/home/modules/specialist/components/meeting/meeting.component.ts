import { Component, Input, OnChanges, SimpleChanges, input } from '@angular/core';
import { SpecialistService } from '../../service/specialist.service';
import { SpecialistResponseDTO } from '../../interfaces/meetingResponse.interface';

@Component({
  selector: 'app-meeting',
  templateUrl: './meeting.component.html',
  styleUrl: './meeting.component.css'
})
export class MeetingComponent implements OnChanges{

  // Inicialmente ningún especialista seleccionado
  constructor(private specialistService: SpecialistService) {}
  
  @Input() isSpecialistId:number = 0;
  specialistProfile: SpecialistResponseDTO | null = null;
  ngOnChanges(changes: SimpleChanges): void{
    if (changes['isSpecialistId'] && !changes['isSpecialistId'].firstChange) {
      console.log('Specialist ID recibido en ScheduleComponent:', this.isSpecialistId);
      if (this.isSpecialistId !== 0) {
        this.specialistService.getSpecialistProfileById(this.isSpecialistId)
        .subscribe(
          (data: SpecialistResponseDTO) => {
            this.specialistProfile = data;
            console.log('Perfil del especialista:', this.specialistProfile);
          },
          error => {
            console.error('Error al obtener el perfil del especialista:', error);
            // Manejo de errores aquí
          }
        );
      }
    } 
  }
  close(event: Event): void {
    event.preventDefault();
    const meetingContainer: HTMLElement | null = document.querySelector('.container-meeting');
    if (meetingContainer) {
      meetingContainer.style.display = 'none';
      document.body.style.overflow = 'auto';
    }
  }
  getSpecialistProfile(): void {
    this.specialistService.getSpecialistProfileById(this.isSpecialistId)
      .subscribe(
        (data: SpecialistResponseDTO) => {
          this.specialistProfile = data;
          console.log('Perfil del especialista:', this.specialistProfile);
        },
        error => {
          console.error('Error al obtener el perfil del especialista:', error);
          // Manejo de errores aquí
        }
      );
  }
  

}
