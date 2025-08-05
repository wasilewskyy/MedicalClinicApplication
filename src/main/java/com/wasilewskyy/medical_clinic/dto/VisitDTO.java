package com.wasilewskyy.medical_clinic.dto;

import com.wasilewskyy.medical_clinic.model.Doctor;
import com.wasilewskyy.medical_clinic.model.Patient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VisitDTO {

    private Long id;
    private LocalDateTime startVisitDateTime;
    private LocalDateTime endVisitDateTime;
    private Doctor doctor;
    private Patient patient;
}
