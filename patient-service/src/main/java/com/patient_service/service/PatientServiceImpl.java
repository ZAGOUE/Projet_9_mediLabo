package com.patient_service.service;

import com.patient_service.model.Patient;
import com.patient_service.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;

    @Override
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    @Override
    public Patient getPatientById(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient non trouvé"));
    }

    @Override
    public Patient addPatient(Patient patient) {
        return patientRepository.save(patient);
    }

    @Override
    public Patient updatePatient(Long id, Patient patient) {
        Patient existing = getPatientById(id);
        existing.setPrenom(patient.getPrenom());
        existing.setNom(patient.getNom());
        existing.setDateDeNaissance(patient.getDateDeNaissance());
        existing.setGenre(patient.getGenre());
        existing.setAdresse(patient.getAdresse());
        existing.setTelephone(patient.getTelephone());
        return patientRepository.save(existing);
    }
}

