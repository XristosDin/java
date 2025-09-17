package com.mark.javaweb.entities;


import jakarta.persistence.*;

import java.time.Instant;
import java.util.concurrent.ThreadLocalRandom;

@Entity
@Table(name = "appointments")
public class Appointments {

    @Id
    private int id;

    private String doctorUserName;

    @Column(name = "doctor_speciality")
    private String doctorSpeciality;

    private String patientUserName;

    @Column(name = "day_time")
    private Instant dayTime;

    private float fee;

    private boolean available;

    public Appointments() {
        this.id = ThreadLocalRandom.current().nextInt(1, 9999);//Generating random id
        this.available = true;//Assuming the appointment is always available when created
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public Instant getDayTime() {
        return dayTime;
    }

    public void setDayTime(Instant dayTime) {
        this.dayTime = dayTime;
    }

    public float getFee() {
        return fee;
    }

    public void setFee(float fee) {
        this.fee = fee;
    }

    public int getId() {
        return id;
    }

    public String getDoctorUserName() {
        return doctorUserName;
    }

    public void setDoctorUserName(String doctorUserName) {
        this.doctorUserName = doctorUserName;
    }

    public String getPatientUserName() {
        return patientUserName;
    }

    public void setPatientUserName(String patientUserName) {
        this.patientUserName = patientUserName;
    }

    public String getDoctorSpeciality() {
        return doctorSpeciality;
    }

    public void setDoctorSpeciality(String doctorSpeciality) {
        this.doctorSpeciality = doctorSpeciality;
    }
}
