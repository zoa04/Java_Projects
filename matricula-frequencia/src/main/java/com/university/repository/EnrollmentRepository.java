package com.university.repository;

import com.university.model.Enrollment;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.Optional;

@ApplicationScoped
public class EnrollmentRepository extends GenericRepository<Enrollment> {
    public EnrollmentRepository() { super(Enrollment.class); }

    @Transactional
    public Optional<Enrollment> findByStudentAndCourse(Long studentId, Long courseId) {
        try {
            Enrollment e = em.createQuery("SELECT en FROM Enrollment en WHERE en.student.id = :s AND en.course.id = :c", Enrollment.class)
                    .setParameter("s", studentId).setParameter("c", courseId).getSingleResult();
            return Optional.of(e);
        } catch (Exception ex) {
            return Optional.empty();
        }
    }
}
