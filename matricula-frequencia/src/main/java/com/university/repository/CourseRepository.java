package com.university.repository;

import com.university.model.Course;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CourseRepository extends GenericRepository<Course> {
    public CourseRepository() { super(Course.class); }
}
