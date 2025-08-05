package com.wasilewskyy.medical_clinic.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wasilewskyy.medical_clinic.dto.CreatePatientCommand;
import com.wasilewskyy.medical_clinic.dto.PatientDTO;
import com.wasilewskyy.medical_clinic.mapper.PatientMapper;
import com.wasilewskyy.medical_clinic.model.Password;
import com.wasilewskyy.medical_clinic.model.Patient;
import com.wasilewskyy.medical_clinic.model.User;
import com.wasilewskyy.medical_clinic.service.PatientServiceJPA;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    PatientServiceJPA patientServiceJPA;

    @MockitoBean
    PatientMapper patientMapper;

    @Test
    void shouldReturnAllPatients() throws Exception {
        List<PatientDTO> patientDTOS = List.of(
                new PatientDTO("Jan", "Nowak", "test@email.com", "123456789", LocalDate.of(1999, 10, 12)),
                new PatientDTO("Anna", "Nowak", "test2@email.com", "111222444", LocalDate.of(2000, 11, 22)),
                new PatientDTO("Jakub", "Kowalski", "test3@email.com", "555777888", LocalDate.of(2005, 5, 8))
        );

        when(patientServiceJPA.getAllPatients(any())).thenReturn();

        mockMvc.perform(MockMvcRequestBuilders.get("/patients"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].firstName").value("Jan"))
                .andExpect(jsonPath("$[0].lastName").value("Nowak"))
                .andExpect(jsonPath("$[0].email").value("test@email.com"))
                .andExpect(jsonPath("$[0].phone").value("123456789"))
                .andExpect(jsonPath("$[0].dateOfBirth").value(LocalDate.of(1999, 10, 12)))
                .andExpect(jsonPath("$[1].firstName").value("Anna"))
                .andExpect(jsonPath("$[1].lastName").value("Nowak"))
                .andExpect(jsonPath("$[1].email").value("test2@email.com"))
                .andExpect(jsonPath("$[1].phone").value("111222444"))
                .andExpect(jsonPath("$[1].dateOfBirth").value(LocalDate.of(2000, 11, 22)))
                .andExpect(jsonPath("$[2].firstName").value("Jakub"))
                .andExpect(jsonPath("$[2].lastName").value("Kowalski"))
                .andExpect(jsonPath("$[2].email").value("test3@email.com"))
                .andExpect(jsonPath("$[2].phone").value("555777888"))
                .andExpect(jsonPath("$[2].dateOfBirth").value(LocalDate.of(2005, 5, 8)));
    }

    @Test
    void shouldReturnPatientByEmail() throws Exception {
        Patient patient = new Patient();
        patient.setEmail("test@email.com");
        when(patientServiceJPA.getPatientByEmail("test@email.com")).thenReturn(patient);

        mockMvc.perform(MockMvcRequestBuilders.get("/patients/test@email.com"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@email.com"));

    }

    @Test
    void shouldAddPatient() throws Exception {
        CreatePatientCommand command = new CreatePatientCommand("Jan", "Nowak", "test@email.com", "password", "123456789", LocalDate.of(1999, 10, 12), "ABC123");
        Patient patient = new Patient(1L, "test@email.com", "ABC123", "Jan", "Nowak", "123456789", LocalDate.of(1999, 10, 12), new User(12L, "username", "password"));
        PatientDTO dto = new PatientDTO("Jan", "Nowak", "test@email.com", "123456789", LocalDate.of(1999, 10 , 12));

        when(patientMapper.toPatient(command)).thenReturn(patient);
        when(patientServiceJPA.addPatient(patient)).thenReturn(patient);
        when(patientMapper.toDTO(patient)).thenReturn(dto);

        mockMvc.perform(MockMvcRequestBuilders.post("/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(command)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value(command.getEmail()));
    }

    @Test
    void shouldDeletePatient() throws Exception {
        String email = "test@email.com";
        doNothing().when(patientServiceJPA).deletePatientByEmail(email);

        mockMvc.perform(MockMvcRequestBuilders.delete("/patients/{email}", email))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldUpdatePatient() throws Exception {
        String email = "test@email.com";
        CreatePatientCommand command = new CreatePatientCommand("Jan", "Nowak", "test@email.com", "password", "123456789", LocalDate.of(1999, 10, 12), "ABC123");
        Patient patient = new Patient(1L, "test@email.com", "ABC123", "Jan", "Nowak", "123456789", LocalDate.of(1999, 10, 12), new User(12L, "username", "password"));
        PatientDTO dto = new PatientDTO("Jan", "Nowak", "test@email.com", "123456789", LocalDate.of(1999, 10 , 12));

        when(patientMapper.toPatient(command)).thenReturn(patient);
        when(patientServiceJPA.updatePatient(patient).thenReturn(patient);
        when(patientMapper.toDTO(patient)).thenReturn(dto);

        mockMvc.perform(put("/patients/{email}", email)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(command)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(email));
    }

    @Test
    void shouldChangePassword() throws Exception {
        String email = "test@email.com";
        Password password = new Password("newPassword");
        Patient patient = new Patient(1L, "test@email.com", "ABC123", "Jan", "Nowak", "123456789", LocalDate.of(1999, 10, 12), new User(12L, "username", "password"));
        PatientDTO dto = new PatientDTO("Jan", "Nowak", "test@email.com", "123456789", LocalDate.of(1999, 10 , 12));

        when(patientServiceJPA.changePassword(email, password)).thenReturn(patient);
        when(patientMapper.toDTO(patient)).thenReturn(dto);

        mockMvc.perform(MockMvcRequestBuilders.patch("/patients/{email}/password", email)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(password)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(email));
    }
}