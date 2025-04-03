package com.wasilewskyy.medical_clinic.repository;

import com.wasilewskyy.medical_clinic.model.Patient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PatientRepository {

    private final List<Patient> patients = new ArrayList<>();

    public List<Patient> findAll() {
        return patients;
    }

    public Patient findByEmail(String email) {
        return patients.stream().filter(p -> p.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }

    public void save(Patient patient) {
        patients.add(patient);
    }

    public void deleteByEmail(String email) {
        patients.removeIf(p -> p.getEmail().equals(email));
    }

    public void update(String email, Patient updatedPatient) {
        for (int i = 0; i < patients.size(); i++) {
            if (patients.get(i).getEmail().equals(email)) {
                patients.set(i, updatedPatient);
                return;
            }
        }
    }
}

