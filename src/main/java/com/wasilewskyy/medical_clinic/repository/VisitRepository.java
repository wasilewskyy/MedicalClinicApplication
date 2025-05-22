package com.wasilewskyy.medical_clinic.repository;

import com.wasilewskyy.medical_clinic.model.Doctor;
import com.wasilewskyy.medical_clinic.model.Patient;
import com.wasilewskyy.medical_clinic.model.Visit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface VisitRepository extends JpaRepository<Visit, Long> {

    Page<Visit> findAllByPatient(Patient patient, Pageable pageable);

    Page<Visit> findAllByDoctor(Doctor doctor, Pageable pageable);

    List<Visit> isOverlapping(LocalDateTime visitStart, LocalDateTime visitEnd, Doctor doctor);

    Page<Visit> findAvailable(LocalDateTime now, Pageable pageable);

}

