package com.tsystems.lims.controller.rest;


import com.tsystems.lims.service.interfaces.IDoctorService;
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
public class DoctorControllerRest {
    @Autowired
    IDoctorService doctorService;

    @GetMapping(value = "rest/doctors", produces = MediaType.APPLICATION_JSON_VALUE)
    @SuppressWarnings("serial")
    public ResponseEntity<Object> getDiagnoses(@RequestParam(value = "name", required = false) String name, @RequestParam(value = "offset", required = false) Integer offset, @RequestParam(value = "count", required = false) Integer count) {
        return new ResponseEntity<>(doctorService.getDoctors(new HashMap<String, String>() {{
            if ((name != null) && (!name.equals(""))) {
                put("name", name);
            }
        }}, offset, count), HttpStatus.OK);
    }
}
