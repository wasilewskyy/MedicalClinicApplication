package com.wasilewskyy.medical_clinic.dto;

import com.wasilewskyy.medical_clinic.model.Doctor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateVisitCommand {

    private LocalDateTime startVisitDateTime;
    private LocalDateTime endVisitDateTime;

}
