package com.wasilewskyy.medical_clinic.controller;

import com.wasilewskyy.medical_clinic.mapper.PatientMapper;
import com.wasilewskyy.medical_clinic.model.CreatePatientCommand;
import com.wasilewskyy.medical_clinic.model.Password;
import com.wasilewskyy.medical_clinic.model.Patient;
import com.wasilewskyy.medical_clinic.model.PatientDTO;
import com.wasilewskyy.medical_clinic.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/patients")
public class PatientController {

    private final PatientService patientService;

    @GetMapping
    public List<PatientDTO> getAllPatients() {
        return patientService.getAllPatients()
                .stream()
                .map(PatientMapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{email}")
    public PatientDTO getPatientByEmail(@PathVariable String email) {
        return PatientMapper.toDTO(patientService.getPatientByEmail(email));
    }

    @PostMapping
    public PatientDTO addPatient(@RequestBody CreatePatientCommand command) {
        return PatientMapper.toDTO(patientService.addPatient(PatientMapper.toPatient(command)));
    }

    @DeleteMapping("/{email}")
    public void deletePatient(@PathVariable String email) {
        patientService.deletePatientByEmail(email);
    }

    @PutMapping("/{email}")
    public PatientDTO updatePatient(@PathVariable String email, @RequestBody CreatePatientCommand patientCommand) {
        return PatientMapper.toDTO(patientService.updatePatient(email, PatientMapper.toPatient(patientCommand)));

    }

    @PatchMapping("/{email}/password")
    public PatientDTO changePassword(@PathVariable String email, @RequestBody Password password) {
        return PatientMapper.toDTO(patientService.changePassword(email, password));
    }

    @PostMapping
    public PatientDTO createPatient(@RequestBody CreatePatientCommand command) {
        return PatientMapper.toDTO(patientService.addPatient(PatientMapper.toPatient(command)));
    }
}
