package com.wasilewskyy.medical_clinic.service;

import com.wasilewskyy.medical_clinic.dto.CreatePatientCommand;
import com.wasilewskyy.medical_clinic.model.Patient;
import com.wasilewskyy.medical_clinic.model.User;
import com.wasilewskyy.medical_clinic.repository.PatientRepositoryJPA;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PatientServiceTest {

    PatientRepositoryJPA patientRepositoryJPA;
    PatientServiceJPA patientServiceJPA;

    @BeforeEach
    void setup() {
        this.patientServiceJPA = new PatientServiceJPA(patientRepositoryJPA);
        this.patientRepositoryJPA = Mockito.mock(PatientRepositoryJPA.class);
    }

    @Test
    void getAllPatients_patientsExist_patientsReturned() {
        //given - sekcja ktora tworzy wszystkie instancje klas
        User user = new User(122L, "kuba123", "password123");
        Patient patient1 = new Patient(123L, "test1@email.com", "1", "kuba", "was", "123456789", LocalDate.of(2000, 12, 29), user);
        Patient patient2 = new Patient(124L, "test2@email.com", "2", "kuba", "was", "123456789", LocalDate.of(2000, 12, 29), user);
        Patient patient3 = new Patient(125L, "test3@email.com", "3", "kuba", "was", "123456789", LocalDate.of(2000, 12, 29), user);
        PageImpl<Patient> page = new PageImpl<>(List.of(patient1, patient2, patient3));

        when(patientRepositoryJPA.findAll(any(Pageable.class))).thenReturn(page);
        //when - wykonanie testu
        List<Patient> result = patientServiceJPA.getAllPatients(PageRequest.of(0,10));

        //then - wykonanie asercji
        assertAll(
                () -> assertEquals(3, result.size()),
                () -> assertEquals(123L, result.get(0).getId()),
                () -> assertEquals(124L, result.get(1).getId()),
                () -> assertEquals(125L, result.get(2).getId())
        );
    }

    @Test
    void getPatientByEmail_patientExists_patientReturned() {
        // given
        User user = new User(123L, "kuba123", "password123");
        Patient patient = new Patient(126L, "test4@email.com", "4", "kuba", "was",
                "123456789", LocalDate.of(2000, 12, 29), user);

        when(patientRepositoryJPA.findByEmail("test4@email.com")).thenReturn(Optional.of(patient));

        // when
        Patient result = patientServiceJPA.getPatientByEmail("test4@email.com");

        // then
        assertAll(
                () -> assertEquals(126L, result.getId()),
                () -> assertEquals("test4@email.com", result.getEmail()),
                () -> assertEquals("kuba", result.getFirstName()),
                () -> assertEquals("was", result.getLastName())
        );
    }

    @Test
    void getPatientByEmail_patientDoesNotExist_throwsException() {
        // given
        String email = "nonexist@test.com";
        when(patientRepositoryJPA.findByEmail(email)).thenReturn(Optional.empty());

        // when/then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            patientServiceJPA.getPatientByEmail(email);});

        assertEquals("Patient with given email does not exist.", exception.getMessage());
    }

    @Test
    void addPatient_validPatient_patientSaved() {
        // given
        User user = new User(123L, "kuba123", "password123");
        Patient patient = new Patient(127L, "newpatient@email.com", "5", "Jan", "Kowalski",
                "987654321", LocalDate.of(1995, 5, 15), user);
        when(patientRepositoryJPA.save(any(Patient.class))).thenReturn(patient);

        // when
        Patient result = patientServiceJPA.addPatient(patient);

        // then
        verify(patientRepositoryJPA).save(patient);
        assertEquals(patient, result);
    }

    @Test
    void deletePatientByEmail_patientExists_patientDeleted() {
        // given
        User user = new User(123L, "kuba123", "password123");
        Patient patient = new Patient(128L, "todelete@email.com", "6", "Anna", "Nowak",
                "555666777", LocalDate.of(1990, 3, 10), user);
        when(patientRepositoryJPA.findByEmail("todelete@email.com")).thenReturn(Optional.of(patient));

        // when
        patientServiceJPA.deletePatientByEmail("todelete@email.com");

        // then
        verify(patientRepositoryJPA).delete(patient);
    }

    @Test
    void deletePatientByEmail_patientDoesNotExist_throwsException() {
        // given
        String email = "nonexist@email.com";
        when(patientRepositoryJPA.findByEmail(email)).thenReturn(Optional.empty());

        // when/then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            patientServiceJPA.deletePatientByEmail(email);
        });

        assertEquals("Patient with given email does not exist.", exception.getMessage());
        verify(patientRepositoryJPA, never()).delete(any());
    }

    @Test
    void updatePatient_patientExists_patientUpdated() {
        // given
        User user = new User(123L, "kuba123", "password123");
        Patient existingPatient = new Patient(129L, "update@email.com", "7", "Tomasz", "Zielinski",
                "111222333", LocalDate.of(1985, 8, 20), user);

        CreatePatientCommand command = new CreatePatientCommand("Tomasz", "Zielinski Updated", "update@email.com",
                "newpassword123", "111222333", LocalDate.of(1985, 8, 20), "7");

        when(patientRepositoryJPA.findByEmail("update@email.com")).thenReturn(Optional.of(existingPatient));

        // when
        Patient result = patientServiceJPA.updatePatient("update@email.com", existingPatient, command);

        // then
        verify(patientRepositoryJPA).save(existingPatient);

        assertEquals("update@email.com", result.getEmail());
    }

    @Test
    void updatePatient_patientDoesNotExist_throwsException() {
        // given
        String email = "nonexist@email.com";
        Patient updatedPatient = new Patient();
        CreatePatientCommand command = new CreatePatientCommand("Test", "User", "nonexist@email.com",
                "password", "123456789", LocalDate.now(), "ABC123");

        when(patientRepositoryJPA.findByEmail(email)).thenReturn(Optional.empty());

        // when/then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {patientServiceJPA.updatePatient(email, updatedPatient, command);});

        assertTrue(exception.getMessage().contains("Patient with this email"));
        verify(patientRepositoryJPA, never()).save(any());
    }
}