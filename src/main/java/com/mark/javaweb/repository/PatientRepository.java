package com.mark.javaweb.repository;

import com.mark.javaweb.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PatientRepository extends JpaRepository<Patient,String> {
    Patient  findByUsername(String patient);
}
