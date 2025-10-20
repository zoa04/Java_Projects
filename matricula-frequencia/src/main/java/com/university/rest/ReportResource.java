package com.university.rest;

import com.university.model.Attendance;
import com.university.repository.AttendanceRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.*;
import java.util.stream.Collectors;

@Path("/reports")
@Produces(MediaType.APPLICATION_JSON)
public class ReportResource {

    @Inject private AttendanceRepository attendanceRepo;

    // /api/reports/attendance?studentId=1&courseId=1
    @GET
    @Path("/attendance")
    public List<Map<String,Object>> attendanceReport(@QueryParam("studentId") Long studentId, @QueryParam("courseId") Long courseId) {
        List<Attendance> list = attendanceRepo.listAll();
        return list.stream().filter(a -> {
            boolean matchStudent = studentId == null || a.getEnrollment().getStudent().getId().equals(studentId);
            boolean matchCourse = courseId == null || a.getEnrollment().getCourse().getId().equals(courseId);
            return matchStudent && matchCourse;
        }).map(a -> {
            Map<String,Object> m = new HashMap<>();
            m.put("enrollmentId", a.getEnrollment().getId());
            m.put("studentId", a.getEnrollment().getStudent().getId());
            m.put("courseId", a.getEnrollment().getCourse().getId());
            m.put("date", a.getDate());
            m.put("present", a.isPresent());
            return m;
        }).collect(Collectors.toList());
    }
}
