package com.wasilewskyy.medical_clinic.mapper;

import com.wasilewskyy.medical_clinic.model.Doctor;
import com.wasilewskyy.medical_clinic.model.DoctorDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DoctorMapper {

    DoctorDTO toDTO(Doctor doctor);

    Doctor toEntity(DoctorDTO dto);

}

