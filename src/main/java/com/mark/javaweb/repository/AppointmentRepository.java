package com.mark.javaweb.repository;

import com.mark.javaweb.entities.Appointments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointments, Integer> {

    // Find appointments by doctor username
    List<Appointments> findByDoctorUserName(String doctorUserName);

    // Find appointments by patient username
    List<Appointments> findByPatientUserName(String patientUserName);

    // Find available appointments
    List<Appointments> findByAvailableTrue();

    // Find appointments by date range
    @Query("SELECT a FROM Appointments a WHERE a.dayTime BETWEEN :startDate AND :endDate")
    List<Appointments> findByDayTimeBetween(@Param("startDate") Instant startDate, @Param("endDate") Instant endDate);

    // Find available appointments by doctor username
    List<Appointments> findByDoctorUserNameAndAvailableTrue(String doctorUserName);

    // Find appointments by doctor speciality
    List<Appointments> findByDoctorSpeciality(String doctorSpeciality);

    // Find available appointments by doctor speciality
    List<Appointments> findByDoctorSpecialityAndAvailableTrue(String doctorSpeciality);
}
