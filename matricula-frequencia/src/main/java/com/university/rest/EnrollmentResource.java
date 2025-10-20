package com.university.rest;

import com.university.model.Enrollment;
import com.university.model.Student;
import com.university.model.Course;
import com.university.repository.EnrollmentRepository;
import com.university.repository.StudentRepository;
import com.university.repository.CourseRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/enrollments")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EnrollmentResource {

    public static class EnrollRequest { public Long studentId; public Long courseId; }

    @Inject private EnrollmentRepository enrollmentRepo;
    @Inject private StudentRepository studentRepo;
    @Inject private CourseRepository courseRepo;

    @POST
    public Response enroll(EnrollRequest req) {
        Student s = studentRepo.find(req.studentId);
        Course c = courseRepo.find(req.courseId);
        if (s == null || c == null) return Response.status(Response.Status.BAD_REQUEST).entity("Student or Course not found").build();

        // check existing
        if (enrollmentRepo.findByStudentAndCourse(req.studentId, req.courseId).isPresent())
            return Response.status(Response.Status.CONFLICT).entity("Already enrolled").build();

        Enrollment en = new Enrollment(s, c);
        enrollmentRepo.save(en);
        return Response.status(Response.Status.CREATED).entity(en).build();
    }
}
