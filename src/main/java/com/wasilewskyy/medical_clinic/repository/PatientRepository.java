package com.wasilewskyy.medical_clinic.repository;

import com.wasilewskyy.medical_clinic.model.Password;
import com.wasilewskyy.medical_clinic.model.Patient;
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
        if (findByEmail(patient.getEmail()) != null) {
            throw new IllegalArgumentException("Patient with email " + patient.getEmail() + " already exists");
        }
        patients.add(patient);
    }

    public void deleteByEmail(String email) {
        patients.removeIf(p -> p.getEmail().equals(email));
    }

    public void update(String email, Patient updatedPatient) {
        patients.stream()
                .filter(patient -> patient.getEmail().equals(email))
                .findFirst()
                .ifPresent(patient -> {
                    int index = patients.indexOf(patient);
                    patients.set(index, updatedPatient);
                });
    }

    public Patient changePassword(String email, Password password) {
        Patient patient = findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Patient with email " + email + " does not exist"));
        patient.setPassword(password.getPassword());
        return patient;
    }
}

