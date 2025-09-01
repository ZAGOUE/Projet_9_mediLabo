package com.frontend_service.service;
import com.frontend_service.model.Note;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class NoteServiceProxyTest {

    @Mock
    private RestTemplate restTemplate;

    private NoteServiceProxy noteServiceProxy;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        noteServiceProxy = new NoteServiceProxy(restTemplate);
        // Simule la valeur du @Value("${gateway.url}")
        org.springframework.test.util.ReflectionTestUtils.setField(noteServiceProxy, "gatewayUrl", "http://localhost:8080");
    }

    @Test
    void getNotesByPatientId_shouldAddAuthorizationHeaderAndReturnList() {
        // Arrange
        Note[] fakeNotes = {new Note("id1", "pat1", "Contenu 1")};
        ResponseEntity<Note[]> fakeResponse = new ResponseEntity<>(fakeNotes, HttpStatus.OK);

        when(restTemplate.exchange(
                eq("http://localhost:8080/notes/patient/1"),
                eq(HttpMethod.GET),
                argThat((HttpEntity<Void> entity) -> {
                    HttpHeaders h = entity.getHeaders();
                    String authHeader = h.getFirst(HttpHeaders.AUTHORIZATION);
                    // Vérifie le header Basic
                    return authHeader != null && authHeader.startsWith("Basic ");
                }),
                eq(Note[].class)
        )).thenReturn(fakeResponse);

        // Act
        List<Note> result = noteServiceProxy.getNotesByPatientId("1");

        // Assert
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getContenu()).isEqualTo("Contenu 1");
        verify(restTemplate).exchange(
                eq("http://localhost:8080/notes/patient/1"),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(Note[].class)
        );
    }

    @Test
    void addNoteForPatient_shouldAddAuthorizationHeaderAndPost() {
        // Arrange
        Note fakeNote = new Note("id2", "pat1", "Nouveau contenu");
        ResponseEntity<Note> fakeResponse = new ResponseEntity<>(fakeNote, HttpStatus.OK);

        when(restTemplate.exchange(
                eq("http://localhost:8080/notes/patient/1"),
                eq(HttpMethod.POST),
                argThat((HttpEntity<String> entity) -> {
                    HttpHeaders h = entity.getHeaders();
                    String authHeader = h.getFirst(HttpHeaders.AUTHORIZATION);
                    String contentType = Objects.requireNonNull(h.getContentType()).toString();
                    String body = entity.getBody();
                    // Vérifie l’entête d’authentification et le content-type
                    if (authHeader == null || !authHeader.startsWith("Basic ")
                            || !contentType.equals(MediaType.APPLICATION_JSON_VALUE)) return false;
                    Assertions.assertNotNull(body);
                    return body.contains("Nouveau contenu");
                }),
                eq(Note.class)
        )).thenReturn(fakeResponse);

        // Act
        Note result = noteServiceProxy.addNoteForPatient("1", "Nouveau contenu");

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo("id2");
        assertThat(result.getContenu()).isEqualTo("Nouveau contenu");
        verify(restTemplate).exchange(
                eq("http://localhost:8080/notes/patient/1"),
                eq(HttpMethod.POST),
                any(HttpEntity.class),
                eq(Note.class)
        );
    }
}
