package com.wasilewskyy.medical_clinic.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wasilewskyy.medical_clinic.dto.DoctorDTO;
import com.wasilewskyy.medical_clinic.mapper.DoctorMapper;
import com.wasilewskyy.medical_clinic.model.Doctor;
import com.wasilewskyy.medical_clinic.model.Institution;
import com.wasilewskyy.medical_clinic.model.User;
import com.wasilewskyy.medical_clinic.service.DoctorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
public class DoctorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    DoctorService doctorService;

    @MockitoBean
    DoctorMapper doctorMapper;

    @Test
    void shouldGetAllDoctors() throws Exception {
        Institution institution = new Institution(1L, "Szpital Miejski", "Warszawa", "00-001", "Marszałkowska", "100", null);
        Doctor doctor1 = new Doctor(1L, "Jan", "Kowalski", "Kardiolog", "jan.kowalski@email.com", new User(), List.of(institution));
        Doctor doctor2 = new Doctor(2L, "Anna", "Nowak", "Neurolog", "anna.nowak@email.com", new User(), List.of(institution));

        List<Doctor> doctors = Arrays.asList(doctor1, doctor2);

        DoctorDTO doctorDTO1 = new DoctorDTO(1L, "Jan", "Kowalski", "Kardiologia", "jan.kowalski@example.com", List.of(institution));
        DoctorDTO doctorDTO2 = new DoctorDTO(2L, "Anna", "Nowak", "Neurologia", "anna.nowak@example.com", List.of(institution));
        List<DoctorDTO> doctorDTOs = Arrays.asList(doctorDTO1, doctorDTO2);

        when(doctorService.getAll()).thenReturn(doctors);
        when(doctorMapper.toDTO(doctor1)).thenReturn(doctorDTO1);
        when(doctorMapper.toDTO(doctor2)).thenReturn(doctorDTO2);

        mockMvc.perform(MockMvcRequestBuilders.get("/doctors")
    }

    @Test
    void shouldGetDoctorById() throws Exception {
        Institution institution = new Institution(1L, "Szpital Miejski", "Warszawa", "00-001", "Marszałkowska", "100", null);

        Doctor doctor = new Doctor(1L, "Jan", "Kowalski", "Kardiolog", "jan.kowalski@email.com", new User(), List.of(institution));
        DoctorDTO doctorDTO = new DoctorDTO(1L, "Jan", "Kowalski", "Kardiologia", "jan.kowalski@example.com", List.of(institution));

        when(doctorService.getById(1L)).thenReturn(doctor);
        when(doctorMapper.toDTO(doctor)).thenReturn(doctorDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/doctors/{id}")
                .andDo()

    }

    @Test
    void shouldCreateDoctor() throws Exception {

        Institution institution = new Institution(1L, "Szpital Miejski", "Warszawa", "00-001", "Marszałkowska", "100", null);
        DoctorDTO inputDTO = new DoctorDTO(null, "Jan", "Kowalski", "Kardiologia", "jan.kowalski@example.com", List.of(institution));
        Doctor doctorToSave = new Doctor(1L, "Jan", "Kowalski", "Kardiolog", "jan.kowalski@email.com", new User(), List.of(institution));
        Doctor savedDoctor = new Doctor(1L, "Jan", "Kowalski", "Kardiolog", "jan.kowalski@email.com", new User(), List.of(institution));
        DoctorDTO outputDTO = new DoctorDTO(1L, "Jan", "Kowalski", "Kardiologia", "jan.kowalski@example.com", List.of(institution));

        when(doctorMapper.toEntity(inputDTO)).thenReturn(doctorToSave);
        when(doctorService.save(doctorToSave)).thenReturn(savedDoctor);
        when(doctorMapper.toDTO(savedDoctor)).thenReturn(outputDTO);

        mockMvc.perform(MockMvcRequestBuilders.put("/doctors")
    }

    @Test
    void shouldUpdateDoctor() throws Exception {
        Institution institution = new Institution(1L, "Szpital Miejski", "Warszawa", "00-001", "Marszałkowska", "100", null);
        DoctorDTO inputDTO = new DoctorDTO(1L, "Jan", "Kowalski", "Kardiologia", "jan.kowalski@example.com", List.of(institution));
        Doctor doctorToUpdate = new Doctor(1L, "Jan", "Kowalski", "Kardiolog", "jan.kowalski@email.com", new User(), List.of(institution));
        Doctor updatedDoctor = new Doctor(1L, "Jan", "Kowalski", "Kardiolog", "jan.kowalski@email.com", new User(), List.of(institution));
        DoctorDTO outputDTO = new DoctorDTO(1L, "Jan", "Kowalski", "Kardiologia", "jan.kowalski@example.com", List.of(institution));

        when(doctorMapper.toEntity(inputDTO)).thenReturn(doctorToUpdate);
        when(doctorService.update(eq(1L), any(Doctor.class))).thenReturn(updatedDoctor);
        when(doctorMapper.toDTO(updatedDoctor)).thenReturn(outputDTO);

        mockMvc.perform(MockMvcRequestBuilders.put("/doctors/{id}")
    }

    @Test
    void shouldDeleteDoctor() throws Exception {
        Long doctorId = 1L;
        doNothing().when(doctorService).delete(doctorId);

        mockMvc.perform(MockMvcRequestBuilders.delete("/doctors/{id}", doctorId)

    }
}