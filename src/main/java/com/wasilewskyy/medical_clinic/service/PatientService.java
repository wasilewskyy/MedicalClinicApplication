package com.wasilewskyy.medical_clinic.service;

import com.wasilewskyy.medical_clinic.model.Patient;
import com.wasilewskyy.medical_clinic.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Patient getPatientByEmail(String email) {
        return patientRepository.findByEmail(email);
    }

    public Patient addPatient(Patient patient) {
        patientRepository.save(patient);
        return patient;
    }

    public void deletePatientByEmail(String email) {
        patientRepository.deleteByEmail(email);
    }

    public Patient updatePatient(String email, Patient updatedPatient) {
        patientRepository.update(email, updatedPatient);
        return updatedPatient;
    }
}