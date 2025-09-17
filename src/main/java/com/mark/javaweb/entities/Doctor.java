package com.mark.javaweb.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "doctors")
public class Doctor extends User {

    @Column(name = "specialty")
    private String specialty;

    // Κανονικοί getters/setters
    public String getSpecialty() { return specialty; }
    public void setSpecialty(String specialty) { this.specialty = specialty; }

    // Alias που ζητά ο DoctorConverter: getDoctorSpeciality()
    public String getDoctorSpeciality() { return specialty; }
    public void setDoctorSpeciality(String s) { this.specialty = s; }
}

