package com.frontend_service.model;

import lombok.Data;

@Data
public class Note {
    private String id;
    private Long patientId;
    private String contenu;
}
