package com.wasilewskyy.medical_clinic.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="PATIENT")
public class Patient {

    @Column(name="PATIENT_EMAIL",unique=false, nullable=false)
    private String email;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idCardNo;

    @Column(name="PATIENT_NAME",unique = false, nullable = false)
    private String firstName;

    @Column(name="PATIENT_SURNAME",unique = false, nullable = false)
    private String lastName;

    @Column(name="PATIENT_PHONE_NUMBER",unique = false, nullable = false)
    private String phoneNumber;

    @Temporal(TemporalType.DATE)
    private LocalDate birthday;

    @Transient
    private User user;

    public void updatePatient(Patient patient) {
        this.email = patient.getEmail();
        this.idCardNo = patient.getIdCardNo();
        this.firstName = patient.getFirstName();
        this.lastName = patient.getLastName();
        this.phoneNumber = patient.getPhoneNumber();
        this.birthday = patient.getBirthday();
        this.user = patient.getUser();
    }
}
