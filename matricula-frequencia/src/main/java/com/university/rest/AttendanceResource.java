package com.university.rest;

import com.university.model.Attendance;
import com.university.model.Enrollment;
import com.university.repository.AttendanceRepository;
import com.university.repository.EnrollmentRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Path("/attendance")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AttendanceResource {

    public static class AttendanceRequest { public Long enrollmentId; public String date; public boolean present; }

    @Inject private AttendanceRepository attendanceRepo;
    @Inject private EnrollmentRepository enrollmentRepo;

    @POST
    public Response mark(AttendanceRequest req) {
        Enrollment en = enrollmentRepo.find(req.enrollmentId);
        if (en == null) return Response.status(Response.Status.BAD_REQUEST).entity("Enrollment not found").build();
        Attendance a = new Attendance(en, LocalDate.parse(req.date), req.present);
        attendanceRepo.save(a);
        return Response.status(Response.Status.CREATED).entity(a).build();
    }

    @GET
    @Path("/byEnrollment/{enrollmentId}")
    public List<Attendance> byEnrollment(@PathParam("enrollmentId") Long enrollmentId) {
        return attendanceRepo.listAll().stream().filter(a->a.getEnrollment().getId().equals(enrollmentId)).collect(Collectors.toList());
    }
}
