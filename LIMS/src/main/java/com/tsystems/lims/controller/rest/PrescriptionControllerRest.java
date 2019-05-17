package com.tsystems.lims.controller.rest;

import com.tsystems.lims.service.interfaces.IPrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@Secured("ROLE_ADMIN")
public class PrescriptionControllerRest {
    @Autowired
    IPrescriptionService prescriptionService;

    @GetMapping(value = "rest/prescriptions", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getPrescriptions(@RequestParam(value = "patientId", required = false) Integer patientId, @RequestParam(value = "patient", required = false) String patient, @RequestParam(value = "cure", required = false) String cure, @RequestParam("offset") Integer offset, @RequestParam("count") Integer count) {
        return new ResponseEntity<>(prescriptionService.getPrescriptions(new HashMap<String, String>() {{
            if (patientId != null) {
                put("patient.id", patientId.toString());
            }
            if ((patient != null) && (!patient.equals(""))) {
                put("patient.name", patient);
            }
            if ((cure != null) && (!cure.equals(""))) {
                put("cure.name", cure);
            }
        }}, offset, count), HttpStatus.OK);
    }
}