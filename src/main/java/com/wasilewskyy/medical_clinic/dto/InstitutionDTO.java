package com.wasilewskyy.medical_clinic.dto;
import com.wasilewskyy.medical_clinic.model.Doctor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstitutionDTO {

    private Long id;
    private String institutionName;
    private String cityName;
    private String postalCode;
    private String streetName;
    private String houseNumber;
    private List<Doctor> doctors;
}
