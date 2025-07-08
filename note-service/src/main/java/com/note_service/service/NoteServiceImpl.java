package com.note_service.service;

import com.note_service.model.Note;
import com.note_service.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;

    @Override
    public List<Note> getNotesByPatientId(String patientId) {
        return noteRepository.findByPatientId(patientId);
    }

    @Override
    public Note addNoteForPatient(String patientId, String contenu) {
        // Vérifier si la note existe déjà
        boolean exists = noteRepository.existsByPatientIdAndContenu(patientId, contenu);
        if (exists) {
            throw new IllegalArgumentException("Une note identique existe déjà pour ce patient.");
        }
        Note note = new Note();
        note.setPatientId(patientId);
        note.setContenu(contenu);
        note.setDate(LocalDateTime.now());
        return noteRepository.save(note);
    }

    @Override
    public void deleteNoteById(String noteId) {
        System.out.println("Suppression note id = " + noteId);
        System.out.println("Existe ? " + noteRepository.existsById(noteId));


        noteRepository.deleteById(noteId);
    }
    @Override
    public List<Note> getAllNotes() {
        return noteRepository.findAll();
    }

}
