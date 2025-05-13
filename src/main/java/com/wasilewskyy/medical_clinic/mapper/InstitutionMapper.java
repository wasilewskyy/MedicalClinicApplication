package com.wasilewskyy.medical_clinic.mapper;

import com.wasilewskyy.medical_clinic.model.Institution;
import com.wasilewskyy.medical_clinic.dto.InstitutionDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InstitutionMapper {

    InstitutionDTO toDTO(Institution institution);

    Institution toEntity(InstitutionDTO dto);
}
