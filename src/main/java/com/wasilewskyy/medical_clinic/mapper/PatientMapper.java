package com.wasilewskyy.medical_clinic.mapper;

import com.wasilewskyy.medical_clinic.model.CreatePatientCommand;
import com.wasilewskyy.medical_clinic.model.Patient;
import com.wasilewskyy.medical_clinic.model.PatientDTO;
import org.mapstruct.Mapper;
import org.springframework.web.bind.annotation.Mapping;

@Mapper(componentModel = "spring")
public interface PatientMapper {

    PatientDTO toDTO(Patient patient);

    @Mapping(target = "user.username", source = "username")
    @Mapping(target = "user.password", source = "password")
    Patient toPatient(CreatePatientCommand command);

}