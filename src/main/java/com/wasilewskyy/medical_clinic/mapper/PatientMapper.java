package com.wasilewskyy.medical_clinic.mapper;

import com.wasilewskyy.medical_clinic.model.CreatePatientCommand;
import com.wasilewskyy.medical_clinic.model.Patient;
import com.wasilewskyy.medical_clinic.model.PatientDTO;

public class PatientMapper {
    public static PatientDTO toDTO(Patient patient) {
        return new PatientDTO(patient.getFirstName(), patient.getLastName(),
                patient.getEmail(), patient.getPhoneNumber(), patient.getBirthday());

    }

    public static Patient toPatient(CreatePatientCommand command) {
        return new Patient(command.getEmail(), command.getPassword(), command.getIdCardNo(), command.getFirstName(),
                command.getLastName(), command.getPhoneNumber(), command.getBirthday());

    }
}
