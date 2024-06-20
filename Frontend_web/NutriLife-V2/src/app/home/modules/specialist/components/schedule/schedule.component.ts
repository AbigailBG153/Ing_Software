import { Component, Input, OnChanges, SimpleChanges } from '@angular/core';
import { ScheduleResponseDTO , MeetingResponseDTO } from '../../interfaces/meetingResponse.interface';
import { ScheduleService } from '../../service/schedule.service';
import { MeetingService } from '../../service/meeting.service';

@Component({
    selector: 'app-schedule',
    templateUrl: './schedule.component.html',
    styleUrls: ['./schedule.component.css'] // Asegúrate de que el nombre del archivo CSS sea correcto
})
export class ScheduleComponent implements OnChanges {
    scheduleByDayAndHour: { [day: string]: { [hour: string]: ScheduleResponseDTO } } = {};
    uniqueDays: string[] = [];
    uniqueDate: string[] = [];
    hours: string[] = [];
    errorMessage: string = '';
    selectedSchedule: ScheduleResponseDTO | null = null;
    customerId = 1 ;
    @Input() specialistId: number = 0;

    constructor(private scheduleService: ScheduleService , private meetingService : MeetingService) {}

    ngOnChanges(changes: SimpleChanges): void {
        if (changes['specialistId'] && !changes['specialistId'].firstChange) {
            console.log('Specialist ID recibido en ScheduleComponent:', this.specialistId);
            if (this.specialistId !== 0) {
                this.scheduleService.getDailySchedulesForCurrentWeek(this.specialistId).subscribe(
                    data => {
                        this.organizeSchedules(data);
                        this.errorMessage = ''; // Reiniciar el mensaje de error si se cargan horarios correctamente
                    },
                    error => {
                        console.error('Error en la solicitud HTTP:', error);
                        this.errorMessage = 'No hay horarios disponibles para esta semana';
                    }
                );
            }
        }
    }

    private organizeSchedules(schedules: ScheduleResponseDTO[]): void {
        this.uniqueDays = Array.from(new Set(schedules.map(schedule => schedule.dayOfWeek)));
        this.uniqueDate = Array.from(new Set(schedules.map(schedule => schedule.date)));
        this.hours = this.generateHoursArray();

        this.uniqueDays.forEach(day => {
            this.scheduleByDayAndHour[day] = {};

            schedules
                .filter(schedule => schedule.dayOfWeek === day)
                .sort((a, b) => a.startTime.localeCompare(b.startTime))
                .forEach(schedule => {
                    const startHour = parseInt(schedule.startTime.slice(0, 2));
                    const endHour = parseInt(schedule.endTime.slice(0, 2));

                    for (let i = startHour; i < endHour; i++) {
                        const hour = `${i.toString().padStart(2, '0')}:00`;
                        this.scheduleByDayAndHour[day][hour] = schedule;
                    }
                });
        });
    }

    getDayFromDate(fullDate: string): string {
        return fullDate.split('-')[2];
    }

    getShortDayOfWeek(longDay: string): string {
        const daysMap: { [key: string]: string } = {
            'MONDAY': 'Lun',
            'TUESDAY': 'Mar',
            'WEDNESDAY': 'Mié',
            'THURSDAY': 'Jue',
            'FRIDAY': 'Vie',
            'SATURDAY': 'Sáb',
            'SUNDAY': 'Dom'
        };

        return daysMap[longDay.toUpperCase()] || '';
    }

    generateHoursArray(): string[] {
        const hoursArray: string[] = [];
        for (let i = 9; i <= 21; i++) {
            const hour = `${i.toString().padStart(2, '0')}:00`;
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
            } else if (schedule.status === 'ACTIVE') {
                return 'status-active';
            }
        }
        return '';
    }

    getNextHour(hour: string): string {
        const currentHour = parseInt(hour.slice(0, 2));
        const nextHour = (currentHour + 1).toString().padStart(2, '0');
        return `${nextHour}:00`;
    }

    selectCell(day: string, hour: string): void {
        const scheduleInfo = this.scheduleByDayAndHour[day]?.[hour];
        const meetingContainer: HTMLElement | null = document.querySelector('.modal-meeting-content');
        if (scheduleInfo && scheduleInfo.status === 'ACTIVE') {
            this.selectedSchedule = scheduleInfo;
            console.log('id : ' + scheduleInfo.scheduleId)
            if (meetingContainer) {
              meetingContainer.style.display = 'block';
              document.body.style.overflow = 'auto';
            }
        }
    }

    cancelMeeting(): void {
        this.selectedSchedule = null;
    }

    confirmMeeting(): void {
      if (this.selectedSchedule && this.customerId) {
        this.meetingService.createMeeting(this.selectedSchedule.scheduleId, this.customerId)
            .subscribe(
                (response: MeetingResponseDTO) => {
                    console.log('Reunión confirmada:', response);
                    this.selectedSchedule = null;
                    alert('Reunión confirmada');
                    this.refreshSchedule();
                },
                error => {
                    console.error('Error al crear la reunión:', error);
                    alert('Hubo un error al crear la reunión');
                }
            );
      }
    
    }

    private refreshSchedule(): void {
      if (this.specialistId !== 0) {
          this.scheduleService.getDailySchedulesForCurrentWeek(this.specialistId).subscribe(
              data => {
                  this.organizeSchedules(data);
                  this.errorMessage = ''; // Reiniciar el mensaje de error si se cargan horarios correctamente
              },
              error => {
                  console.error('Error en la solicitud HTTP:', error);
                  this.errorMessage = 'No hay horarios disponibles para esta semana';
              }
          );
      }
  }
}
