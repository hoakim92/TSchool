package com.tsystems.lims.controller;


import com.tsystems.lims.dto.PatientDto;
import com.tsystems.lims.models.PatientGender;
import com.tsystems.lims.service.interfaces.IDiagnosisService;
import com.tsystems.lims.service.interfaces.IDoctorService;
import com.tsystems.lims.service.interfaces.IPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.logging.Logger;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@Secured("ROLE_ADMIN")
public class PatientController {

    @Autowired
    IPatientService patientService;
    @Autowired
    IDiagnosisService diagnosisService;
    @Autowired
    IDoctorService doctorService;

    @RequestMapping(value = "patients", method = POST)
    public String postPatient(@RequestParam(value = "id", required = false) Integer id, @RequestParam(value = "doctorId") Integer doctorId, @RequestParam(value = "diagnosisIds[]", required = false) Integer[] diagnosisIds,
                              @RequestParam(value = "name") String name, @RequestParam(value = "gender") PatientGender gender, @RequestParam(value = "dateOfBirth") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateOfBirth,
                              @RequestParam(value = "insurance") String insurance, @RequestParam(value = "email", required = false) String email, @RequestParam(value = "status") String status, Model model) {
        return "redirect:patients/" + patientService.saveOrUpdatePatient(id, doctorId, diagnosisIds, name, gender, dateOfBirth, insurance, email, status).getId();
    }

    @GetMapping("patients/createpatient")
    public String createPatient(Model model) {
        return "patient";
    }

    @GetMapping("/patients")
    public String getAllPatients() {
        return "patientsList";
    }

    @RequestMapping(value = "/patients/{id}", method = GET)
    public String getPatient(Model model, @PathVariable int id) {
        model.addAttribute("patient", patientService.getPatient(id));
        return "patient";
    }
}