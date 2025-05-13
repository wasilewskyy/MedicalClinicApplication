package com.wasilewskyy.medical_clinic.service;

import com.wasilewskyy.medical_clinic.dto.CreatePatientCommand;
import com.wasilewskyy.medical_clinic.model.Patient;
import com.wasilewskyy.medical_clinic.repository.PatientRepositoryJPA;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientServiceJPA {

    private final PatientRepositoryJPA patientRepositoryJPA;

    public List<Patient> getAllPatients(Pageable pageable) {
        return patientRepositoryJPA.findAll(pageable).getContent();
    }

    public Patient getPatientByEmail(String email) {
        return patientRepositoryJPA.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Patient with given email does not exist."));
    }

    public Patient addPatient(Patient patient) {
        patientRepositoryJPA.save(patient);
        return patient;
    }

    public void deletePatientByEmail(String email) {
        Patient existingPatient = patientRepositoryJPA.findByEmail(email)
                .orElseThrow(()-> new IllegalArgumentException("Patient with given email does not exist."));
        patientRepositoryJPA.delete(existingPatient);
    }

    public Patient updatePatient(String email, Patient updatedPatient, CreatePatientCommand command) {
        Patient existingPatient = patientRepositoryJPA.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Patient with this email " + email +"does not exist"));
        existingPatient.updateFromCommand(command);
        return patientRepositoryJPA.save(existingPatient);
    }
}