package com.frontend_service.model;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.assertj.core.api.Assertions.assertThat;

public class PatientModelTest {

    @Test
    void testPatientNoArgsConstructorAndSetters() {
        Patient patient = new Patient();
        patient.setId(1L);
        patient.setPrenom("Jean");
        patient.setNom("Dupont");
        patient.setDateDeNaissance(LocalDate.of(1980, 5, 20));
        patient.setGenre("M");
        patient.setAdresse("1 rue Paris");
        patient.setTelephone("0601020304");

        assertThat(patient.getId()).isEqualTo(1L);
        assertThat(patient.getPrenom()).isEqualTo("Jean");
        assertThat(patient.getNom()).isEqualTo("Dupont");
        assertThat(patient.getDateDeNaissance()).isEqualTo(LocalDate.of(1980, 5, 20));
        assertThat(patient.getGenre()).isEqualTo("M");
        assertThat(patient.getAdresse()).isEqualTo("1 rue Paris");
        assertThat(patient.getTelephone()).isEqualTo("0601020304");
    }

    @Test
    void testPatientAllArgsConstructor() {
        LocalDate dob = LocalDate.of(1995, 1, 15);
        Patient patient = new Patient(2L, "Marie", "Curie", dob, "F", "2 rue Lyon", "0708091011");

        assertThat(patient.getId()).isEqualTo(2L);
        assertThat(patient.getPrenom()).isEqualTo("Marie");
        assertThat(patient.getNom()).isEqualTo("Curie");
        assertThat(patient.getDateDeNaissance()).isEqualTo(dob);
        assertThat(patient.getGenre()).isEqualTo("F");
        assertThat(patient.getAdresse()).isEqualTo("2 rue Lyon");
        assertThat(patient.getTelephone()).isEqualTo("0708091011");
    }

    @Test
    void testEqualsAndHashCode() {
        LocalDate dob = LocalDate.of(2000, 1, 1);
        Patient p1 = new Patient(3L, "Test", "Case", dob, "F", "Rue X", "0123456789");
        Patient p2 = new Patient(3L, "Test", "Case", dob, "F", "Rue X", "0123456789");

        assertThat(p1).isEqualTo(p2);
        assertThat(p1.hashCode()).isEqualTo(p2.hashCode());
    }
}
