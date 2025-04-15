package com.wasilewskyy.medical_clinic.mapper;

import com.wasilewskyy.medical_clinic.model.CreatePatientCommand;
import com.wasilewskyy.medical_clinic.model.Patient;
import com.wasilewskyy.medical_clinic.model.PatientDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PatientMapper {

    PatientDTO toDTO(Patient patient);

    Patient toPatient(CreatePatientCommand command);

}