package com.assessment.service;

import com.assessment.model.Note;
import com.assessment.model.Patient;
import com.assessment.proxy.NoteProxy;
import com.assessment.proxy.PatientProxy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AssessmentServiceTest {

    @Mock
    private PatientProxy patientProxy;

    @Mock
    private NoteProxy noteProxy;

    @InjectMocks
    private AssessmentService assessmentService;

    @Test
    public void testAssessRisk_WithTriggers() {
        Long patientId = 1L;

        // Mock patient
        Patient mockPatient = new Patient();
        mockPatient.setDateDeNaissance("1980-01-01");
        mockPatient.setGenre("M");

        // Mock notes contenant des déclencheurs
        Note note1 = new Note();
        note1.setContenu("Hémoglobine A1C élevé");
        Note note2 = new Note();
        note2.setContenu("Patient fumeur avec symptômes anormaux");

        when(patientProxy.getPatientById(patientId)).thenReturn(mockPatient);
        when(noteProxy.getNotesByPatientId(patientId)).thenReturn(List.of(note1, note2));

        String risk = assessmentService.assessRisk(patientId);

        // Selon la logique, avec ces déclencheurs et âge, risque doit être "Borderline" (ou autre selon ta logique)
        assertThat(risk).isEqualTo("Borderline");
    }

    @Test
    public void testAssessRisk_NoTriggers() {
        Long patientId = 2L;

        Patient mockPatient = new Patient();
        mockPatient.setDateDeNaissance("1990-01-01");
        mockPatient.setGenre("F");

        Note note = new Note();
        note.setContenu("Aucun symptôme notable");

        when(patientProxy.getPatientById(patientId)).thenReturn(mockPatient);
        when(noteProxy.getNotesByPatientId(patientId)).thenReturn(List.of(note));

        String risk = assessmentService.assessRisk(patientId);

        assertThat(risk).isEqualTo("None");
    }
}
