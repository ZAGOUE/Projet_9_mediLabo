package com.note_service.controller;

import com.note_service.model.Note;
import com.note_service.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/notes")
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    // GET /notes/patient/{patientId}
    @GetMapping("/patient/{patientId}")
    public List<Note> getNotesByPatientId(@PathVariable String patientId) {
        return noteService.getNotesByPatientId(patientId);
    }

    // POST /notes/patient/{patientId}
    @PostMapping("/patient/{patientId}")
    public ResponseEntity<?> addNoteForPatient(
            @PathVariable String patientId,
            @RequestBody Map<String, String> body
    ) {
        String contenu = body.get("contenu");
        try {
            Note note = noteService.addNoteForPatient(patientId, contenu);
            return ResponseEntity.ok(note);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @DeleteMapping("/{noteId}")
    public ResponseEntity<Void> deleteNote(@PathVariable String noteId) {
        noteService.deleteNoteById(noteId);
        return ResponseEntity.noContent().build();
    }
    // Pour lister toutes les notes
    @GetMapping
    public List<Note> getAllNotes() {
        return noteService.getAllNotes();
    }

}
