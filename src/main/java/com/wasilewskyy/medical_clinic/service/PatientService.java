package com.wasilewskyy.medical_clinic.service;

import com.wasilewskyy.medical_clinic.model.Password;
import com.wasilewskyy.medical_clinic.model.Patient;
import com.wasilewskyy.medical_clinic.repository.PatientRepository;
import com.wasilewskyy.medical_clinic.validation.PatientValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Optional<Patient> getPatientByEmail(String email) {
        return patientRepository.findByEmail(email);
    }

    public Patient addPatient(Patient patient) {
        PatientValidator.validateNewPatient(patient, patientRepository);
        patientRepository.save(patient);
        return patient;
    }

    public void deletePatientByEmail(String email) {
        patientRepository.deleteByEmail(email);
    }

    public Patient updatePatient(String email, Patient updatedPatient) {
        Optional<Patient> existingPatient = patientRepository.findByEmail(email);
        if (existingPatient.isEmpty()) {
            throw new IllegalArgumentException("Patient with this email does not exist");
        }
        PatientValidator.validateUpdate(existingPatient.orElse(null), updatedPatient);
        PatientValidator.validateEmailUpdate(updatedPatient, existingPatient.get());
        patientRepository.update(email, updatedPatient);
        return updatedPatient;
    }

    public Patient changePassword(String email, Password password) {
        if (password.getPassword() == null || password.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
        return patientRepository.changePassword(email, password);
    }
}