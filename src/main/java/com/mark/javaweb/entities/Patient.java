package com.mark.javaweb.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "patients")
public class Patient extends User {

    @Column(name = "amka")
    private String amka;

    // Κανονικοί getters/setters (lowercase)
    public String getAmka() { return amka; }
    public void setAmka(String amka) { this.amka = amka; }

    // Alias που ζητά ο PatientConverter: getAMKA()
    public String getAMKA() { return amka; }
    public void setAMKA(String a) { this.amka = a; }
}
