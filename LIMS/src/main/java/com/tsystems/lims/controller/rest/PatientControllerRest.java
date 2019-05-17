package com.tsystems.lims.controller.rest;

import com.tsystems.lims.service.interfaces.IPatientService;
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
public class PatientControllerRest {
    @Autowired
    IPatientService patientService;

    @GetMapping(value = "rest/patients", produces = MediaType.APPLICATION_JSON_VALUE)
    @SuppressWarnings("serial")
    public ResponseEntity<Object> getPatients(@RequestParam(value = "name", required = false) String name, @RequestParam(value = "offset", required = false) Integer offset, @RequestParam(value = "count", required = false) Integer count) {
        return new ResponseEntity<>(patientService.getPatients(new HashMap<String, String>() {{
            if ((name != null) && (!name.equals(""))) {
                put("name", name);
            }
        }}, offset, count), HttpStatus.OK);
    }
}
