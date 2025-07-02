package com.frontend_service.controller;

import com.frontend_service.model.Patient;
import com.frontend_service.model.Note;
import com.frontend_service.service.AssessmentServiceProxy;
import com.frontend_service.service.PatientServiceProxy;
import com.frontend_service.service.NoteServiceProxy; // <-- ajoute ceci
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientServiceProxy patientServiceProxy;
    private final NoteServiceProxy noteServiceProxy; // <-- injecte ton proxy notes
    private final AssessmentServiceProxy assessmentServiceProxy;

    @GetMapping
    public String getAllPatients(Model model) {
        List<Patient> patients = patientServiceProxy.getAllPatients();
        model.addAttribute("patients", patients);
        return "patients";
    }

    @GetMapping("/{id}")
    public String getPatientDetail(@PathVariable Long id, Model model) {
        Patient patient = patientServiceProxy.getPatientById(id);
        List<Note> notes = noteServiceProxy.getNotesByPatientId(id);

        // Récupère l'évaluation via le proxy assessment-service
        String assessment = assessmentServiceProxy.getAssessment(id);

        model.addAttribute("patient", patient);
        model.addAttribute("notes", notes);
        model.addAttribute("assessment", assessment);
        return "patientDetail";
    }


    @PostMapping("/{id}/notes") // <-- bon mapping
    public String addNote(@PathVariable Long id, @RequestParam String contenu) {
        noteServiceProxy.addNoteForPatient(id, contenu); // <-- tu appelleras cette méthode (à faire !)
        return "redirect:/patients/" + id;
    }
    @PostMapping("/{id}/edit")
    public String updatePatient(@PathVariable Long id,
                                @RequestParam String nom,
                                @RequestParam String prenom,
                                @RequestParam String dateDeNaissance,
                                @RequestParam String genre,
                                @RequestParam String adresse,
                                @RequestParam String telephone) {
        patientServiceProxy.updatePatient(id, nom, prenom, dateDeNaissance, genre, adresse, telephone);
        return "redirect:/patients/" + id;
    }

}
