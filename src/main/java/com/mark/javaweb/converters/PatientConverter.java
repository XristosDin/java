package com.mark.javaweb.converters;

import com.mark.javaweb.entities.Patient;
import com.mark.javaweb.records.PatientRequestRecord;
import com.mark.javaweb.records.PatientReturnRecord;
import org.mindrot.jbcrypt.BCrypt;

public class PatientConverter {

    public static PatientReturnRecord convertPatientToPatientReturnRecord(Patient patient){
        return new PatientReturnRecord(patient.getUsername(), patient.getPassword(),patient.getFirstName(),patient.getLastName(),patient.getAMKA());
    }

    public static Patient convertPatientRequestRecordToPatient(PatientRequestRecord patientRequestRecord){
        Patient patient = new Patient();
        patient.setAMKA(patientRequestRecord.AMKA());
        patient.setUsername(patientRequestRecord.username());
        String hashedPassword = BCrypt.hashpw(patientRequestRecord.password(), BCrypt.gensalt());
        patient.setPassword(hashedPassword);
        patient.setFirstName(patientRequestRecord.firstName());
        patient.setLastName(patientRequestRecord.lastName());
        return patient;
    }

}
