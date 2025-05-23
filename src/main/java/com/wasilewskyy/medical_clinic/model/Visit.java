package com.wasilewskyy.medical_clinic.model;

import com.wasilewskyy.medical_clinic.dto.CreateVisitCommand;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "visits")
public class Visit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime startVisitDateTime;
    private LocalDateTime endVisitDateTime;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "doctor_id", referencedColumnName = "id")
    private Doctor doctor;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    private Patient patient;

    public static Visit createVisit(Doctor doctor, CreateVisitCommand command) {
        Visit visit = new Visit();
        visit.setDoctor(doctor);
        visit.setStartVisitDateTime(command.getStartVisitDateTime());
        visit.setEndVisitDateTime(command.getEndVisitDateTime());
        return visit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Visit visit = (Visit) o;
        return id != null && id.equals(visit.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
