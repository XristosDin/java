package com.mark.javaweb.entities;


import jakarta.persistence.*;

@Entity
@Table(name = "admins")
public class Admin extends User {
    private String adminPassword = "2004";

    public Admin() {
        super();
    }

    public String getAdminPassword() {
        return adminPassword;
    }

}
