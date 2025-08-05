package com.wasilewskyy.medical_clinic.repository;

import com.wasilewskyy.medical_clinic.model.Password;
import com.wasilewskyy.medical_clinic.model.Patient;
import com.wasilewskyy.medical_clinic.validation.PatientValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PatientRepository {

    private final List<Patient> patients = new ArrayList<>();

    public List<Patient> findAll() {
        return new ArrayList<>(patients);
    }

    public Optional<Patient> findByEmail(String email) {
        return patients.stream()
                .filter(p -> p.getEmail().equals(email))
                .findFirst();
    }

    public void save(Patient patient) {
        if (findByEmail(patient.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Patient with email " + patient.getEmail() + " already exists");
        }
        patients.add(patient);
    }

    public void deleteByEmail(String email) {
        patients.removeIf(p -> p.getEmail().equals(email));
    }

    public void update(String email, Patient updatedPatient) {
        Patient patient = findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Patient with email " + email + " does not exist"));
        PatientValidator.validateUpdate(patient, updatedPatient);
        PatientValidator.validateEmailUpdate(patient, updatedPatient);
        patient.updatePatient(patient);
    }

    public Patient changePassword(String email, Password password) {
        Patient patient = findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Patient with email " + email + " does not exist"));
        patient.getUser().setPassword(password.getPassword());
        return patient;
    }
}

