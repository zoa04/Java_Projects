package com.university.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "attendance", indexes = @Index(columnList = "enrollment_id"))
public class Attendance {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "enrollment_id")
    private Enrollment enrollment;

    private LocalDate date;
    private boolean present;

    public Attendance() {}
    public Attendance(Enrollment enrollment, LocalDate date, boolean present) {
        this.enrollment = enrollment; this.date = date; this.present = present;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Enrollment getEnrollment() { return enrollment; }
    public void setEnrollment(Enrollment enrollment) { this.enrollment = enrollment; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public boolean isPresent() { return present; }
    public void setPresent(boolean present) { this.present = present; }

    @Override public String toString() { return "Attendance{"+id+":e="+enrollment.getId()+",d="+date+"}"; }
}
