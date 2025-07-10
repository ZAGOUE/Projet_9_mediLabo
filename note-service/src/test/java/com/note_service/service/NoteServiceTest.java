package com.note_service.service;

import com.note_service.model.Note;
import com.note_service.repository.NoteRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class NoteServiceTest {
    @Mock
    private NoteRepository noteRepository;  // Mock de la dépendance

    @InjectMocks
    private NoteServiceImpl noteService;  // La classe à tester

    @Test
    public void testDeleteNoteById() {
        String noteId = "123";

        // Simuler le comportement de la méthode existsById
        when(noteRepository.existsById(noteId)).thenReturn(true);

        // Appel de la méthode à tester
        noteService.deleteNoteById(noteId);

        // Vérifier que deleteById a bien été appelé
        verify(noteRepository).deleteById(noteId);
    }

    @Test
    public void testGetAllNotes() {
        // Créer des notes factices pour le test
        Note note1 = new Note("1", "Patient1", "Contenu1", null);
        Note note2 = new Note("2", "Patient2", "Contenu2", null);

        // Simuler le comportement de la méthode findAll
        when(noteRepository.findAll()).thenReturn(Arrays.asList(note1, note2));

        // Appel de la méthode à tester
        List<Note> notes = noteService.getAllNotes();

        // Vérifier que la liste retournée contient bien les deux notes
        assertEquals(2, notes.size());
        assertEquals("Patient1", notes.get(0).getPatientId());
        assertEquals("Patient2", notes.get(1).getPatientId());
    }

}
