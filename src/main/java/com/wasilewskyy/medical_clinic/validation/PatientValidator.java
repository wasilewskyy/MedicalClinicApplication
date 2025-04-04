package com.wasilewskyy.medical_clinic.validation;

import com.wasilewskyy.medical_clinic.model.Patient;
import com.wasilewskyy.medical_clinic.repository.PatientRepository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PatientValidator {

    public static void validateNewPatient(Patient patient, PatientRepository patientRepository) {
        if (patient.getIdCardNo() == null || patient.getFirstName() == null
                || patient.getLastName() == null || patient.getBirthday() == null
                || patient.getPhoneNumber() == null || patient.getEmail() == null
                || patient.getPassword() == null) {
            throw new IllegalArgumentException("None of the patient fields can be blank.");
        }
        if (patientRepository.findByEmail(patient.getEmail())) {
            throw new IllegalArgumentException("Patient with this email address already exists.");
        }
        patientRepository.findByEmail(patient.getEmail());
    }

    public static void validateUpdate(Patient existingPatient, Patient updatedPatient) {
        if (updatedPatient.getIdCardNo() != null && !updatedPatient.getIdCardNo().equals(existingPatient.getIdCardNo())) {
            throw new IllegalArgumentException("You cannot change the id of a patient.");
        }

        if (updatedPatient.getIdCardNo() == null || updatedPatient.getFirstName() == null
                || updatedPatient.getLastName() == null || updatedPatient.getBirthday() == null
                || updatedPatient.getPhoneNumber() == null || updatedPatient.getEmail() == null
                || updatedPatient.getPassword() == null) {
            throw new IllegalArgumentException("None of the updated patient fields can be blank.");
        }

        if (!updatedPatient.getEmail().equals(existingPatient.getEmail()) && existingEmails.contains(updatedPatient.getEmail())) {
            throw new IllegalArgumentException("Patient with this email address already exists.");
        }
    }
}
