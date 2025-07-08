package com.assessment.proxy;

import com.assessment.model.Note;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "note-service", url = "${note.service.url}")
public interface NoteProxy {
    @GetMapping("/notes/patient/{patientId}")
    List<Note> getNotesByPatientId(@PathVariable("patientId") Long patientId);
}
