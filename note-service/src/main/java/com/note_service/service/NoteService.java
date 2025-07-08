package com.note_service.service;

import com.note_service.model.Note;

import java.util.List;

public interface NoteService {
    List<Note> getNotesByPatientId(String patientId);
    Note addNoteForPatient(String patientId, String contenu);
    void deleteNoteById(String noteId);
    List<Note> getAllNotes();

}
