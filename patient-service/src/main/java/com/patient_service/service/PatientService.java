package com.patient_service.service;

import com.patient_service.model.Patient;

import java.util.List;

public interface PatientService {
    List<Patient> getAllPatients();
    Patient getPatientById(Long id);
    Patient addPatient(Patient patient);
    Patient updatePatient(Long id, Patient patient);
}

