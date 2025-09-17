package com.mark.javaweb.controller;


import com.mark.javaweb.entities.Doctor;
import com.mark.javaweb.records.DoctorRequestRecord;
import com.mark.javaweb.records.DoctorReturnRecord;
import com.mark.javaweb.records.LoginRecord;
import com.mark.javaweb.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/doctors")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @GetMapping()
    public List<Doctor> getDoctors() {
        return doctorService.findAllDoctors();
    }

    @GetMapping("{username}")
    public DoctorReturnRecord getDoctorByUsername(@PathVariable String username) {
        return doctorService.findDoctorByUsername(username);
    }

    @GetMapping("/login")
    public ResponseEntity<String> checkLogin(@RequestBody LoginRecord loginRecord) {
        return doctorService.checkLogin(loginRecord.username(), loginRecord.password()) ? ResponseEntity.ok("Login successful") : ResponseEntity.status(401).body("Login failed");
    }

    @PostMapping
    public ResponseEntity<String> createDoctor(@RequestBody DoctorRequestRecord doctorRequestRecord) {
        DoctorRequestRecord doctorRequest = new DoctorRequestRecord(doctorRequestRecord.username(), doctorRequestRecord.password(), doctorRequestRecord.firstName(), doctorRequestRecord.lastName(), doctorRequestRecord.doctorSpeciality());
        doctorService.createDoctor(doctorRequest);
        return ResponseEntity.ok("Doctor with username:" + doctorRequest.username() + "created");
    }

    @PutMapping("{username}")
    public ResponseEntity<String> updateDoctor(@PathVariable String username, @RequestBody DoctorRequestRecord doctorRequestRecord) {
        DoctorRequestRecord doctorRequest = new DoctorRequestRecord(username, doctorRequestRecord.password(), doctorRequestRecord.firstName(), doctorRequestRecord.lastName(), doctorRequestRecord.doctorSpeciality());
        doctorService.updateDoctor(doctorRequest);
        return ResponseEntity.ok("Doctor with username:" + username + "updated");
    }

    @DeleteMapping("{username}")
    public ResponseEntity<String> deleteDoctor(@PathVariable String username) {
        doctorService.deleteDoctorByUsername(username);
        return ResponseEntity.ok("Doctor with username: " + username + " deleted");
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAllDoctors() {
        doctorService.deleteDoctors();
        return ResponseEntity.ok("Doctors deleted");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRecord loginRecord) {
        if (doctorService.checkLogin(loginRecord.username(), loginRecord.password())) {
            return ResponseEntity.ok("Login successful");
        }
        return ResponseEntity.status(401).body("Login failed");
    }
}