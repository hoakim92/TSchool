package com.tsystems.lims.controller.rest;

import com.tsystems.lims.service.interfaces.ITherapyEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class EventControllerRest {
    @Autowired
    ITherapyEventService therapyEventService;

    @GetMapping(value = "rest/eventsByDay", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getEventsForToday() {
        return new ResponseEntity<>(therapyEventService.getTherapyEvents("ByDay", null).stream().filter(e -> e.getStatus().equals("PLANNED")).toArray(), HttpStatus.OK);
    }

    @GetMapping(value = "rest/eventsBySchedule", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getEvents(@RequestParam("period") String period, @RequestParam(value = "patients", required = false) Integer[] patients,
                                            @RequestParam(value = "offset", required = false) Integer offset, @RequestParam(value = "count", required = false) Integer count) {
        return new ResponseEntity<>(therapyEventService.getTherapyEvents(period, patients, offset, count), HttpStatus.OK);
    }

    @GetMapping(value = "rest/events", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getEvents(@RequestParam(value = "prescriptionId", required = false) Integer prescriptionId,
                                            @RequestParam(value = "offset", required = false) Integer offset, @RequestParam(value = "count", required = false) Integer count) {
        return new ResponseEntity<>(therapyEventService.getTherapyEvents(new HashMap<String, String>() {{
            if ((prescriptionId != null)) {
                put("prescription.id", prescriptionId.toString());
            }
        }}, offset, count), HttpStatus.OK);
    }
}


