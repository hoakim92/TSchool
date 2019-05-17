package com.tsystems.lims.controller;

import com.tsystems.lims.dto.DiagnosisDto;
import com.tsystems.lims.service.interfaces.IDiagnosisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@Secured("ROLE_ADMIN")
public class DiagnosisController {
    @Autowired
    IDiagnosisService diagnosisService;

    @GetMapping("/diagnosis")
    public String getAllDiagnosis() {
        return "diagnosisList";
    }

    @PostMapping("/diagnosis")
    public String postDiagnosis(@RequestParam(value = "id", required = false) Integer id, @RequestParam(value = "description", required = false) String description, @RequestParam(value = "name", required = false) String name, Model model) {
        DiagnosisDto diagnosisDto = diagnosisService.saveOrUpdateDiagnosis(id, name, description);
        return "redirect:diagnosis/"+diagnosisDto.getId();
    }

    @GetMapping("diagnosis/creatediagnosis")
    public String createDiagnosis() {
        return "diagnosis";
    }


    @RequestMapping(value = "/diagnosis/{id}", method = GET)
    public String getDiagnosis(Model model, @PathVariable int id) {
        model.addAttribute("diagnosis", diagnosisService.getDiagnosis(id));
        return "diagnosis";
    }
}
