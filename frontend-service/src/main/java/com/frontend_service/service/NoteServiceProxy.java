package com.frontend_service.service;

import com.frontend_service.model.Note;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class NoteServiceProxy {

    private final RestTemplate restTemplate;

    @Value("${note.service.url}")
    private String noteServiceUrl;

    public NoteServiceProxy(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Note> getNotesByPatientId(Long patientId) {
        HttpHeaders headers = new HttpHeaders();
        // Ajoute l’authentification si nécessaire !
        String auth = "admin:admin123";
        String encodedAuth = java.util.Base64.getEncoder().encodeToString(auth.getBytes());
        headers.add("Authorization", "Basic " + encodedAuth);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<Note[]> response = restTemplate.exchange(
                noteServiceUrl + "/notes/patient/" + patientId,
                HttpMethod.GET,
                entity,
                Note[].class
        );
        Note[] notes = response.getBody();
        return notes != null ? Arrays.asList(notes) : List.of();
    }

    public Note addNoteForPatient(Long patientId, String contenu) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String auth = "admin:admin123";
        String encodedAuth = java.util.Base64.getEncoder().encodeToString(auth.getBytes());
        headers.add("Authorization", "Basic " + encodedAuth);

        String body = String.format("{\"contenu\":\"%s\"}", contenu);

        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        ResponseEntity<Note> response = restTemplate.exchange(
                noteServiceUrl + "/notes/patient/" + patientId,
                HttpMethod.POST,
                entity,
                Note.class
        );
        return response.getBody();
    }

}
