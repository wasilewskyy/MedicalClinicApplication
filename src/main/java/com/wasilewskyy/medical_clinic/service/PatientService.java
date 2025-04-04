package com.wasilewskyy.medical_clinic.service;

import com.wasilewskyy.medical_clinic.model.Patient;
import com.wasilewskyy.medical_clinic.repository.PatientRepository;
import com.wasilewskyy.medical_clinic.validation.PatientValidator;
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
        PatientValidator.validateNewPatient(patient);
        patientRepository.save(patient);
        return patient;
    }

    public void deletePatientByEmail(String email) {
        patientRepository.deleteByEmail(email);
    }

    public Patient updatePatient(String email, Patient updatedPatient) {
        Patient existingPatient = patientRepository.findByEmail(email);
        if (existingPatient == null) {
            throw new IllegalArgumentException("Patient with this email does not exist");
        }
        PatientValidator.validateUpdate(existingPatient, updatedPatient);
        patientRepository.update(email, updatedPatient);
        return updatedPatient;
    }
}