package com.frontend_service.service;

import com.frontend_service.model.Patient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.util.Arrays;
import java.util.Base64;
import java.util.List;

@Service
public class PatientServiceProxy {

    private final RestTemplate restTemplate;

    @Value("${patient.service.url}")
    private String patientServiceUrl;

    public PatientServiceProxy(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Patient> getAllPatients() {
        HttpHeaders headers = new HttpHeaders();
        String auth = "admin:admin123";
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());

        headers.add("Authorization", "Basic " + encodedAuth);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<Patient[]> response = restTemplate.exchange(
                patientServiceUrl + "/patients",
                HttpMethod.GET,
                entity,
                Patient[].class
        );
        Patient[] patients = response.getBody();
        return patients != null ? Arrays.asList(patients) : List.of();
    }

    public Patient getPatientById(Long id) {
        HttpHeaders headers = new HttpHeaders();
        String auth = "admin:admin123";
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());

        headers.add("Authorization", "Basic " + encodedAuth);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<Patient> response = restTemplate.exchange(
                patientServiceUrl + "/patients/" + id,
                HttpMethod.GET,
                entity,
                Patient.class
        );
        return response.getBody();
    }
    public void updatePatient(Long id, String nom, String prenom, String dateDeNaissance, String genre, String adresse, String telephone) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String auth = "admin:admin123";
        String encodedAuth = java.util.Base64.getEncoder().encodeToString(auth.getBytes());
        headers.add("Authorization", "Basic " + encodedAuth);

        String body = String.format(
                "{\"nom\":\"%s\", \"prenom\":\"%s\", \"dateDeNaissance\":\"%s\", \"genre\":\"%s\", \"adresse\":\"%s\", \"telephone\":\"%s\"}",
                nom, prenom, dateDeNaissance, genre, adresse, telephone
        );
        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        restTemplate.exchange(
                patientServiceUrl + "/patients/" + id,
                HttpMethod.PUT,
                entity,
                Void.class
        );
    }

}
