package com.wasilewskyy.medical_clinic.controller;

import com.wasilewskyy.medical_clinic.mapper.PatientMapper;
import com.wasilewskyy.medical_clinic.model.CreatePatientCommand;
import com.wasilewskyy.medical_clinic.model.Password;
import com.wasilewskyy.medical_clinic.model.PatientDTO;
import com.wasilewskyy.medical_clinic.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/patients")
@Tag(name = "Patients", description = "Patient management API")
public class PatientController {

    private final PatientService patientService;
    private final PatientMapper patientMapper;

    @Operation(summary = "Get all patients", description = "Returns a list of all patients.")
    @ApiResponse(responseCode = "200", description = "List of patients",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = PatientDTO.class)))
    @GetMapping
    public List<PatientDTO> getAllPatients() {
        return patientService.getAllPatients()
                .stream()
                .map(patientMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Get patient by email", description = "Returns a single patient by email.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Patient found",
                    content = @Content(schema = @Schema(implementation = PatientDTO.class))),
            @ApiResponse(responseCode = "404", description = "Patient not found")
    })
    @GetMapping("/{email}")
    public PatientDTO getPatientByEmail(@PathVariable String email) {
        return patientMapper.toDTO(patientService.getPatientByEmail(email));
    }

    @Operation(summary = "Add a new patient", description = "Creates a new patient in the system.")
    @ApiResponse(responseCode = "201", description = "Patient created",
            content = @Content(schema = @Schema(implementation = PatientDTO.class)))
    @PostMapping
    public PatientDTO addPatient(@RequestBody CreatePatientCommand command) {
        return patientMapper.toDTO(patientService.addPatient(patientMapper.toPatient(command)));
    }

    @Operation(summary = "Delete a patient by email", description = "Deletes the patient by email.")
    @ApiResponse(responseCode = "204", description = "Patient deleted")
    @DeleteMapping("/{email}")
    public void deletePatient(@PathVariable String email) {
        patientService.deletePatientByEmail(email);
    }

    @Operation(summary = "Update a patient by email", description = "Updates an existing patient by email.")
    @ApiResponse(responseCode = "200", description = "Patient updated",
            content = @Content(schema = @Schema(implementation = PatientDTO.class)))
    @PutMapping("/{email}")
    public PatientDTO updatePatient(@PathVariable String email, @RequestBody CreatePatientCommand patientCommand) {
        return patientMapper.toDTO(patientService.updatePatient(email, patientMapper.toPatient(patientCommand)));

    }

    @Operation(summary = "Change patient's password", description = "Updates only the patient's password.")
    @ApiResponse(responseCode = "200", description = "Password updated",
            content = @Content(schema = @Schema(implementation = PatientDTO.class)))
    @PatchMapping("/{email}/password")
    public PatientDTO changePassword(@PathVariable String email, @RequestBody Password password) {
        return patientMapper.toDTO(patientService.changePassword(email, password));
    }
}
