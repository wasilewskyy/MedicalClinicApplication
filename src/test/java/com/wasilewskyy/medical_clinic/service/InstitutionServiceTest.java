package com.wasilewskyy.medical_clinic.service;

import com.wasilewskyy.medical_clinic.model.Institution;
import com.wasilewskyy.medical_clinic.repository.InstitutionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class InstitutionServiceTest {

    private InstitutionRepository institutionRepository;
    private InstitutionService institutionService;

    @BeforeEach
    void setUp() {
        institutionRepository = Mockito.mock(InstitutionRepository.class);
        institutionService = new InstitutionService(institutionRepository);
    }

    @Test
    void getAll_institutionsExist_shouldReturnAllInstitutions() {
        // given
        Institution institution1 = new Institution(1L, "Szpital Miejski", "Warszawa", "00-001", "Szpitalna", "10", new ArrayList<>());
        Institution institution2 = new Institution(2L, "Klinika Specjalistyczna", "Kraków", "30-001", "Lekarska", "5", new ArrayList<>());
        Institution institution3 = new Institution(3L, "Centrum Medyczne", "Gdańsk", "80-001", "Medyczna", "15", new ArrayList<>());

        List<Institution> institutions = Arrays.asList(institution1, institution2, institution3);
        when(institutionRepository.findAll()).thenReturn(institutions);

        // when
        List<Institution> result = institutionService.getAll();

        // then
        assertAll(
                () -> assertEquals(3, result.size()),
                () -> assertEquals(1L, result.get(0).getId()),
                () -> assertEquals("Szpital Miejski", result.get(0).getInstitutionName()),
                () -> assertEquals(2L, result.get(1).getId()),
                () -> assertEquals("Klinika Specjalistyczna", result.get(1).getInstitutionName()),
                () -> assertEquals(3L, result.get(2).getId()),
                () -> assertEquals("Centrum Medyczne", result.get(2).getInstitutionName())
        );
        verify(institutionRepository).findAll();
    }

    @Test
    void getById_institutionExists_shouldReturnInstitution() {
        // given
        Institution institution = new Institution(1L, "Szpital Miejski", "Warszawa", "00-001",
                "Szpitalna", "10", new ArrayList<>());

        when(institutionRepository.findById(1L)).thenReturn(Optional.of(institution));

        // when
        Institution result = institutionService.getById(1L);

        // then
        assertAll(
                () -> assertEquals(1L, result.getId()),
                () -> assertEquals("Szpital Miejski", result.getInstitutionName()),
                () -> assertEquals("Warszawa", result.getCityName()),
                () -> assertEquals("00-001", result.getPostalCode()),
                () -> assertEquals("Szpitalna", result.getStreetName()),
                () -> assertEquals("10", result.getHouseNumber())
        );
        verify(institutionRepository).findById(1L);
    }


    @Test
    void saveInstitution_validInstitution_shouldSaveAndReturnInstitution() {
        // given
        Institution institution = new Institution(null, "Nowy Szpital", "Poznań", "60-001",
                "Nowa", "20", new ArrayList<>());

        Institution savedInstitution = new Institution(1L, "Nowy Szpital", "Poznań", "60-001",
                "Nowa", "20", new ArrayList<>());

        when(institutionRepository.save(institution)).thenReturn(savedInstitution);

        // when
        Institution result = institutionService.save(institution);

        // then
        assertAll(
                () -> assertEquals(1L, result.getId()),
                () -> assertEquals("Nowy Szpital", result.getInstitutionName()),
                () -> assertEquals("Poznań", result.getCityName())
        );
        verify(institutionRepository).save(institution);
    }
}
