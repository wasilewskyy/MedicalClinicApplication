package com.wasilewskyy.medical_clinic.controller;

import com.wasilewskyy.medical_clinic.dto.CreateVisitCommand;
import com.wasilewskyy.medical_clinic.dto.VisitDTO;
import com.wasilewskyy.medical_clinic.mapper.VisitMapper;
import com.wasilewskyy.medical_clinic.service.VisitService;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/visits")
public class VisitController {

    private final VisitService visitService;
    private final VisitMapper visitMapper;

    @PostMapping("/doctor/{doctorId}")
    public VisitDTO createVisit(@PathVariable Long doctorId, @RequestBody CreateVisitCommand command) {
        return visitMapper.toDTO(visitService.addVisit(command, doctorId));
    }

    @PostMapping("/{visitId}/assign")
    public VisitDTO assignPatient(@PathVariable Long visitId, @RequestParam String email) {
        return visitMapper.toDTO(visitService.assignPatientToVisit(visitId, email));
    }

    @GetMapping
    public Page<VisitDTO> getAllVisits(@ParameterObject Pageable pageable) {
        return visitService.getAllVisits(pageable).map(visitMapper::toDTO);
    }

    @GetMapping("/available")
    public Page<VisitDTO> getAvailableVisits(@ParameterObject Pageable pageable) {
        return visitService.getAvailableVisits(pageable).map(visitMapper::toDTO);
    }

    @GetMapping("/doctor/{doctorId}")
    public Page<VisitDTO> getVisitsByDoctor(@PathVariable Long doctorId, @ParameterObject Pageable pageable) {
        return visitService.getVisitsByDoctor(doctorId, pageable).map(visitMapper::toDTO);
    }

    @GetMapping("/patient/{email}")
    public Page<VisitDTO> getVisitsByPatient(@PathVariable String email, @ParameterObject Pageable pageable) {
        return visitService.getVisitsByPatient(email, pageable).map(visitMapper::toDTO);
    }
}