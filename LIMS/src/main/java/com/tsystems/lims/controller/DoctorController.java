package com.tsystems.lims.controller;

import com.tsystems.lims.dto.DoctorDto;
import com.tsystems.lims.service.interfaces.IDoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@Secured("ROLE_ADMIN")
public class DoctorController {

    @Autowired
    IDoctorService doctorService;

    @PostMapping("/doctors")
    public String postDoctor(@RequestParam(value = "id", required = false) Integer id, @RequestParam(value = "name", required = false) String name, Model model) {
        DoctorDto doctorDto = doctorService .saveOrUpdateDoctor(id, name);
        return "redirect:doctors/"+doctorDto.getId();
    }

    @GetMapping("doctors/createdoctor")
    public String createDoctor(Model model) {
        return "doctor";
    }

    @GetMapping("/doctors")
    public String getAllDoctors(Model model) {
        model.addAttribute("doctors", doctorService.getDoctors());
        return "doctorsList";
    }

    @RequestMapping(value = "/doctors/{id}", method = GET)
    public String getDoctor(Model model, @PathVariable int id) {
        model.addAttribute("doctor", doctorService.getDoctor(id));
        return "doctor";
    }
}
