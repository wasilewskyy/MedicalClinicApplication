package com.wasilewskyy.medical_clinic.dto;
import com.wasilewskyy.medical_clinic.model.Institution;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String specialization;
    private String email;
    private List<Institution> institutions;
}
