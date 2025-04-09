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

@RestController
@RequiredArgsConstructor
@RequestMapping("/patients")
public class PatientController {

    private final PatientService patientService;
    private final PatientMapper patientMapper;

    @GetMapping
    public List<Patient> getAllPatients() {
        return patientService.getAllPatients();
    }

    @GetMapping("/{email}")
    public Patient getPatientByEmail(@PathVariable String email) {
        return patientService.getPatientByEmail(email);
    }

    @PostMapping
    public Patient addPatient(@RequestBody Patient patient) {
        return patientService.addPatient(patient);
    }

    @DeleteMapping("/{email}")
    public void deletePatient(@PathVariable String email) {
        patientService.deletePatientByEmail(email);
    }

    @PutMapping("/{email}")
    public Patient updatePatient(@PathVariable String email, @RequestBody Patient patient) {
        return patientService.updatePatient(email, patient);

    }

    @PatchMapping("/{email}/password")
    public Patient changePassword(@PathVariable String email, @RequestBody Password password) {
        return patientService.changePassword(email, password);
    }

    @PostMapping
    public PatientDTO createPatient(@RequestBody CreatePatientCommand command) {
        return patientService.createPatient(command);
    }
}
