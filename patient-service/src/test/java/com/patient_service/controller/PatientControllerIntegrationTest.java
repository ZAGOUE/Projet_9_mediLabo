package com.patient_service.controller;

import com.patient_service.model.Patient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PatientControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;
    private TestRestTemplate restTemplateWithAuth;

    @BeforeEach
    public void setup() {
        restTemplateWithAuth = restTemplate.withBasicAuth("admin", "admin123");
    }

    @Test
    public void testGetAllPatients() {
        ResponseEntity<List<Patient>> response = restTemplateWithAuth.exchange(
                "/patients",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}

        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    public void testAddAndGetPatient() {
        Patient newPatient = new Patient();
        newPatient.setNom("Dupont");
        newPatient.setPrenom("Jean");
        // Remplir les autres champs nécessaires

        ResponseEntity<Patient> postResponse = restTemplateWithAuth.postForEntity("/patients", newPatient, Patient.class);
        assertThat(postResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Patient createdPatient = postResponse.getBody();
        assertThat(createdPatient).isNotNull();
        assertThat(createdPatient.getId()).isNotNull();

        Long id = createdPatient.getId();

        ResponseEntity<Patient> getResponse = restTemplateWithAuth.getForEntity("/patients/" + id, Patient.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody()).isNotNull();
        assertThat(getResponse.getBody().getNom()).isEqualTo("Dupont");
    }

    @Test
    public void testUpdatePatient() {
        // Création d’un patient à modifier
        Patient patient = new Patient();
        patient.setNom("Martin");
        patient.setPrenom("Alice");
        ResponseEntity<Patient> postResponse = restTemplateWithAuth.postForEntity("/patients", patient, Patient.class);
        assertThat(postResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Patient createdPatient = postResponse.getBody();
        assertThat(createdPatient).isNotNull();

        Long id = createdPatient.getId();

        // Mise à jour du patient
        Patient updatedPatient = new Patient();
        updatedPatient.setNom("Martin");
        updatedPatient.setPrenom("Alicia"); // prénom modifié

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Patient> requestEntity = new HttpEntity<>(updatedPatient, headers);

        ResponseEntity<Patient> putResponse = restTemplateWithAuth.exchange("/patients/" + id, HttpMethod.PUT, requestEntity, Patient.class);
        assertThat(putResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Patient patientUpdated = putResponse.getBody();
        assertThat(patientUpdated).isNotNull();
        assertThat(patientUpdated.getPrenom()).isEqualTo("Alicia");
    }
}


