package com.university.repository;

import com.university.model.Attendance;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AttendanceRepository extends GenericRepository<Attendance> {
    public AttendanceRepository() { super(Attendance.class); }
}
