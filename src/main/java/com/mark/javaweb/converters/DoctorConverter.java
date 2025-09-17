package com.mark.javaweb.converters;

import org.mindrot.jbcrypt.BCrypt;

import com.mark.javaweb.entities.Doctor;
import com.mark.javaweb.records.DoctorRequestRecord;
import com.mark.javaweb.records.DoctorReturnRecord;

public class DoctorConverter {

    public static Doctor convertRequestRecordDoctorToDoctor(DoctorRequestRecord doctorRequestRecord) {
        Doctor doctor = new Doctor();
        doctor.setUsername(doctorRequestRecord.username());
        String hashedPassword = BCrypt.hashpw(doctorRequestRecord.password(), BCrypt.gensalt());
        doctor.setPassword(hashedPassword);
        doctor.setFirstName(doctorRequestRecord.firstName());
        doctor.setLastName(doctorRequestRecord.lastName());
        doctor.setDoctorSpeciality(doctorRequestRecord.doctorSpeciality());
        return doctor;
    }

    public static DoctorReturnRecord convertDoctorToDoctorReturnRecord(Doctor doctor) {
        return new DoctorReturnRecord(doctor.getUsername(), doctor.getPassword(), doctor.getFirstName(), doctor.getLastName(), doctor.getDoctorSpeciality());
    }
}
