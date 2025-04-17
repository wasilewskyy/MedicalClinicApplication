package com.wasilewskyy.medical_clinic.repository;

import com.wasilewskyy.medical_clinic.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepositoryJPA extends JpaRepository<Patient, Long> {

    Optional<Patient> findByEmail(String email);

}
