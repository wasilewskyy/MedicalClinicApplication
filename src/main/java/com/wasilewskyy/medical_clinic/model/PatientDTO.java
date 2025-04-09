package com.wasilewskyy.medical_clinic.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String idCardNo;

}
