package com.wasilewskyy.medical_clinic.service;

import com.wasilewskyy.medical_clinic.model.Doctor;
import com.wasilewskyy.medical_clinic.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepository;

    public List<Doctor> getAll() {
        return doctorRepository.findAll();
    }

    public Doctor getById(Long id) {
        return doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
    }

    public Doctor save(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    public void delete(Long id) {
        doctorRepository.deleteById(id);
    }

    public Doctor update(Long id, Doctor updated) {
        Doctor existing = getById(id);
        updated.setId(existing.getId());
        return doctorRepository.save(updated);
    }
}