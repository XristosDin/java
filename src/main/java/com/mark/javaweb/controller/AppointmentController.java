package com.mark.javaweb.controller;


import com.mark.javaweb.records.AppointmentRequestRecord;
import com.mark.javaweb.records.AppointmentReturnRecord;
import com.mark.javaweb.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping
    public List<AppointmentReturnRecord> getAppointments() {
        return appointmentService.getAppointments();
    }

    @GetMapping("{id}")
    public AppointmentReturnRecord getAppointmentById(@PathVariable int id) {
        return appointmentService.getAppointmentById(id);
    }

    @GetMapping("/doctor/{doctorUserName}")
    public List<AppointmentReturnRecord> getAppointmentsByDoctorUserName(@PathVariable String doctorUserName) {
        return appointmentService.getAppointmentsByDoctorUserName(doctorUserName);
    }

    @GetMapping("/patient/{patientUserName}")
    public List<AppointmentReturnRecord> getAppointmentsByPatientUserName(@PathVariable String patientUserName) {
        return appointmentService.getAppointmentsByPatientUserName(patientUserName);
    }

    @GetMapping("/dateRange")
    public List<AppointmentReturnRecord> getAppointmentsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant endDate) {
        return appointmentService.getAppointmentsByDateRange(startDate, endDate);
    }

    @GetMapping("/doctor/{doctorUserName}/available")
    public List<AppointmentReturnRecord> getAvailableAppointmentsByDoctorUserName(@PathVariable String doctorUserName) {
        return appointmentService.getAvailableAppointmentsByDoctorUserName(doctorUserName);
    }


    @GetMapping("/speciality/{doctorSpeciality}/available")
    public List<AppointmentReturnRecord> getAvailableAppointmentsByDoctorSpeciality(@PathVariable String doctorSpeciality) {
        return appointmentService.getAvailableAppointmentsByDoctorSpeciality(doctorSpeciality);
    }

    @PostMapping
    public ResponseEntity<String> saveAppointment(@RequestBody AppointmentRequestRecord appointmentRequestRecord) {
        AppointmentRequestRecord appointmentRequestRecord1 = new AppointmentRequestRecord(appointmentRequestRecord.id(), appointmentRequestRecord.doctorUserName(), appointmentRequestRecord.doctorSpeciality(), appointmentRequestRecord.patientUserName(), appointmentRequestRecord.dayTime(), appointmentRequestRecord.fee(), appointmentRequestRecord.available());
        appointmentService.createAppointment(appointmentRequestRecord1);
        return ResponseEntity.ok("Appointment created");
    }

    @PutMapping
    public ResponseEntity<String> updateAppointment(@RequestBody AppointmentRequestRecord appointmentRequestRecord) {
        AppointmentRequestRecord appointmentRequestRecord1 = new AppointmentRequestRecord(appointmentRequestRecord.id(), appointmentRequestRecord.doctorUserName(), appointmentRequestRecord.doctorSpeciality(), appointmentRequestRecord.patientUserName(), appointmentRequestRecord.dayTime(), appointmentRequestRecord.fee(), appointmentRequestRecord.available());
        appointmentService.updateAppointment(appointmentRequestRecord1);
        return ResponseEntity.ok("Appointment updated");
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAppointment() {
        appointmentService.deleteAppointments();
        return ResponseEntity.ok("Appointments deleted");
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteAppointment(@PathVariable int id) {
        appointmentService.deleteAppointment(id);
        return ResponseEntity.ok("Appointment deleted");
    }
}
