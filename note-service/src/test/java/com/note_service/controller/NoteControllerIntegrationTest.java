package com.note_service.controller;

import com.note_service.model.Note;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NoteControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private TestRestTemplate restTemplateWithAuth;

    @BeforeEach
    public void setup() {
        // Adapte le username et mot de passe selon ta config Security
        restTemplateWithAuth = restTemplate.withBasicAuth("admin", "admin123");
    }

    @Test
    public void testGetNotesByPatientId() {
        String patientId = "123"; // adapte selon un patient existant en base ou mocké

        ResponseEntity<List<Note>> response = restTemplateWithAuth.exchange(
                "/notes/patient/" + patientId,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    public void testAddNoteForPatient() {
        String patientId = UUID.randomUUID().toString(); // patientId unique à chaque run

        Map<String, String> body = Map.of("contenu", "Note importante pour test");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);

        ResponseEntity<Note> response = restTemplateWithAuth.postForEntity("/notes/patient/" + patientId, request, Note.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Note createdNote = response.getBody();
        assertThat(createdNote).isNotNull();
        assertThat(createdNote.getPatientId()).isEqualTo(patientId);
        assertThat(createdNote.getContenu()).isEqualTo("Note importante pour test");
    }

}
