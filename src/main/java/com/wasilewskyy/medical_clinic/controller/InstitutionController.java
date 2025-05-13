package com.wasilewskyy.medical_clinic.controller;

import com.wasilewskyy.medical_clinic.exception.ErrorMessage;
import com.wasilewskyy.medical_clinic.mapper.InstitutionMapper;
import com.wasilewskyy.medical_clinic.dto.InstitutionDTO;
import com.wasilewskyy.medical_clinic.service.InstitutionService;
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
@RequestMapping("/institutions")
@RequiredArgsConstructor
@Tag(name = "Institutions", description = "Institution managment API")
public class InstitutionController {

    private final InstitutionService institutionService;
    private final InstitutionMapper institutionMapper;

    @Operation(summary = "Get all institutions", description = "Returns a list of all institutions.")
    @ApiResponse(responseCode = "200", description = "List of institutions",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = InstitutionDTO.class)))
    @GetMapping
    public List<InstitutionDTO> getAll() {
        return institutionService.getAll().stream()
                .map(institutionMapper::toDTO)
                .toList();
    }


    @Operation(summary = "Get institution by id", description = "Returns a single institution by id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Institution found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = InstitutionDTO.class))),
            @ApiResponse(responseCode = "404", description = "Institution not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
    })
    @GetMapping("/{id}")
    public InstitutionDTO getById(@PathVariable Long id) {
        return institutionMapper.toDTO(institutionService.getById(id));
    }

    @Operation(summary = "Create a new institution", description = "Creates a new institution in the system.")
    @ApiResponse(responseCode = "201", description = "Institution created",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = InstitutionDTO.class)))
    @PostMapping
    public InstitutionDTO create(@RequestBody InstitutionDTO dto) {
        return institutionMapper.toDTO(institutionService.save(institutionMapper.toEntity(dto)));
    }

    @Operation(summary = "Update a institution by id", description = "Updates an existing institution by id.")
    @ApiResponse(responseCode = "200", description = "Institution updated",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = InstitutionDTO.class)))
    @PutMapping("/{id}")
    public InstitutionDTO update(@PathVariable Long id, @RequestBody InstitutionDTO dto) {
        return institutionMapper.toDTO(institutionService.update(id, institutionMapper.toEntity(dto)));
    }

    @Operation(summary = "Delete a institution by id", description = "Deletes the institution by id.")
    @ApiResponse(responseCode = "204", description = "Institution deleted")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        institutionService.delete(id);
    }
}
