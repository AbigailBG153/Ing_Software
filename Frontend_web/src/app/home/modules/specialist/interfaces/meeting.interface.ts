export interface ScheduleResponseDTO {
    scheduleId: number;
    dayOfWeek: string;
    date: string;
    startTime: string;
    endTime: string;
    status: string;
    meeting: any;
}
