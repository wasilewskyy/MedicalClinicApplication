package com.wasilewskyy.medical_clinic.repository;

import com.wasilewskyy.medical_clinic.model.Doctor;
import com.wasilewskyy.medical_clinic.model.Patient;
import com.wasilewskyy.medical_clinic.model.Visit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface VisitRepository extends JpaRepository<Visit, Long> {

    Page<Visit> findAllByPatient(Patient patient, Pageable pageable);

    Page<Visit> findAllByDoctor(Doctor doctor, Pageable pageable);

    @Query("SELECT visit FROM Visit visit " +
            "WHERE visit.startVisitDateTime <= :newVisitEnd " +
            "AND visit.endVisitDateTime >= :newVisitStart " +
            "AND visit.doctor = :doctor")
    List<Visit> isOverlapping(@Param("newVisitStart") LocalDateTime startVisitDateTime,
                              @Param("newVisitEnd") LocalDateTime endVisitDateTime,
                              @Param("doctor") Doctor doctor);

    Page<Visit> findAvailable(LocalDateTime now, Pageable pageable);

}

