package com.frontend_service.model;
import com.frontend_service.model.Note;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class NoteModelTest {

    @Test
    void testNoArgsConstructorAndSetters() {
        Note note = new Note();
        note.setId("id1");
        note.setPatientId("pat1");
        note.setContenu("Contenu test");

        assertThat(note.getId()).isEqualTo("id1");
        assertThat(note.getPatientId()).isEqualTo("pat1");
        assertThat(note.getContenu()).isEqualTo("Contenu test");
    }

    @Test
    void testAllArgsConstructor() {
        Note note = new Note("id2", "pat2", "Autre contenu");

        assertThat(note.getId()).isEqualTo("id2");
        assertThat(note.getPatientId()).isEqualTo("pat2");
        assertThat(note.getContenu()).isEqualTo("Autre contenu");
    }

    @Test
    void testEqualsAndHashCode() {
        Note n1 = new Note("id3", "pat3", "Coucou");
        Note n2 = new Note("id3", "pat3", "Coucou");

        assertThat(n1).isEqualTo(n2);
        assertThat(n1.hashCode()).isEqualTo(n2.hashCode());
    }
}
