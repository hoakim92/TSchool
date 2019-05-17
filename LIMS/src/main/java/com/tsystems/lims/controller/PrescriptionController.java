package com.tsystems.lims.controller;

import com.tsystems.lims.dto.PrescriptionDto;
import com.tsystems.lims.models.PrescriptionPeriodType;
import com.tsystems.lims.service.interfaces.ICureService;
import com.tsystems.lims.service.interfaces.IPatientService;
import com.tsystems.lims.service.interfaces.IPrescriptionServiceJMS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@Secured("ROLE_ADMIN")
public class PrescriptionController {
    @Autowired
    IPrescriptionServiceJMS prescriptionService;

    @Autowired
    IPatientService patientService;

    @Autowired
    ICureService cureService;

    @RequestMapping(value = "prescriptions", method = POST)
    public String postPatient(@RequestParam(value = "id", required = false) Integer id, @RequestParam(value = "patientId") Integer patientId, @RequestParam(value = "cureId") Integer cureId,
                              @RequestParam(value = "dateOfBegin") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateOfBegin, @RequestParam(value = "dateOfEnd") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateOfEnd,
                              @RequestParam(value = "doze") String doze, @RequestParam(value = "periodType") String periodType, @RequestParam(value = "dates[]") String[] dates, @RequestParam(value = "daysOfWeek[]", required = false) Integer[] daysOfWeek,
                              Model model) {
        PrescriptionDto prescriptionDto = prescriptionService.saveOrUpdatePrescription(id, patientId, cureId, dateOfBegin, dateOfEnd, doze, PrescriptionPeriodType.valueOf(periodType), dates, daysOfWeek);
        return "redirect:prescriptions/" + prescriptionDto.getId();
    }

    @RequestMapping(value = "prescriptions/createprescription", method = GET)
    public String createPrescription(@RequestParam(value = "patientId", required = false) Integer patientId, Model model) {
        if (patientId != null)
            model.addAttribute("patient", patientService.getPatient(patientId));
        return "prescription";
    }

    @RequestMapping(value = "prescriptions/cancelPlannedEvents", method = GET)
    public String cancelUnplannedEvents(@RequestParam(value = "id") Integer id, Model model) {
        prescriptionService.cancelPlannedEvents(id);
        return getPrescription(model, id);
    }

    @RequestMapping(value = "prescriptions", method = GET)
    public String getPrescriptions() {
        return "prescriptionsList";
    }

    @RequestMapping(value = "prescriptions/{id}", method = GET)
    public String getPrescription(Model model, @PathVariable Integer id) {
        model.addAttribute("prescription", prescriptionService.getPrescription(id));
        return "prescription";
    }

}
