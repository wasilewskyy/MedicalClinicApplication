package com.wasilewskyy.medical_clinic.mapper;

import com.wasilewskyy.medical_clinic.dto.CreateVisitCommand;
import com.wasilewskyy.medical_clinic.dto.VisitDTO;
import com.wasilewskyy.medical_clinic.model.Visit;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VisitMapper {
    VisitDTO toDTO(Visit visit);
    Visit toVisit(CreateVisitCommand command);
}
