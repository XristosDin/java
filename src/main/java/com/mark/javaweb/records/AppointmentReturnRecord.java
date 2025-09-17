package com.mark.javaweb.records;


import java.time.Instant;


public record AppointmentReturnRecord(int id, String doctorUserName, String doctorSpeciality, String patientUserName, Instant dayTime,
                                      float fee, boolean available) {
}
