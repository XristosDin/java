package com.mark.javaweb.service;

import com.mark.javaweb.converters.DoctorConverter;
import com.mark.javaweb.entities.Doctor;
import com.mark.javaweb.records.DoctorRequestRecord;
import com.mark.javaweb.records.DoctorReturnRecord;
import com.mark.javaweb.repository.DoctorRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    public List<Doctor> findAllDoctors() {
        return doctorRepository.findAll();
    }

    public DoctorReturnRecord findDoctorByUsername(String username) {
        return DoctorConverter.convertDoctorToDoctorReturnRecord(doctorRepository.findByUsername(username));
    }

    public void createDoctor(DoctorRequestRecord doctorRequestRecord) {
        doctorRepository.save(DoctorConverter.convertRequestRecordDoctorToDoctor(doctorRequestRecord));
    }

    public void updateDoctor(DoctorRequestRecord doctorRequestRecord) {
        doctorRepository.save(DoctorConverter.convertRequestRecordDoctorToDoctor(doctorRequestRecord));
    }

    public void deleteDoctorByUsername(String username) {
        doctorRepository.deleteByUsername(username);
    }

    public void deleteDoctors() {
        doctorRepository.deleteAll();
    }

    public boolean checkLogin(String username, String password) {
        Doctor doctor = doctorRepository.findByUsername(username);
        if(doctor == null) {
            return false;
        }
        String doctorPassword = doctor.getPassword();
        return BCrypt.checkpw(password, doctorPassword);
    }
}
