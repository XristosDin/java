package com.mark.javaweb.service;


import com.mark.javaweb.converters.AppointmentConverter;
import com.mark.javaweb.entities.Appointments;
import com.mark.javaweb.records.AppointmentRequestRecord;
import com.mark.javaweb.records.AppointmentReturnRecord;
import com.mark.javaweb.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.chrono.ChronoLocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;


    public List<AppointmentReturnRecord> getAppointments() {
        List<Appointments> appointments = appointmentRepository.findAll();
        List<AppointmentReturnRecord> appointmentReturnRecords = new ArrayList<>();
        appointments.forEach(appointment -> appointmentReturnRecords.add(AppointmentConverter.convertAppointmentToReturnRecordAppointment(appointment)));
        return appointmentReturnRecords;
    }

    public AppointmentReturnRecord getAppointmentById(int id) {
        return AppointmentConverter.convertAppointmentToReturnRecordAppointment(appointmentRepository.findById(id).get());
    }

    public List<AppointmentReturnRecord> getAppointmentsByDoctorUserName(String doctorUserName) {
        List<Appointments> appointments = appointmentRepository.findByDoctorUserName(doctorUserName);
        List<AppointmentReturnRecord> appointmentReturnRecords = new ArrayList<>();
        appointments.forEach(appointment -> appointmentReturnRecords.add(AppointmentConverter.convertAppointmentToReturnRecordAppointment(appointment)));
        return appointmentReturnRecords;
    }

    public List<AppointmentReturnRecord> getAppointmentsByPatientUserName(String patientUserName) {
        List<Appointments> appointments = appointmentRepository.findByPatientUserName(patientUserName);
        List<AppointmentReturnRecord> appointmentReturnRecords = new ArrayList<>();
        appointments.forEach(appointment -> appointmentReturnRecords.add(AppointmentConverter.convertAppointmentToReturnRecordAppointment(appointment)));
        return appointmentReturnRecords;
    }

    public List<AppointmentReturnRecord> getAppointmentsByDateRange(Instant startDate, Instant endDate) {
        List<Appointments> appointments = appointmentRepository.findByDayTimeBetween(startDate, endDate);
        List<AppointmentReturnRecord> appointmentReturnRecords = new ArrayList<>();
        appointments.forEach(appointment -> appointmentReturnRecords.add(AppointmentConverter.convertAppointmentToReturnRecordAppointment(appointment)));
        return appointmentReturnRecords;
    }

    public List<AppointmentReturnRecord> getAvailableAppointmentsByDoctorUserName(String doctorUserName) {
        List<Appointments> appointments = appointmentRepository.findByDoctorUserNameAndAvailableTrue(doctorUserName);
        List<AppointmentReturnRecord> appointmentReturnRecords = new ArrayList<>();
        appointments.forEach(appointment -> appointmentReturnRecords.add(AppointmentConverter.convertAppointmentToReturnRecordAppointment(appointment)));
        return appointmentReturnRecords;
    }


    public List<AppointmentReturnRecord> getAvailableAppointmentsByDoctorSpeciality(String doctorSpeciality) {
        List<Appointments> appointments = appointmentRepository.findByDoctorSpecialityAndAvailableTrue(doctorSpeciality);
        List<AppointmentReturnRecord> appointmentReturnRecords = new ArrayList<>();
        appointments.forEach(appointment -> appointmentReturnRecords.add(AppointmentConverter.convertAppointmentToReturnRecordAppointment(appointment)));
        return appointmentReturnRecords;
    }

    public void createAppointment(AppointmentRequestRecord appointmentRequestRecord) {
        Optional<Appointments> existingAppointment = appointmentRepository.findById(appointmentRequestRecord.id());
        if (existingAppointment.isPresent()) {
            System.out.println("Existing appointment");
        } else {
            Appointments appointments = AppointmentConverter.convertAppointmentRequestRecordToAppointment(appointmentRequestRecord);
            appointmentRepository.save(appointments);
        }
    }

    public void updateAppointment(AppointmentRequestRecord appointmentRequestRecord) {
        Appointments appointments = AppointmentConverter.convertAppointmentRequestRecordToAppointment(appointmentRequestRecord);
        appointmentRepository.save(appointments);
        appointmentRepository.deleteById(appointmentRequestRecord.id());
    }

    public void deleteAppointment(int id) {
        Optional<Appointments> optionalAppointment = appointmentRepository.findById(id);

        if (optionalAppointment.isPresent()) {
            Appointments appointment = optionalAppointment.get();
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime localDateTime = LocalDateTime.ofInstant(appointment.getDayTime(), ZoneId.systemDefault());
            ChronoLocalDateTime<?> chronoDateTime = localDateTime;

            // Check if appointment is more than 3 days away
            if (now.plusDays(3).isBefore(chronoDateTime)) {
                appointmentRepository.deleteById(id);
            } else {
                System.out.println("The appointment is within the next three days and cannot be deleted.");
            }
        } else {
            System.out.println("Appointment not found with ID: " + id);
        }
    }

    public void deleteAppointments() {
        appointmentRepository.deleteAll();
    }
}
