package com.wasilewskyy.medical_clinic.repository;

import com.wasilewskyy.medical_clinic.model.Patient;
import org.hibernate.query.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepositoryJPA extends JpaRepository<Patient, Long> {

    Optional<Patient> findByEmail(String email);

    Page<Patient> findAll(Pageable pageable);

}
