package com.tsystems.lims.controller;

import com.tsystems.lims.models.TherapyEventCause;
import com.tsystems.lims.models.TherapyEventStatus;
import com.tsystems.lims.service.interfaces.IPatientService;
import com.tsystems.lims.service.interfaces.ITherapyEventService;
import com.tsystems.lims.service.interfaces.ITherapyEventServiceJMS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class ScheduleController {
    @Autowired
    ITherapyEventServiceJMS therapyEventService;

    @Autowired
    IPatientService patientService;

    @GetMapping(value = "schedule")
    public String getScheduleForm() {
        return "schedule";
    }

    @RequestMapping(value = "schedule/{id}", method = GET)
    public String getEvent(Model model, @PathVariable Integer id) {
        model.addAttribute("event", therapyEventService.getTherapyEvent(id));
        return "event";
    }

    @RequestMapping(value = "schedule", method = POST)
    public String postSchedule(@RequestParam(value = "id") Integer id, @RequestParam(value = "status") String status, @RequestParam(value = "cause", required = false) String cause, Model model) {
        return "redirect:schedule/"+therapyEventService.updateStatusAndCause(id, TherapyEventStatus.valueOf(status), TherapyEventCause.valueOf(cause)).getId();
    }


}
