package com.assessment.service;

import com.assessment.model.Note;
import com.assessment.model.Patient;
import com.assessment.proxy.NoteProxy;
import com.assessment.proxy.PatientProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
public class AssessmentService {

    @Autowired
    private PatientProxy patientProxy;

    @Autowired
    private NoteProxy noteProxy;

    // Liste des déclencheurs à rechercher
    private static final List<String> TRIGGERS = List.of(
            "Hémoglobine A1C", "Microalbumine", "Taille", "Poids", "Fumeur", "Fumeuse",
            "Anormal", "Cholestérol", "Vertiges", "Rechute", "Réaction", "Anticorps"
    );

    public String assessRisk(Long patientId) {
        // 1. Récupérer le patient via le proxy
        Patient patient = patientProxy.getPatientById(patientId);

        // 2. Récupérer ses notes via le proxy
        List<Note> notes = noteProxy.getNotesByPatientId(patientId);

        // 3. Compter le nombre de déclencheurs dans les notes
        int triggerCount = countTriggers(notes);

        // 4. Calculer l’âge
        int age = calculateAge(patient.getDateDeNaissance());

        // 5. Évaluer le risque
        return evaluateRisk(age, patient.getGenre(), triggerCount);
    }


    private int countTriggers(List<Note> notes) {
        int count = 0;
        for (Note note : notes) {
            String contenu = note.getContenu();

            if (contenu == null) continue; // 🔒 Sécurité

            for (String trigger : TRIGGERS) {
                if (contenu.toLowerCase().contains(trigger.toLowerCase())) {
                    count++;
                }
            }
        }
        return count;
    }

    private int calculateAge(String birthDate) {
        LocalDate birth = LocalDate.parse(birthDate);
        return Period.between(birth, LocalDate.now()).getYears();
    }


    private String evaluateRisk(int age, String gender, int triggers) {
        if (triggers == 0) return "None";

        if (age > 30) {
            if (triggers >= 8) return "Early onset";
            if (triggers >= 6) return "In Danger";
            if (triggers >= 2) return "Borderline";
        } else {
            if ("M".equalsIgnoreCase(gender)) {
                if (triggers >= 5) return "Early onset";
                if (triggers >= 3) return "In Danger";
            } else {
                if (triggers >= 7) return "Early onset";
                if (triggers >= 4) return "In Danger";
            }
        }

        return "None";
    }
}
