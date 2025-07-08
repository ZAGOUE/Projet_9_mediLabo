package com.note_service.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "notes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Note {

    @Id
    private String id;

    private String patientId;
    private String contenu;
    private LocalDateTime date = LocalDateTime.now();
}
