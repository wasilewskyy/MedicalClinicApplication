package com.wasilewskyy.medical_clinic.repository;

import com.wasilewskyy.medical_clinic.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Optional<Doctor> findByName(String name);
}
