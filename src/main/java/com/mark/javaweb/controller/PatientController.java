package com.mark.javaweb.controller;


import com.mark.javaweb.records.LoginRecord;
import com.mark.javaweb.records.PatientRequestRecord;
import com.mark.javaweb.records.PatientReturnRecord;
import com.mark.javaweb.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/patients")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @GetMapping
    public List<PatientReturnRecord> getPatients() {
        return patientService.getPatients();
    }

    @GetMapping("{username}")
    public PatientReturnRecord getPatientByUsername(@PathVariable String username) {
        return patientService.getPatientByUsername(username);
    }

    @PostMapping
    public ResponseEntity<String> createPatient(@RequestBody PatientRequestRecord patientRequestRecord) {
        PatientRequestRecord patientRequest = new PatientRequestRecord(patientRequestRecord.username(), patientRequestRecord.password(), patientRequestRecord.firstName(), patientRequestRecord.lastName(), patientRequestRecord.AMKA());
        patientService.createPatient(patientRequest);
        return ResponseEntity.ok("Patient with username: " + patientRequest.username() + "created");
    }

    @GetMapping("/login")
    public ResponseEntity<String> checkLogin(@RequestBody PatientRequestRecord patientRequestRecord) {
        return patientService.checkLogin(patientRequestRecord.username(), patientRequestRecord.password()) ? ResponseEntity.ok("Login successful") : ResponseEntity.status(401).body("Login failed");
    }

    @PutMapping("{username}")
    public ResponseEntity<String> updatePatient(@PathVariable String username, @RequestBody PatientRequestRecord patientRequestRecord) {
        PatientRequestRecord patientRequest = new PatientRequestRecord(username, patientRequestRecord.password(), patientRequestRecord.firstName(), patientRequestRecord.lastName(), patientRequestRecord.AMKA());
        patientService.updatePatient(patientRequest);
        return ResponseEntity.ok("Patient with username: " + patientRequest.username() + "updated");
    }


    @DeleteMapping("{username}")
    public ResponseEntity<String> deletePatient(@PathVariable String username) {
        patientService.deletePatientByUsername(username);
        return ResponseEntity.ok("Patient with username: " + username + "deleted");
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAllPatients() {
        patientService.deleteAllPatients();
        return ResponseEntity.ok("All patients deleted");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRecord loginRecord) {
        if (patientService.checkLogin(loginRecord.username(), loginRecord.password())) {
            return ResponseEntity.ok("Login successful");
        }
        return ResponseEntity.status(401).body("Login failed");
    }
}
