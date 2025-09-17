package com.mark.javaweb.repository;

import com.mark.javaweb.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DoctorRepository extends JpaRepository<Doctor,String> {

    void deleteByUsername(String username);

    Doctor findByUsername(String username);
}
