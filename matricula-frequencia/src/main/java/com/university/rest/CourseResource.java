package com.university.rest;

import com.university.model.Course;
import com.university.repository.CourseRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/courses")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CourseResource {

    @Inject private CourseRepository repo;

    @POST public Response create(Course c) {
        repo.save(c);
        return Response.status(Response.Status.CREATED).entity(c).build();
    }

    @GET public List<Course> list() { return repo.listAll(); }
}
