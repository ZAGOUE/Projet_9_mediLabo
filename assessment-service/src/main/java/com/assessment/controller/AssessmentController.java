package com.assessment.controller;

import com.assessment.service.AssessmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/assessments")
public class AssessmentController {

    private final AssessmentService assessmentService;

    @Autowired
    public AssessmentController(AssessmentService assessmentService) {
        this.assessmentService = assessmentService;
    }

    /**
     * Évalue le risque de diabète pour un patient donné
     * @param patientId l'identifiant du patient
     * @return une chaîne représentant le niveau de risque
     */
    @GetMapping
    public String getAssessment(@RequestParam Long patientId) {
        return assessmentService.assessRisk(patientId);
    }
}
