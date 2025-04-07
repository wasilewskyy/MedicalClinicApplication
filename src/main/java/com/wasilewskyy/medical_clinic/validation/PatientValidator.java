package com.wasilewskyy.medical_clinic.validation;

import com.wasilewskyy.medical_clinic.model.Patient;
import com.wasilewskyy.medical_clinic.repository.PatientRepository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PatientValidator {

    public static void validateNewPatient(Patient patient, PatientRepository patientRepository) {
        if (patient.getIdCardNo() == null || patient.getFirstName() == null
                || patient.getLastName() == null || patient.getBirthday() == null
                || patient.getPhoneNumber() == null || patient.getEmail() == null
                || patient.getPassword() == null) {
            throw new IllegalArgumentException("None of the patient fields can be blank.");
        }
        Optional<Patient> entityPatient = patientRepository.findByEmail(patient.getEmail());
        if (entityPatient.isPresent() && !entityPatient.get().getEmail().equals(patient.getEmail())) {
            throw new IllegalArgumentException("Email already exists.");
        }
    }

    public static void validateUpdate(Patient patient, Patient newPatient) {
        if (newPatient.getIdCardNo() != null && !newPatient.getIdCardNo().equals(patient.getIdCardNo())) {
            throw new IllegalArgumentException("You cannot change the id of a patient.");
        }
        if (newPatient.getIdCardNo() == null || newPatient.getFirstName() == null
                || newPatient.getLastName() == null || newPatient.getBirthday() == null
                || newPatient.getPhoneNumber() == null || newPatient.getEmail() == null
                || newPatient.getPassword() == null) {
            throw new IllegalArgumentException("None of the updated patient fields can be blank.");
        }
    }

    public static void validateEmailUpdate(Patient patient, Patient newPatient) {
        if (newPatient.getEmail().equals(patient.getEmail())) {
            throw new IllegalArgumentException("Patient with this email address already exists.");
        }
    }
}
