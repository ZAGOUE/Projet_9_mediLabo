package com.frontend_service.model;

import lombok.Data;

@Data
public class Note {
    private String id;
    private String patientId;
    private String contenu;
}
