package com.wasilewskyy.medical_clinic.service;

import com.wasilewskyy.medical_clinic.model.Doctor;
import com.wasilewskyy.medical_clinic.model.User;
import com.wasilewskyy.medical_clinic.repository.DoctorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DoctorServiceTest {

    DoctorRepository doctorRepository;
    DoctorService doctorService;

    @BeforeEach
    void setup() {
        this.doctorRepository = Mockito.mock(DoctorRepository.class);
        this.doctorService = new DoctorService(doctorRepository);
    }

    @Test
    void getAllDoctors_doctorExist_shouldReturnAllDoctors() {
        // given
        User user = new User(122L, "JanKowal", "Kowal123");
        Doctor doctor = new Doctor(123L, "Jan", "Kowalski", "Chirurg", "test@email.com", user, null);
        Doctor doctor2 = new Doctor(124L, "Jan", "Kowalski", "Chirurg", "test@email.com", user, null);
        Doctor doctor3 = new Doctor(125L, "Jan", "Kowalski", "Chirurg", "test@email.com", user, null);
        List<Doctor> doctors = Arrays.asList(doctor, doctor2, doctor3);

        when(doctorRepository.findAll()).thenReturn(doctors);
        // when
        List<Doctor> result = doctorService.getAll();

        // then
        assertAll(
                () -> assertEquals(3, result.size()),
                () -> assertEquals(123L, result.get(0).getId()),
                () -> assertEquals(124L, result.get(1).getId()),
                () -> assertEquals(125L, result.get(2).getId())

        );
    }

    @Test
    void getDoctorById_doctorExist_shouldReturnDoctor() {
        // given
        User user = new User(122L, "AnnKow", "Kowal123");
        Doctor doctor = new Doctor(123L, "Anna", "Kowalska", "Chirurg", "test@email.com", user, null);

        when(doctorRepository.findById(123L)).thenReturn(Optional.of(doctor));
        // when
        Doctor result = doctorRepository.getReferenceById(123L);

        // then
        assertAll(
                () -> assertEquals(123L, result.getId()),
                () -> assertEquals("test@email.com", result.getEmail()),
                () -> assertEquals("Anna", result.getFirstName()),
                () -> assertEquals("Kowalska", result.getLastName()),
                () -> assertEquals("Chirurg", result.getSpecialization())
        );
    }

    @Test
    void saveDoctor_doctorExist_shouldSaveDoctor() {
        // given
        User user = new User(122L, "AnnKow", "Kowal123");
            Doctor doctor = new Doctor(123L, "Anna", "Kowalska", "Chirurg", "test@email.com", user, null);

        when(doctorRepository.save(doctor)).thenReturn(doctor);
        // when
        Doctor result = doctorService.save(doctor);

        // then
        verify(doctorRepository).save(doctor);
        assertEquals(doctor, result);

    }

    @Test
    void deleteDoctor_doctorExist_shouldDeleteDoctor() {
        // given
        User user = new User(122L, "AnnKow", "Kowal123");
        Doctor doctor = new Doctor(123L, "Jan", "Kowalski", "Chirurg", "test@email.com", user, null);

        when(doctorRepository.findById(123L)).thenReturn(Optional.of(doctor));
        // when
        doctorService.delete(123L);

        //then
        verify(doctorRepository).delete(doctor);
    }

    @Test
    void updateDoctor_doctorExist_shouldUpdateDoctor() {
        // given
        User user = new User(122L, "AnnKow", "Kowal123");
        Doctor existingDoctor = new Doctor(123L, "Anna", "Kowalska", "Chirurg", "test@email.com", user, null);

        User updatedUser = new User(122L, "JanNow", "NewPass123");
        Doctor updatedDoctor = new Doctor(null, "Jan", "Nowak", "Ortopeda", "new@email.com", updatedUser, null);

        Doctor savedDoctor = new Doctor(123L, "Jan", "Nowak", "Ortopeda", "new@email.com", updatedUser, null);

        when(doctorRepository.findById(123L)).thenReturn(Optional.of(existingDoctor));
        when(doctorRepository.save(existingDoctor)).thenReturn(savedDoctor);

        // when
        Doctor result = doctorService.update(123L, updatedDoctor);

        // then
        assertAll(
                () -> assertEquals(123L, result.getId()),
                () -> assertEquals("Jan", result.getFirstName()),
                () -> assertEquals("Nowak", result.getLastName()),
                () -> assertEquals("Ortopeda", result.getSpecialization()),
                () -> assertEquals("new@email.com", result.getEmail())
        );

        verify(doctorRepository).save(existingDoctor);
        assertEquals(123L, result.getId());

    }
}
