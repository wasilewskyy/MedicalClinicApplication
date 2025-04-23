package com.wasilewskyy.medical_clinic.controller;

import com.wasilewskyy.medical_clinic.exception.ErrorMessage;
import com.wasilewskyy.medical_clinic.mapper.DoctorMapper;
import com.wasilewskyy.medical_clinic.model.DoctorDTO;
import com.wasilewskyy.medical_clinic.service.DoctorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctors")
@RequiredArgsConstructor
@Tag(name = "Doctors", description = "Doctor managment API")
public class DoctorController {

    private final DoctorService doctorService;
    private final DoctorMapper doctorMapper;

    @Operation(summary = "Get all doctors", description = "Returns a list of all doctors.")
    @ApiResponse(responseCode = "200", description = "List of doctors",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = DoctorDTO.class)))
    @GetMapping
    public List<DoctorDTO> getAll() {
        return doctorService.getAll().stream()
                .map(doctorMapper::toDTO)
                .toList();
    }

    @Operation(summary = "Get doctor by id", description = "Returns a single doctor by id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Doctor found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DoctorDTO.class))),
            @ApiResponse(responseCode = "404", description = "Doctor not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
    })
    @GetMapping("/{id}")
    public DoctorDTO getById(@PathVariable Long id) {
        return doctorMapper.toDTO(doctorService.getById(id));
    }

    @Operation(summary = "Create a new doctor", description = "Creates a new doctor in the system.")
    @ApiResponse(responseCode = "201", description = "Doctor created",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = DoctorDTO.class)))
    @PostMapping
    public DoctorDTO create(@RequestBody DoctorDTO dto) {
        return doctorMapper.toDTO(doctorService.save(doctorMapper.toEntity(dto)));
    }

    @Operation(summary = "Update a doctor by id", description = "Updates an existing doctor by id.")
    @ApiResponse(responseCode = "200", description = "Doctor updated",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = DoctorDTO.class)))
    @PutMapping("/{id}")
    public DoctorDTO update(@PathVariable Long id, @RequestBody DoctorDTO dto) {
        return doctorMapper.toDTO(doctorService.update(id, doctorMapper.toEntity(dto)));
    }

    @Operation(summary = "Delete a doctor by id", description = "Deletes the doctor by id.")
    @ApiResponse(responseCode = "204", description = "Doctor deleted")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        doctorService.delete(id);
    }
}