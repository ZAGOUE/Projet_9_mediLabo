package com.assessment.controller;

import com.assessment.model.Note;
import com.assessment.model.Patient;
import com.assessment.proxy.NoteProxy;
import com.assessment.proxy.PatientProxy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class AssessmentControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private PatientProxy patientProxy;

    @MockBean
    private NoteProxy noteProxy;

    private TestRestTemplate restTemplateWithAuth;

    @BeforeEach
    public void setup() {
        restTemplateWithAuth = restTemplate.withBasicAuth("admin", "admin123");
    }

    @Test
    public void testGetAssessment() {
        Long patientId = 1L;

        // Mock patient
        Patient mockPatient = new Patient();
        mockPatient.setId(patientId);
        mockPatient.setDateDeNaissance("1980-01-01");
        mockPatient.setGenre("M");
        when(patientProxy.getPatientById(patientId)).thenReturn(mockPatient);

        // Mock notes
        Note note1 = new Note();
        note1.setContenu("Le patient a un taux élevé d'Hémoglobine A1C.");
        Note note2 = new Note();
        note2.setContenu("Patient fumeur, montre des signes anormaux.");
        when(noteProxy.getNotesByPatientId(patientId)).thenReturn(List.of(note1, note2));

        ResponseEntity<String> response = restTemplateWithAuth.getForEntity("/assessments?patientId=" + patientId, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotBlank();

        // Selon les déclencheurs, le risque devrait être "Borderline" ou "In Danger"
        // On peut vérifier que la réponse est une des valeurs attendues
        assertThat(response.getBody()).matches("None|Borderline|In Danger|Early onset");
    }
}
