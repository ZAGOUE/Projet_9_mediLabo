package com.note_service.controller;

import com.note_service.model.Note;
import com.note_service.repository.NoteRepository;
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

    @Autowired
    private NoteRepository noteRepository;

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

    @Test
    void deleteNote_shouldReturnNoContent() {
        // Préparer une note en base
        Note note = new Note("id1", "pat1", "contenu", null);
        noteRepository.save(note);

        // Effectuer la requête DELETE
        ResponseEntity<Void> response = restTemplateWithAuth.exchange(
                "/notes/{noteId}",
                HttpMethod.DELETE,
                null,
                Void.class,
                "id1"
        );
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        // Vérifier que la note n'existe plus
        assertThat(noteRepository.existsById("id1")).isFalse();
    }
    @Test
    void getAllNotes_shouldReturnListOfNotes() {
        // Nettoyer et insérer des notes
        noteRepository.deleteAll();
        Note note1 = new Note("id1", "pat1", "contenu1", null);
        Note note2 = new Note("id2", "pat2", "contenu2", null);
        noteRepository.save(note1);
        noteRepository.save(note2);

        ResponseEntity<List<Note>> response = restTemplateWithAuth.exchange(
                "/notes", // adapte le mapping s’il est /notes ou /api/notes
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(2);
        assertThat(response.getBody()).extracting(Note::getPatientId)
                .containsExactlyInAnyOrder("pat1", "pat2");
    }

}
