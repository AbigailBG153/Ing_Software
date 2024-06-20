import { MeetingService } from './../../../specialist/service/meeting.service';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { forkJoin, Subscription } from 'rxjs';
import { MeetingResponseDTO, SpecialistResponseDTO } from './../../../specialist/interfaces/meetingResponse.interface';
import { SpecialistService } from './../../../specialist/service/specialist.service';
import { MeetingRequestDTO } from './../../../specialist/interfaces/meetingRequest.interface';

@Component({
  selector: 'app-list-meeting',
  templateUrl: './list-meeting.component.html',
  styleUrl: './list-meeting.component.css'
})
export class ListMeetingComponent  implements OnInit, OnDestroy {
  meetings: MeetingResponseDTO[] = [];
  specialistResponseDTOs: SpecialistResponseDTO[] = [];
  private updateInterval: any;
  private intervalTime = 30000; // Intervalo de actualización en milisegundos (30 segundos)
  activeDropdownIndex: number | null = null;
  constructor(private meetingService: MeetingService, private specialistService: SpecialistService) { }

  ngOnInit(): void {
    this.loadMeetings();
    this.startMeetingStatusUpdater();

  }

  ngOnDestroy(): void {
    this.stopMeetingStatusUpdater();
  }

  loadMeetings() {
    const customerId = 1; // Reemplaza con el ID del cliente deseado
    this.meetingService.getMeetingsByCustomerId(customerId)
      .subscribe(
        meetings => {
          console.log('Meetings loaded:', meetings.length);
          this.processMeetings(meetings);
        },
        error => {
          console.error('Error loading meetings:', error);
          // Manejo de errores aquí
        }
      );
  }

  processMeetings(meetings: MeetingResponseDTO[]) {
    // Filtrar y mapear las reuniones válidas
    const validMeetings = meetings.filter(meeting => meeting.specialistId !== null && meeting.specialistId !== undefined);
  
    // Ordenar reuniones por fecha y hora (más reciente primero)
    validMeetings.sort((a, b) => {
      // Primero por fecha (más reciente primero)
      if (a.date > b.date) return -1;
      if (a.date < b.date) return 1;
      // Luego por hora de inicio
      if (a.startTime > b.startTime) return -1;
      if (a.startTime < b.startTime) return 1;
      return 0;
    });
  
    this.meetings = validMeetings; // Asignar las reuniones ordenadas al arreglo de reuniones
  
    // Obtener observables para cada solicitud de perfil de especialista
    const requests = validMeetings.map(meeting => this.specialistService.getSpecialistProfileById(meeting.specialistId!));
  
    // Realizar todas las solicitudes en paralelo
    forkJoin(requests)
      .subscribe(
        (responses: SpecialistResponseDTO[]) => {
          this.specialistResponseDTOs = responses;
          console.log('Perfiles de especialistas cargados:', this.specialistResponseDTOs);
        },
        error => {
          console.error('Error al obtener los perfiles de los especialistas:', error);
          // Manejo de errores aquí
        }
      );
  }

  startMeetingStatusUpdater() {
    this.updateInterval = setInterval(() => {
      console.log(`Checking meetings status...`);
      this.updateMeetingStatuses();
    }, this.intervalTime);
  }

  stopMeetingStatusUpdater() {
    if (this.updateInterval) {
      clearInterval(this.updateInterval);
      this.updateInterval = null;
    }
  }

  updateMeetingStatuses() {
    const now = new Date();
  
    // Filtrar las reuniones que tienen estado 'PENDING' y han sido cargadas y están almacenadas en this.meetings
    const pendingMeetingsToUpdate = this.meetings.filter(meeting => meeting.specialistId !== null && meeting.specialistId !== undefined && meeting.meetStatus === 'PENDING' && new Date(`${meeting.date}T${meeting.endTime}`) < now);

    if (pendingMeetingsToUpdate.length > 0) {
      console.log(`Meetings with status PENDING and passed end time found. IDs:`, pendingMeetingsToUpdate.map(meeting => meeting.meetingId));

      // Recopilar IDs de reuniones para actualizar
      const meetingIdsToUpdate = pendingMeetingsToUpdate.map(meeting => meeting.meetingId);

      // Función para actualizar una reunión y devolver el observable
      const updateMeeting = (meetingId: number) => {
        const updatedMeeting: MeetingRequestDTO = { status: 'NO_ASIST' };
        return this.meetingService.updateMeeting(meetingId, updatedMeeting);
      };

      // Actualizar las reuniones en secuencia
      const updateSequence = meetingIdsToUpdate.reduce((chain, meetingId) => {
        return chain.then(() => updateMeeting(meetingId).toPromise())
                    .then(response => {
                      console.log(`Meeting ID ${meetingId} updated successfully.`);
                      // Aquí podrías realizar actualizaciones locales si es necesario
                    })
                    .catch(error => {
                      console.error(`Error updating meeting ID ${meetingId}:`, error);
                      // Manejo de errores aquí
                    });
      }, Promise.resolve());

      // Después de actualizar todas, reiniciar el intervalo
      updateSequence.then(() => {
        console.log('All meetings updated successfully.');
        this.loadMeetings(); // Cargar reuniones nuevamente después de actualizar
      });
    } else {
      // Si no hay reuniones pendientes para actualizar, simplemente reiniciar el intervalo
      console.log('No pending meetings to update.');
    }
  }
  
  getStatusClass(status: String | undefined | null): String {
    if (!status) {
      return '';
    }
    switch (status.toUpperCase()) {
      case 'PENDING':
        return 'pending';
      case 'COMPLETED':
        return 'completed';
      case 'NO_ASSIST':
        return 'no_asist';
      default:
        return '';
    }
  }

  toggleDropdown(index: number): void {
    if (this.activeDropdownIndex === index) {
      this.activeDropdownIndex = null; // Cierra el dropdown si ya está abierto
    } else {
      this.activeDropdownIndex = index; // Abre el dropdown correspondiente al índice
    }
  }

  onActionSelectChange(action: string): void {
    // Implementa la lógica para la acción seleccionada (por ejemplo, eliminar)
    if (action === 'delete') {
      // Lógica para eliminar la reunión o realizar otra acción necesaria
    }
  }

}
