package com.university.repository;

import com.university.model.Student;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class StudentRepository extends GenericRepository<Student> {
    public StudentRepository() { super(Student.class); }
}
