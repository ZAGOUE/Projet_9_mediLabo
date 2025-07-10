package com.frontend_service.controller;

import com.frontend_service.model.Patient;
import com.frontend_service.model.Note;
import com.frontend_service.service.AssessmentServiceProxy;
import com.frontend_service.service.NoteServiceProxy;
import com.frontend_service.service.PatientServiceProxy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.ui.Model;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class PatientControllerTest {

    @Mock
    private PatientServiceProxy patientServiceProxy;
    @Mock
    private NoteServiceProxy noteServiceProxy;
    @Mock
    private AssessmentServiceProxy assessmentServiceProxy;

    @InjectMocks
    private PatientController patientController;

    @Mock
    private Model model;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllPatients_shouldAddPatientsToModelAndReturnView() {
        // Arrange
        List<Patient> patients = List.of(new Patient(), new Patient());
        when(patientServiceProxy.getAllPatients()).thenReturn(patients);

        // Act
        String view = patientController.getAllPatients(model);

        // Assert
        verify(patientServiceProxy).getAllPatients();
        verify(model).addAttribute(eq("patients"), eq(patients));
        assertThat(view).isEqualTo("patients");
    }

    @Test
    void getPatientDetail_shouldAddPatientNotesAssessmentToModelAndReturnView() {
        // Arrange
        Long id = 1L;
        Patient patient = new Patient();
        List<Note> notes = List.of(new Note());
        String assessment = "Risque faible";
        when(patientServiceProxy.getPatientById(id)).thenReturn(patient);
        when(noteServiceProxy.getNotesByPatientId("1")).thenReturn(notes);
        when(assessmentServiceProxy.getAssessment("1")).thenReturn(assessment);

        // Act
        String view = patientController.getPatientDetail(id, model);

        // Assert
        verify(patientServiceProxy).getPatientById(id);
        verify(noteServiceProxy).getNotesByPatientId("1");
        verify(assessmentServiceProxy).getAssessment("1");
        verify(model).addAttribute("patient", patient);
        verify(model).addAttribute("notes", notes);
        verify(model).addAttribute("assessment", assessment);
        assertThat(view).isEqualTo("patientDetail");
    }

    @Test
    void addNote_shouldCallProxyAndRedirect() {
        // Arrange
        Long id = 1L;
        String contenu = "Nouveau test";
        // Pas de when/thenReturn ici car on ne récupère pas de valeur

        // Act
        String result = patientController.addNote(id, contenu);

        // Assert
        verify(noteServiceProxy).addNoteForPatient("1", contenu);
        assertThat(result).isEqualTo("redirect:/patients/1");
    }

    @Test
    void updatePatient_shouldCallProxyAndRedirect() {
        // Arrange
        Long id = 1L;
        String nom = "Nom";
        String prenom = "Prenom";
        String dateDeNaissance = "2000-01-01";
        String genre = "F";
        String adresse = "Adresse";
        String telephone = "0102030405";

        // Act
        String result = patientController.updatePatient(id, nom, prenom, dateDeNaissance, genre, adresse, telephone);

        // Assert
        verify(patientServiceProxy).updatePatient(id, nom, prenom, dateDeNaissance, genre, adresse, telephone);
        assertThat(result).isEqualTo("redirect:/patients/1");
    }
}
