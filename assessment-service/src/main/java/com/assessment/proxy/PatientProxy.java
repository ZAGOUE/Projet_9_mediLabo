package com.assessment.proxy;

import com.assessment.model.Patient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "patient-service", url = "http://localhost:8081") // port du service patient
public interface PatientProxy {
    @GetMapping("/patients/{id}")
    Patient getPatientById(@PathVariable("id") Long id);
}
