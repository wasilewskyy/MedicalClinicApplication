package com.wasilewskyy.medical_clinic.service;

import com.wasilewskyy.medical_clinic.model.Institution;
import com.wasilewskyy.medical_clinic.repository.InstitutionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InstitutionService {

    private final InstitutionRepository institutionRepository;

    public List<Institution> getAll() {
        return institutionRepository.findAll();
    }

    public Institution getById(Long id) {
        return institutionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Institution not found"));
    }

    public Institution save(Institution institution) {
        return institutionRepository.save(institution);
    }

    public void delete(Long id) {
        institutionRepository.deleteById(id);
    }

    public Institution update(Long id, Institution updated) {
        Institution existing = getById(id);
        updated.setId(existing.getId());
        return institutionRepository.save(updated);
    }
}