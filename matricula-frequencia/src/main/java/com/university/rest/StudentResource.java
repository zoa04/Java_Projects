package com.university.rest;

import com.university.model.Student;
import com.university.repository.StudentRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/students")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StudentResource {

    @Inject
    private StudentRepository repo;

    @POST
    public Response create(Student s) {
        repo.save(s);
        return Response.status(Response.Status.CREATED).entity(s).build();
    }

    @GET
    public List<Student> list() { return repo.listAll(); }
}
