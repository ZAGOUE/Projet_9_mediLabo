package com.note_service.repository;

import com.note_service.model.Note;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends MongoRepository<Note, String> {
    List<Note> findByPatientId(String patientId);
    boolean existsByPatientIdAndContenu(String patientId, String contenu);
}
