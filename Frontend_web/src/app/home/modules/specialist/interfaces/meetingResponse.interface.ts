export interface ScheduleResponseDTO {
    scheduleId: number;
    dayOfWeek: string;
    date: string;
    startTime: string;
    endTime: string;
    status: string;
    meeting: MeetingResponseDTO;
}
export interface SpecialistResponseDTO{
    specId : number;
    name :String;
    phoneNumber : number;
    age : number;
    score : number;
    occupation :String;
    email :String;
}

export interface MeetingResponseDTO{
    meetingId  : number;
    customerId : number;
    date :String;
    startTime:String;
    endTime:String;
    meetStatus:String;
    scheduleId : number; 
    specialistId : number;
    specialistName : string;
}
