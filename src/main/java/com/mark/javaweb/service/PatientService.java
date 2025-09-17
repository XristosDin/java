package com.mark.javaweb.service;

import com.mark.javaweb.converters.PatientConverter;
import com.mark.javaweb.entities.Patient;
import com.mark.javaweb.records.PatientRequestRecord;
import com.mark.javaweb.records.PatientReturnRecord;
import com.mark.javaweb.repository.PatientRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PatientService {
    @Autowired
    private PatientRepository patientRepository;

    public List<PatientReturnRecord> getPatients() {
        List<Patient> patients = patientRepository.findAll();
        List<PatientReturnRecord> patientReturnRecords = new ArrayList<>();
        patients.forEach(patient -> {
            patientReturnRecords.add(PatientConverter.convertPatientToPatientReturnRecord(patient));
        });
        return patientReturnRecords;
    }


    public PatientReturnRecord getPatientByUsername(String username) {
        return PatientConverter.convertPatientToPatientReturnRecord(patientRepository.findByUsername(username));
    }

    public void createPatient(PatientRequestRecord patientRequestRecord) {
        patientRepository.save(PatientConverter.convertPatientRequestRecordToPatient(patientRequestRecord));
    }

    public void updatePatient(PatientRequestRecord patientRequestRecord) {
        patientRepository.save(PatientConverter.convertPatientRequestRecordToPatient(patientRequestRecord));
    }


    public void deletePatientByUsername(String username) {
        patientRepository.deleteById(username);
    }

    public void deleteAllPatients() {
        patientRepository.deleteAll();
    }

    public boolean checkLogin(String username, String password) {
        Patient patient = patientRepository.findByUsername(username);
        if(patient == null) {
            return false;
        }
        String doctorPassword = patient.getPassword();
        return BCrypt.checkpw(password, doctorPassword);
    }
}
