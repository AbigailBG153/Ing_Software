import { Component, OnInit } from '@angular/core';
import { ScheduleService } from './service/schedule.service';
import { ScheduleResponseDTO } from './interfaces/meeting.interface';

@Component({
  selector: 'app-specialist',
  templateUrl: './specialist.component.html',
  styleUrls: ['./specialist.component.css']
})
export class SpecialistComponent implements OnInit {

  scheduleByDayAndHour: { [day: string]: { [hour: string]: ScheduleResponseDTO } } = {};
  uniqueDays: string[] = [];
  hours: string[] = [];

  specialistId: number = 22;

  constructor(private scheduleService: ScheduleService) { }
   
  ngOnInit(): void {
    this.scheduleService.getDailySchedulesForCurrentWeek(this.specialistId).subscribe(data => {
      this.organizeSchedules(data);
    });
  }

  private organizeSchedules(schedules: ScheduleResponseDTO[]): void {
    // Obtener días únicos de la semana y generar array de horas
    this.uniqueDays = Array.from(new Set(schedules.map(schedule => schedule.dayOfWeek)));
    this.hours = this.generateHoursArray();

    // Inicializar scheduleByDayAndHour con horarios vacíos
    this.uniqueDays.forEach(day => {
      this.scheduleByDayAndHour[day] = {};

      // Ordenar horarios por hora de inicio para facilitar la lógica de intervalos
      schedules.filter(schedule => schedule.dayOfWeek === day).sort((a, b) => a.startTime.localeCompare(b.startTime)).forEach(schedule => {
        const startHour = parseInt(schedule.startTime.slice(0, 2)); // Obtener hora de inicio
        const endHour = parseInt(schedule.endTime.slice(0, 2)); // Obtener hora de fin

        // Iterar sobre el rango de horas y asignar el horario a cada hora en ese rango
        for (let i = startHour; i < endHour; i++) {
          const hour = `${i.toString().padStart(2, '0')}:00:00`;
          this.scheduleByDayAndHour[day][hour] = schedule;
        }
      });
    });
  }

  generateHoursArray(): string[] {
    const hoursArray: string[] = [];
    for (let i = 9; i <= 21; i++) {
      const hour = `${i.toString().padStart(2, '0')}:00:00`;
      hoursArray.push(hour);
    }
    return hoursArray;
  }

  getCellStyle(day: string, hour: string): string {
    if (this.scheduleByDayAndHour[day] && this.scheduleByDayAndHour[day][hour]) {
      const schedule = this.scheduleByDayAndHour[day][hour];
      if (schedule.status === 'DISABLED') {
        return 'status-disabled';
      } else if (schedule.status === 'OCCUPIED') {
        return 'status-occupied';
      }
    }
    return ''; // Si no se encuentra un horario, retornar clase vacía
  }

  open(event: Event): void {
    event.preventDefault();
    const meetingContainer: HTMLElement | null = document.querySelector('.container-meeting');
    if (meetingContainer) {
      meetingContainer.classList.add('open-meeting');
      meetingContainer.style.display = 'flex';
    }
  }

  close(event: Event): void {
    event.preventDefault();
    const meetingContainer: HTMLElement | null = document.querySelector('.container-meeting');
    if (meetingContainer) {
      meetingContainer.classList.remove('open-meeting');
      meetingContainer.style.display = 'none';
    }
  }

  getNextHour(hour: string): string {
    const currentHour = parseInt(hour.slice(0, 2));
    const nextHour = (currentHour + 1).toString().padStart(2, '0');
    return `${nextHour}:00:00`;
  }
}
