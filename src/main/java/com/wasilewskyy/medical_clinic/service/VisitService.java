package com.wasilewskyy.medical_clinic.service;

import com.wasilewskyy.medical_clinic.dto.CreateVisitCommand;
import com.wasilewskyy.medical_clinic.model.Doctor;
import com.wasilewskyy.medical_clinic.model.Patient;
import com.wasilewskyy.medical_clinic.model.Visit;
import com.wasilewskyy.medical_clinic.repository.DoctorRepository;
import com.wasilewskyy.medical_clinic.repository.PatientRepositoryJPA;
import com.wasilewskyy.medical_clinic.repository.VisitRepository;
import com.wasilewskyy.medical_clinic.validation.VisitValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.wasilewskyy.medical_clinic.validation.VisitValidator.validateVisitTime;

@Service
@RequiredArgsConstructor
public class VisitService {

    private final VisitRepository visitRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepositoryJPA patientRepository;
    private VisitValidator visitValidator;

    @Transactional
    public Visit addVisit(CreateVisitCommand command) {
        validateVisitTime(command.getStartVisitDateTime(), command.getEndVisitDateTime());

        Doctor doctor = doctorRepository.findById(command.getDoctorId())
                .orElseThrow(() -> new IllegalArgumentException("Doctor not found"));

        boolean overlaps = !visitRepository.isOverlapping(
                command.getStartVisitDateTime(),
                command.getEndVisitDateTime(), doctor).isEmpty();

        if (overlaps) {
            throw new IllegalArgumentException("Visit overlaps with an existing one for this doctor");
        }

        Visit visit = Visit.createVisit(doctor, command);
        return visitRepository.save(visit);
    }

    @Transactional
    public Visit assignPatientToVisit(Long visitId, String patientEmail) {
        Visit visit = visitRepository.findById(visitId)
                .orElseThrow(() -> new IllegalArgumentException("Visit not found"));

        if (visit.getStartVisitDateTime().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Cannot assign patient to past visit");
        }

        if (visit.getPatient() != null) {
            throw new IllegalArgumentException("Visit is already taken");
        }

        Patient patient = patientRepository.findByEmail(patientEmail)
                .orElseThrow(() -> new IllegalArgumentException("Patient not found"));

        visit.setPatient(patient);
        return visitRepository.save(visit);
    }

    public Page<Visit> getAllVisits(Pageable pageable) {
        return visitRepository.findAll(pageable);
    }

    public Page<Visit> getVisitsByDoctor(Long doctorId, Pageable pageable) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new IllegalArgumentException("Doctor not found"));
        return visitRepository.findAllByDoctor(doctor, pageable);
    }

    public Page<Visit> getVisitsByPatient(String patientEmail, Pageable pageable) {
        Patient patient = patientRepository.findByEmail(patientEmail)
                .orElseThrow(() -> new IllegalArgumentException("Patient not found"));
        return visitRepository.findAllByPatient(patient, pageable);
    }

    public Page<Visit> getAvailableVisits(Pageable pageable) {
        return visitRepository.findAvailable(LocalDateTime.now(), pageable);
    }
}