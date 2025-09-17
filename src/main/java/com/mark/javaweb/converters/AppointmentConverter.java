package com.mark.javaweb.converters;

import com.mark.javaweb.entities.Appointments;
import com.mark.javaweb.records.AppointmentRequestRecord;
import com.mark.javaweb.records.AppointmentReturnRecord;

public class AppointmentConverter {

    public static AppointmentReturnRecord convertAppointmentToReturnRecordAppointment(Appointments appointment) {
        return new AppointmentReturnRecord(appointment.getId(), appointment.getDoctorUserName(), appointment.getDoctorSpeciality(), appointment.getPatientUserName(), appointment.getDayTime(), appointment.getFee(), appointment.isAvailable());
    }

    public static Appointments convertAppointmentRequestRecordToAppointment(AppointmentRequestRecord appointmentRequestRecord) {
        Appointments appointment = new Appointments();
        appointment.setDoctorUserName(appointmentRequestRecord.doctorUserName());
        appointment.setDoctorSpeciality(appointmentRequestRecord.doctorSpeciality());
        appointment.setPatientUserName(appointmentRequestRecord.patientUserName());
        appointment.setDayTime(appointmentRequestRecord.dayTime());
        appointment.setFee(appointmentRequestRecord.fee());
        appointment.setAvailable(appointmentRequestRecord.available());
        return appointment;
    }
}
