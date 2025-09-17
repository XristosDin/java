package com.mark.javaweb.records;


import java.time.Instant;


public record AppointmentRequestRecord(int id, String doctorUserName, String doctorSpeciality, String patientUserName, Instant dayTime,
                                       float fee, boolean available) {
}
