package com.frontend_service.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AssessmentServiceProxy {

    private final RestTemplate restTemplate;

    @Value("${assessment.service.url}")
    private String assessmentServiceUrl;

    public AssessmentServiceProxy(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getAssessment(Long patientId) {
        HttpHeaders headers = new HttpHeaders();
        String auth = "admin:admin123";
        String encodedAuth = java.util.Base64.getEncoder().encodeToString(auth.getBytes());
        headers.add("Authorization", "Basic " + encodedAuth);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        // URL attendue par assessment-service (adapte si besoin)
        String url = assessmentServiceUrl + "/assessments?patientId=" + patientId;

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                String.class
        );
        return response.getBody();
    }
}
